package com.example.instagram.model;

import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel
public class ParcelPost {
    public String username;
    public String description;
    public String date;
    public ParseFile image;
    public ParseUser user;

    public ParcelPost() {}

    public ParcelPost(String username, String description, String date, ParseFile image, ParseUser user) {
        this.username = username;
        this.description = description;
        this.date = date;
        this.image = image;
        this.user = user;
    }
}
