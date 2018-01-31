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
            url = "https://test-static.bestjlb.com/application?tid=42&type=18&orgId=1000000019&activeCode=APP_GrabRedPacket&access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMDEwNDA1IiwiaXNvbGF0aW9uIjoiYmVzdGpsYiIsImV4cCI6MTUxNzI4MDQyMSwidHlwZSI6IkFORFJPSUQiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNTdjZDc5YjEtNGY3Yi00ZDAyLWFlMTItNGY5YTFiOTE0NjM3In0.NPSz3DWqE9-Av4u20Bkn4KAS-uCCFM0bB3TEG4zRbemL76d3JDUVX4ebCMX1pwAxNU9p3-RqEMSNE8cyJ9Ei6Q";
            url = "file:///D:/temp/%E6%9D%AD%E5%B7%9E%E5%8F%AB%E5%8F%AB%E5%AD%A6%E7%A7%91%E8%8B%B1%E8%AF%AD%E5%A4%A9%E9%99%8D%E7%BA%A2%E5%8C%85.html";
            render.renderURL(url, out, ImageRenderer.Type.PNG);
            System.out.println("end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
