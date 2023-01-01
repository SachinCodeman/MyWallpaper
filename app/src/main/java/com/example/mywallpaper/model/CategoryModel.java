package com.example.mywallpaper.model;

public class CategoryModel {

    private String categoryTitleUrl;
    private int categoryImageUrl;

    public CategoryModel() {
    }

    public CategoryModel(String categoryTitleUrl, int categoryImageUrl) {
        this.categoryTitleUrl = categoryTitleUrl;
        this.categoryImageUrl = categoryImageUrl;
    }

    public String getCategoryTitleUrl() {
        return categoryTitleUrl;
    }

    public void setCategoryTitleUrl(String categoryTitleUrl) {
        this.categoryTitleUrl = categoryTitleUrl;
    }

    public int getCategoryImageUrl() {
        return categoryImageUrl;
    }

    public void setCategoryImageUrl(int categoryImageUrl) {
        this.categoryImageUrl = categoryImageUrl;
    }

}
