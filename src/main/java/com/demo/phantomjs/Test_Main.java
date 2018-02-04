package com.demo.phantomjs;

import com.demo.phantomjs.Exception.PhantomException;
import com.demo.phantomjs.concurrent.PhantomConnector;
import com.demo.phantomjs.domain.HtmlInfo;
import com.demo.phantomjs.domain.HtmlToImgConfig;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test_Main {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        for (int i = 0; i < 10; i++) {
            String num = ""+i;
            new Thread(()->createPhantom("key_"+num,blockingQueue)).start();
        }
        try {
            Thread.currentThread().sleep(30*1000);
            System.out.println("start put url");
            for (int i = 0; i < 100; i++) {
                blockingQueue.put("http://www.baidu.com");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static PhantomConnector createPhantom(String key,BlockingQueue<String> blockingQueue){

        String js_path = "G:/work/code/code/phantomjs_code/util01.js";
        HtmlToImgConfig config = new HtmlToImgConfig();

        config.setLogPath("D:/temp/log_"+key+".log");
        config.setViewWidth(800);
        config.setViewHeight(600);
        config.setPs_top(0);
        config.setPs_left(0);
        config.setPs_width(800);
        config.setPs_height(600);
        config.setWaitTime(1000);

        PhantomConnector pc = null;
        try {
            pc = new PhantomConnector(js_path, config, (key1,result) -> System.out.println("result key="+key1+" result="+result),blockingQueue);
        } catch (PhantomException e) {
            e.printStackTrace();
        }

//        try {
//            Thread.currentThread().sleep(300*1000);
//            pc.kill();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return pc;

    }
}
