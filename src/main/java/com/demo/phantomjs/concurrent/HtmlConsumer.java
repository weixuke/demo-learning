package com.demo.phantomjs.concurrent;

import com.demo.phantomjs.Exception.PhantomException;
import com.demo.phantomjs.domain.HtmlToImgConfig;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class HtmlConsumer implements Runnable {

    private BlockingQueue queue;

    private boolean isRunning;

    private PhantomConnector pc;

    public HtmlConsumer(String js_path, HtmlToImgConfig config, BlockingQueue queue) throws PhantomException {
        this.queue = queue;
        this.pc = new PhantomConnector(js_path,config,null,null);
    }

    @Override
    public void run() {
        String data = null;
        Random r = new Random();

    }

    public void stop() {
        isRunning = false;
    }
}
