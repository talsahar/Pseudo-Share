package com.tal.pseudo_share.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by User on 15/12/2017.
 */

@Entity
@TypeConverters({DateConverter.class, Difficulty.class, PseudoType.class})
public class Pseudo {
    @PrimaryKey
    @NonNull
    public String id;
    private long date;
    private String name;
    public Difficulty difficulty;
    public PseudoType type;
    private String author;
    private String description;
    private String imageUrl;
    private String content;
    private boolean isDeleted;
    public long lastUpdate;

    public static PseudoBuilder builder(String id) {
        return new PseudoBuilder(id);
    }

    public PseudoBuilder builderFromThis() {
        PseudoBuilder builder = new PseudoBuilder(id);
        builder.setAuthor(author).setDate(DateConverter.toDate(date)).setName(name).setDifficulty(difficulty).setPseudoType(type).setDescription(description).setImageurl(imageUrl)
                .setContent(content).setDeleted(isDeleted).setLastupdate(lastUpdate);
        return builder;
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


    public Pseudo() {
    }

    //compare ids
    @Override
    public boolean equals(Object obj) {
        Pseudo p = (Pseudo) obj;
        return p.getId().equals(((Pseudo) obj).getId());
    }


    public static class PseudoBuilder {

        Pseudo pseudo;

        public PseudoBuilder(String id) {
            pseudo = new Pseudo();
            pseudo.setId(id);
        }

        public PseudoBuilder setDate(Date date) {
            pseudo.date = DateConverter.toTimestamp(date);
            return this;
        }

        public PseudoBuilder setName(String name) {
            pseudo.name = name;
            return this;
        }

        public PseudoBuilder setDifficulty(Difficulty difficulty) {
            pseudo.difficulty = difficulty;
            return this;
        }

        public PseudoBuilder setPseudoType(PseudoType pseudoType) {
            pseudo.type = pseudoType;
            return this;
        }


        public PseudoBuilder setAuthor(String author) {
            pseudo.author = author;
            return this;
        }

        public PseudoBuilder setDescription(String description) {
            pseudo.description = description;
            return this;
        }

        public PseudoBuilder setImageurl(String imageurl) {
            pseudo.imageUrl = imageurl;
            return this;
        }

        public PseudoBuilder setContent(String content) {
            pseudo.content = content;
            return this;
        }

        public PseudoBuilder setDeleted(boolean bool) {
            pseudo.isDeleted = bool;
            return this;
        }

        public PseudoBuilder setLastupdate(long date) {
            pseudo.lastUpdate = date;
            return this;
        }

        public Pseudo build() {
            return pseudo;
        }

        public String getId() {
            return pseudo.getId();
        }

        public void setId(String id) {
            pseudo.id = id;
        }
    }

    public String getImageFileName() {
        return id + ".jpg";
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public PseudoType getType() {
        return type;
    }

    public void setType(PseudoType type) {
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}
