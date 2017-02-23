package com.example.agupt23.newsapp;

/**
 * Created by agupt23 on 2/23/17.
 */

public class News {

    private String mNewsTitle;

    private String mSectionName;

    private String mPublishedDate;

    private String mWebLink;

    public News(String newsTitle, String sectionName, String publishedDate, String webLink) {
        mNewsTitle = newsTitle;
        mSectionName = sectionName;
        mPublishedDate = publishedDate;
        mWebLink = webLink;
    }

    public String getmNewsTitle() {
        return mNewsTitle;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public String getmWebLink() {
        return mWebLink;
    }
}
