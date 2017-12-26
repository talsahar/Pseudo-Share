package com.tal.pseudo_share.model;

import java.util.Date;

/**
 * Created by User on 15/12/2017.
 */

public class Pseudo {

    private String name;
    private String author;
    private Date date;
    private PseudoImage image;
    private String content;
    private int likes;

    public Pseudo(String name, String author, Date date, PseudoImage image, String content, int likes) {
        this.name = name;
        this.author = author;
        this.date = date;
        this.image = image;
        this.content = content;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public PseudoImage getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }
}
