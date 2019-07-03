/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/2/19 11:02 AM
 *
 * Last modified 7/2/19 11:02 AM
 */

package com.flipp.flopp.data.art.network;

import androidx.annotation.Nullable;

public class ArtsyServiceHolder {

    private ArtsyService artsyService;

    @Nullable
    ArtsyService artsyService() {
        return artsyService;
    }

    public void setArtsyService(ArtsyService artsyService) {
        this.artsyService = artsyService;
    }
}
