package com.demo.phantomjs.domain;

import com.alibaba.fastjson.JSON;

public class HtmlInfo {
    private String key;
    private String url;
    private String savePath;
    private String result;

    public HtmlInfo(){

    }

    public String getSavePath() {
        return savePath;
    }

    public HtmlInfo(String key, String url, String savePath, String result) {
        this.key = key;
        this.url = url;
        this.savePath = savePath;
        this.result = result;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String toJson(){

        return JSON.toJSONString(this);
    }
}
