/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 10:35 PM
 *
 * Last modified 7/4/19 4:59 PM
 */

package com.flipp.flopp.util;

import com.flipp.flopp.common.architecture.AppExecutors;

import java.util.concurrent.Executor;

/**
 * Executors to use for testing
 *
 * We use this to avoid issues with mockito (ex. unsupported threading classes)
 */
public class InstantAppExecutors extends AppExecutors {
    private static Executor instant = command -> command.run();

    public  InstantAppExecutors() {
        super(instant, instant, instant);
    }
}