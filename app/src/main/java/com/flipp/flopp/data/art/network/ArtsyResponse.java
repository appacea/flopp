/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/2/19 12:16 PM
 *
 * Last modified 7/2/19 12:16 PM
 */

package com.flipp.flopp.data.art.network;

import com.flipp.flopp.data.art.local.Art;

import java.util.List;

public class ArtsyResponse {

    private int total_count;
    private ArtsyEmbedded _embedded;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public ArtsyEmbedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(ArtsyEmbedded _embedded) {
        this._embedded = _embedded;
    }

    public static class ArtsyEmbedded{

        private List<Art> artworks;

        public List<Art> getArtworks() {
            return artworks;
        }

        public void setArtworks(List<Art> artworks) {
            this.artworks = artworks;
        }
    }
}

