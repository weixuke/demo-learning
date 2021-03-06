package com.demo.phantomjs.domain;

import com.alibaba.fastjson.JSON;

public class HtmlToImgConfig {
    private String logPath;
    private int viewWidth;
    private int viewHeight;
    private int ps_top;
    private int ps_left;
    private int ps_width;
    private int ps_height;
    private int waitTime;

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }

    public int getPs_top() {
        return ps_top;
    }

    public void setPs_top(int ps_top) {
        this.ps_top = ps_top;
    }

    public int getPs_left() {
        return ps_left;
    }

    public void setPs_left(int ps_left) {
        this.ps_left = ps_left;
    }

    public int getPs_width() {
        return ps_width;
    }

    public void setPs_width(int ps_width) {
        this.ps_width = ps_width;
    }

    public int getPs_height() {
        return ps_height;
    }

    public void setPs_height(int ps_height) {
        this.ps_height = ps_height;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public String toJson(){

        return JSON.toJSONString(this);
    }
}
