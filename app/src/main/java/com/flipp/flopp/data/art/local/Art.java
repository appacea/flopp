/*
 * Copyright (c) Tchipr Ltd 2019. All right reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Yvan Stern on 7/1/19 1:53 PM
 *
 * Last modified 7/1/19 1:53 PM
 */

package com.flipp.flopp.data.art.local;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Art {


    @PrimaryKey
    @NonNull
    private String id;

    private String category;
    private String medium;
    private String date;
    private String collecting_institution;
    private List<String> image_versions;
    private ArtLinks _links;

    @Embedded
    private ArtOwner owner;



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
        return this._links.image.href.replace("{image_version}","large");
    }

    public class ArtLinks{
        public ArtImage thumbnail;
        public ArtImage image;

    }

    public class ArtImage{
        public String href;
    }
}
