/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/2/19 9:22 PM
 *
 * Last modified 7/2/19 9:22 PM
 */

package com.flipp.flopp.data.art.local;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * Class that represents Art Owner
 */
public class ArtOwner implements Parcelable {

    private String name;
    private String thumbnail;

    public ArtOwner(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    protected ArtOwner(Parcel in) {
        name = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(thumbnail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ArtOwner> CREATOR = new Parcelable.Creator<ArtOwner>() {
        @Override
        public ArtOwner createFromParcel(Parcel in) {
            return new ArtOwner(in);
        }

        @Override
        public ArtOwner[] newArray(int size) {
            return new ArtOwner[size];
        }
    };
}