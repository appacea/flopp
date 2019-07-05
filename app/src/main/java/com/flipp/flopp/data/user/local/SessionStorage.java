/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 8:09 PM
 *
 * Last modified 7/4/19 8:09 PM
 */

package com.flipp.flopp.data.user.local;

import android.content.SharedPreferences;

/***
 * Handle session
 */
public class SessionStorage {

    static private String KEY_CITY = "com.flipp.flopp.user.city";
    static private String DEFAULT_CITY = "Montreal";

    private SharedPreferences prefs;
    public SessionStorage(SharedPreferences prefs){
        this.prefs = prefs;
    }

    /**
     * Get the current city in session
     * @return
     */
    public String getCity(){
        String city = prefs.getString(KEY_CITY,DEFAULT_CITY);
        return city;
    }

    /**
     * Save a city into the session
     * @param city
     */
    public void saveCity(String city){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CITY, city);
        editor.commit();
    }
}
