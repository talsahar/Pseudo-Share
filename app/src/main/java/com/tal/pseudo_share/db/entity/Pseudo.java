package com.tal.pseudo_share.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.tal.pseudo_share.db.DateConverter;

import java.util.Date;

/**
 * Created by User on 15/12/2017.
 */

@Entity
@TypeConverters(DateConverter.class)
public class Pseudo {
    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String author;
    private String description;
    private Date date;

    public Pseudo(){

    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    private String imagePath;
    private String content;
    private int likes;

    public Pseudo(@NonNull String name, String author, String description, Date date, String image, String content, int likes) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.date = date;
        this.imagePath = image;
        this.content = content;
        this.likes = likes;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.imagePath = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }


    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }
}
