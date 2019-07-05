/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 1:53 PM
 *
 * Last modified 7/1/19 1:53 PM
 */

package com.flipp.flopp.data.art.local;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Local entity for Artwork
 *
 */
@Entity
public class Art implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String category;
    private String medium;
    private String date;
    private String collecting_institution;
    private List<String> image_versions;
    private ArtLinks _links;
    private int price;
    private boolean isFavorite = false;
    private String city;
    private int untilDayOfWeek = new Random().nextInt(6)+1;
    @Embedded
    private ArtOwner owner;

    public int getUntilDayOfWeek() {
        return untilDayOfWeek;
    }

    public void setUntilDayOfWeek(int untilDayOfWeek) {
        this.untilDayOfWeek = untilDayOfWeek;
    }

    public String getReadableUntil(){
        switch(this.untilDayOfWeek){
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "Monday";
        }
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getReadablePrice(){
        return "$"+getPrice() + "/mth";
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price ;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCollecting_institution() {
        return collecting_institution;
    }

    public void setCollecting_institution(String collecting_institution) {
        this.collecting_institution = collecting_institution;
    }

    public List<String> getImage_versions() {
        return image_versions;
    }

    public void setImage_versions(List<String> image_versions) {
        this.image_versions = image_versions;
    }


    public ArtLinks get_links() {
        return _links;
    }

    public void set_links(ArtLinks _links) {
        this._links = _links;
    }

    public ArtOwner getOwner() {
        return owner;
    }

    public void setOwner(ArtOwner owner) {
        this.owner = owner;
    }

    public String getLargeImageUrl(){
        return this._links.image.getHref().replace("{image_version}","large");
    }

    public String getThumbnailUrl(){
        return this._links.thumbnail.getHref();
    }

    public Art(){
        price =  new Random().nextInt(100000)+2000;
    }

    protected Art(Parcel in) {
        id = in.readString();
        title = in.readString();
        category = in.readString();
        medium = in.readString();
        date = in.readString();
        collecting_institution = in.readString();
        if (in.readByte() == 0x01) {
            image_versions = new ArrayList<String>();
            in.readList(image_versions, String.class.getClassLoader());
        } else {
            image_versions = null;
        }
        _links = (ArtLinks) in.readValue(ArtLinks.class.getClassLoader());
        price = in.readInt();
        isFavorite = in.readByte() != 0;
        city = in.readString();
        untilDayOfWeek = in.readInt();
        owner = (ArtOwner) in.readValue(ArtOwner.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(category);
        dest.writeString(medium);
        dest.writeString(date);
        dest.writeString(collecting_institution);
        if (image_versions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(image_versions);
        }
        dest.writeValue(_links);
        dest.writeInt(price);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(city);
        dest.writeInt(untilDayOfWeek);
        dest.writeValue(owner);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Art> CREATOR = new Parcelable.Creator<Art>() {
        @Override
        public Art createFromParcel(Parcel in) {
            return new Art(in);
        }

        @Override
        public Art[] newArray(int size) {
            return new Art[size];
        }
    };




}
