package com.tal.pseudo_share.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.firebase.database.ServerValue;
import com.tal.pseudo_share.model.utils.DateConverter;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by User on 15/12/2017.
 */

@Entity
@TypeConverters(DateConverter.class)
public class Pseudo {
    @PrimaryKey
    @NonNull
    public String id;
    private long date;
    private String name;
    public String difficulty;
    public String type;
    private String author;
    private String description;
    private String imageUrl;
    private String content;
    public long lastUpdated;

    public HashMap<String,Object> toJson(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("date", date);
        result.put("author", author);
        result.put("name", name);
        result.put("type", type);
        result.put("difficulty", difficulty);
        result.put("description", description);
        result.put("imageUrl", imageUrl);
        result.put("content", content);
        result.put("lastUpdate", lastUpdated);
        return result;
    }

    public Pseudo() {
    }

    public Pseudo(@NonNull String id, long date, String name, String difficulty, String type, String author, String description, String imageUrl, String content, long lastUpdated) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.difficulty = difficulty;
        this.type = type;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.content = content;
        this.lastUpdated = lastUpdated;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
