package com.unleash.articleservice.Model.util;

import com.unleash.articleservice.Model.Article;

import java.util.Comparator;

public class ArticleComparator implements Comparator<Article> {

    @Override
    public int compare(Article a1, Article a2) {
        return a2.getUploadedOn().compareTo(a1.getUploadedOn());
    }
}
