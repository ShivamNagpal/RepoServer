package com.nagpal.shivam.reposerver;

public class RepoInfo {
    private String title;
    private String url;

    public RepoInfo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
