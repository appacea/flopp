/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/4/19 12:03 PM
 *
 * Last modified 7/4/19 12:03 PM
 */

package com.flipp.flopp.data.art.local;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class that defines links for an Artwork
 */
public class ArtLinks implements Parcelable {
    public ArtImage thumbnail = new ArtImage();
    public ArtImage image= new ArtImage();


    public ArtLinks(){}

    protected ArtLinks(Parcel in) {
        thumbnail = (ArtImage) in.readValue(ArtImage.class.getClassLoader());
        image = (ArtImage) in.readValue(ArtImage.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(thumbnail);
        dest.writeValue(image);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ArtLinks> CREATOR = new Parcelable.Creator<ArtLinks>() {
        @Override
        public ArtLinks createFromParcel(Parcel in) {
            return new ArtLinks(in);
        }

        @Override
        public ArtLinks[] newArray(int size) {
            return new ArtLinks[size];
        }
    };
}