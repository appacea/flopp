/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 1:41 PM
 *
 * Last modified 7/4/19 1:41 PM
 */

package com.flipp.flopp;


import com.flipp.flopp.data.art.repository.ArtRepository;
import com.flipp.flopp.ui.main.MainViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;;
import androidx.lifecycle.Observer;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@RunWith(JUnit4.class)

public class MainViewModelTest {

    private MainViewModel viewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    ArtRepository artRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel(artRepository);
    }


    @Test
    public void testNull() {
        assertThat(viewModel.getAllArt(), notNullValue());
        verify(artRepository, never()).loadArt();
    }

    @Test
    public  void fetchWhenObserved() {
        viewModel.getAllArt().observeForever(mock(Observer.class));
        verify(artRepository, times(1)).loadArt();
    }


}
