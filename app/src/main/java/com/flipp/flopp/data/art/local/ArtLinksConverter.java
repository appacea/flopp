/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/2/19 1:35 PM
 *
 * Last modified 7/2/19 1:35 PM
 */

package com.flipp.flopp.data.art.local;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import androidx.room.TypeConverter;

public class ArtLinksConverter {

    @TypeConverter
    public static ArtLinks fromString(String artLinks) {
        Type listType = new TypeToken<ArtLinks>() {}.getType();
        return new Gson().fromJson(artLinks, listType);
    }

    @TypeConverter
    public static String fromArtLinks(ArtLinks artLinks) {
        Gson gson = new Gson();
        String json = gson.toJson(artLinks);
        return json;
    }
}
