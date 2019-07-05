/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 10:36 PM
 *
 * Last modified 7/4/19 10:35 PM
 */

package com.flipp.flopp.repo;

import com.flipp.flopp.common.architecture.Resource;
import com.flipp.flopp.data.art.local.Art;
import com.flipp.flopp.data.art.local.ArtDao;
import com.flipp.flopp.data.art.network.ArtsyResponse;
import com.flipp.flopp.data.art.network.ArtsyService;
import com.flipp.flopp.data.art.network.RandomMeResponse;
import com.flipp.flopp.data.art.network.RandomMeService;
import com.flipp.flopp.data.art.repository.ArtRepository;
import com.flipp.flopp.util.ApiUtil;
import com.flipp.flopp.util.InstantAppExecutors;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ArtRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ArtRepository artRepository;

    @Mock
    ArtDao artDao;
    @Mock
    ArtsyService artsyService;
    @Mock
    RandomMeService randomMeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        artRepository = new ArtRepository(new InstantAppExecutors(), artDao, artsyService, randomMeService);
    }

    @Test
    public void testNetworkProcess() {

        MutableLiveData artResponse = new MutableLiveData<List<Art>>();
        when(artDao.selectAllByCity(anyString())).thenReturn(artResponse);

        ArtsyResponse response = new ArtsyResponse();
        ArtsyResponse.ArtsyEmbedded embedded = new ArtsyResponse.ArtsyEmbedded();
        Art art1 = new Art();
        art1.setId("1");
        List<Art> artwork = new ArrayList<Art>();
        artwork.add(art1);
        artResponse.setValue(artwork);
        embedded.setArtworks(artwork);
        response.set_embedded(embedded);
        when(artsyService.getArt(anyString())).thenReturn(ApiUtil.successCall(response));

        RandomMeResponse randomMe = new RandomMeResponse();
        List<RandomMeResponse.User> users = new ArrayList<RandomMeResponse.User>();
        RandomMeResponse.User user = new RandomMeResponse.User();
        RandomMeResponse.Name name = new RandomMeResponse.Name();
        name.first = "JONAS";
        name.last = "GOG";
        user.name = name;
        RandomMeResponse.Picture picture = new RandomMeResponse.Picture();
        picture.thumbnail = "";
        user.picture = picture;
        users.add(user);
        randomMe.setResults(users);
        when(randomMeService.getUsers()).thenReturn(ApiUtil.successCall(randomMe));

        LiveData<Resource<List<Art>>> data = artRepository.loadArt("Montreal");
        Observer observer = mock(Observer.class);
        data.observeForever(observer);

        Art art2 = new Art();
        art2.setId("1");
        List<Art> artwork2 = new ArrayList<Art>();
        artwork.add(art2);
        artResponse.setValue(artwork2);

        verify(artDao,atLeast(2)).selectAllByCity(anyString());
        verify(artsyService).getArt(anyString());
        verify(randomMeService).getUsers();
    }


    @Test
    public void goToNetwork() {
        //Test a scenario where data needs to be pulled online
    }


    @Test
    public void dontGoToNetwork() {
        //Test a scenario where data can be offline
    }
}
