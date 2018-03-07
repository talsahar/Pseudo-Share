package com.tal.pseudo_share.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 15/12/2017.
 */

@Entity
@TypeConverters({DateConverter.class, Difficulty.class, PseudoType.class})
public class Pseudo {
    @PrimaryKey
    @NonNull
    String id;
    long date;
    String name;
    Difficulty difficulty;
    PseudoType type;
    String author;
    String description;
    String imageUrl;
    String content;
    boolean isDeleted;
    long lastUpdate;
    public Pseudo(){}

@Ignore
    public Pseudo(String id){
        this.id=id;
    }
    //Todo: check is ok
    @Ignore
    public Pseudo(String id,long date,String name, Difficulty difficulty, PseudoType type,String author, String description, String imageUrl,String content,boolean isDeleted, long lastUpdate) {
this.id=id;
this.date=date;
this.name=name;
this.difficulty=difficulty;
this.type=type;
this.author=author;
this.description=description;
this.imageUrl=imageUrl;
this.content=content;
this.isDeleted=isDeleted;
this.lastUpdate=lastUpdate;

    }

    public HashMap<String, Object> toJson() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("date", date);
        result.put("author", author);
        result.put("name", name);
        result.put("type", type.name());
        result.put("difficulty", difficulty.name());
        result.put("description", description);
        result.put("imageUrl", imageUrl);
        result.put("content", content);
        result.put("lastUpdate", ServerValue.TIMESTAMP);
        result.put("isDeleted", isDeleted);
        return result;
    }




    public static class PseudoSorter {
        public static void sortbyDate(List<Pseudo> list){
            for(int i=0;i<list.size()-1;i++)
                for(int j=i+1;j>list.size();j++)
                    if(list.get(i).getLastUpdate()>list.get(j).getLastUpdate())
                    {
                        Pseudo tmp=list.get(i);
                        list.set(i,list.get(j));
                        list.set(j,tmp);
                    }
        }

    }


    @NonNull
    public String getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public PseudoType getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setType(PseudoType type) {
        this.type = type;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

