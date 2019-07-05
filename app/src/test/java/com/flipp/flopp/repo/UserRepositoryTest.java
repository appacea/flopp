/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 10:47 PM
 *
 * Last modified 7/4/19 10:47 PM
 */

package com.flipp.flopp.repo;

import android.content.SharedPreferences;

import com.flipp.flopp.data.user.local.SessionStorage;
import com.flipp.flopp.data.user.repository.UserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class UserRepositoryTest {
    private UserRepository userRepository;
    private SessionStorage sessionStorage;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    SharedPreferences prefs;

    @Mock
    SharedPreferences.Editor editor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionStorage = new SessionStorage(prefs);
        userRepository = new UserRepository(sessionStorage);
    }

    @Test
    public void testGetCity() {
        String city = "Montreal";
        when(prefs.getString(anyString(),anyString())).thenReturn(city);
        assertEquals(city,sessionStorage.getCity());
        assertEquals(city,userRepository.getCity());
    }

    @Test
    public void testSetCity() {
        when(prefs.edit()).thenReturn(editor);
        String city = "Montreal";
        userRepository.setCity(city);
        verify(editor,times(1)).putString(anyString(),anyString());
    }
}
