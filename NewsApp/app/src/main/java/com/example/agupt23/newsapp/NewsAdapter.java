package com.example.agupt23.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by agupt23 on 2/21/17.
 */

public class NewsAdapter extends ArrayAdapter<News>{

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context,0,news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_element_news,parent,false);
        }

        News currentNews = getItem(position);

        TextView titleView = (TextView) convertView.findViewById(R.id.news_title);
        titleView.setText(currentNews.getmNewsTitle());

        TextView publishedDateView = (TextView) convertView.findViewById(R.id.published_date);
        if (currentNews.getmPublishedDate() == null) {
            publishedDateView.setVisibility(GONE);
        } else {
            publishedDateView.setText(currentNews.getmPublishedDate());
        }

        TextView sectionNameView = (TextView) convertView.findViewById(R.id.section_name);
        if (currentNews.getmSectionName() == null) {
            sectionNameView.setVisibility(GONE);
        } else {
            sectionNameView.setText(currentNews.getmSectionName());
        }
        return convertView;
    }
}
