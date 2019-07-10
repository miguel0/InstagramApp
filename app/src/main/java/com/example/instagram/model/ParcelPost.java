package com.example.instagram.model;

import com.parse.ParseFile;
import org.parceler.Parcel;

@Parcel
public class ParcelPost {
    public String username;
    public String description;
    public String date;
    public ParseFile image;

    public ParcelPost() {}

    public ParcelPost(String username, String description, String date, ParseFile image) {
        this.username = username;
        this.description = description;
        this.date = date;
        this.image = image;
    }
}
