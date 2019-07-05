/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/2/19 9:08 PM
 *
 * Last modified 7/2/19 9:08 PM
 */

package com.flipp.flopp.data.art.network;

import java.util.List;

public class RandomMeResponse {
    private List<User> results;

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }

    public static class User{
        public Picture picture;
        public Name name;


    }

    public static class Picture{
        public String thumbnail;
    }

    public static class Name{
        public String first;
        public String last;
        public String getName(){
            return first+" "+last;
        }

    }
}
