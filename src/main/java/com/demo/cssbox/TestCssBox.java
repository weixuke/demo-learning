package com.demo.cssbox;

import org.fit.cssbox.demo.ImageRenderer;

import java.io.File;
import java.io.FileOutputStream;

public class TestCssBox {
    public static void main(String args[]){
        try{
            ImageRenderer render = new ImageRenderer();
            System.out.println("start");
            FileOutputStream out = new FileOutputStream(new File("D:"+ File.separator+"html.png"));

            String url = "http://10.10.10.22:8001/jlb-web-front-app/dist/#/activeManage";
            //url = "http://jui.org";
            url = "http://10.10.10.10:7990/projects/AP_SERVER/repos/jlb/browse/jlb-yy-app";
            url = "http://read.10086.cn/elib/www/marketingcenter/operationTransact.jsp";
            url = "https://mvnrepository.com/artifact/net.sf.cssbox/cssbox/4.12";
            url = "http://10.10.10.30:3000/";
//            url = "http://tool.oschina.net/hexconvert/";
//            url = "file:///D:/html/Bitbucket.html";
            url = "file:///D:/testhtml.html";
            render.renderURL(url, out, ImageRenderer.Type.PNG);
            System.out.println("end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
