/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 8:09 PM
 *
 * Last modified 7/4/19 8:09 PM
 */

package com.flipp.flopp.data.user.local;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionStorage {

    static private String KEY = "com.flipp.flopp.user";
    static private String KEY_CITY = "com.flipp.flopp.user.city";
    static private String DEFAULT_CITY = "Anywhere";

    private Context context;
    public SessionStorage(Context context){
        this.context = context;
    }

    public String getCity(){
        SharedPreferences prefs = context.getSharedPreferences(KEY,Context.MODE_PRIVATE);
        return prefs.getString(KEY_CITY,DEFAULT_CITY);
    }

    public void saveCity(String city){
        SharedPreferences prefs = context.getSharedPreferences(KEY,Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_CITY, city);
    }
}
