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

public class ArtImage implements Parcelable {
    private String href;


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public ArtImage(){
            href = "";
    }

    protected ArtImage(Parcel in) {
        href = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ArtImage> CREATOR = new Parcelable.Creator<ArtImage>() {
        @Override
        public ArtImage createFromParcel(Parcel in) {
            return new ArtImage(in);
        }

        @Override
        public ArtImage[] newArray(int size) {
            return new ArtImage[size];
        }
    };
}