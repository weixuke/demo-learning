package com.demo.phantomjs.concurrent;

import com.demo.phantomjs.Exception.PhantomException;
import com.demo.phantomjs.domain.HtmlInfo;
import com.demo.phantomjs.domain.HtmlToImgConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class PhantomConnector {
    private static Logger logger = Logger.getLogger(PhantomConnector.class);

    private final static String SERVER_INIT_SUCCESS = "SERVER_INIT_SUCCESS";
    private final static String HTML_TO_IMG_SUCCESS = "HTML_TO_IMG_SUCCESS";
    private final static String HTML_TO_IMG_FAIL = "HTML_TO_IMG_FAIL";
    private static BlockingQueue<String> blockingQueue;
    private String pid;
    private OutputStream out;
    private PrintWriter writer;
    private InputStream in;
    private InputStreamReader inReader;
    private BufferedReader reader;

    private static boolean flag = true;

    public PhantomConnector(String js_path,HtmlToImgConfig config,ResponseHandler rr,BlockingQueue<String> blockingQueue) throws PhantomException {
        if(null == config){
            throw new PhantomException("config is null");
        }
        try {
            Process process = Runtime.getRuntime().exec("phantomjs "+js_path+" "+"\""
                    +(config.toJson().replace("\"","'"))+"\"");
            in = process.getInputStream();
            inReader = new InputStreamReader(in, "utf-8");
            reader = new BufferedReader(inReader);
            pid = reader.readLine();
            out = process.getOutputStream();
            writer = new PrintWriter(out);
            logger.info("init phantomjs pid="+pid);
            if(SERVER_INIT_SUCCESS.equals(reader.readLine())){
                logger.info("init phantomjs pid="+pid +" success!");
            }else{
                logger.error("init phantomjs pid="+pid +" fail!");
                kill();
                throw new PhantomException("init phantomjs error!");
            }

            new Thread(new ResponseThread(reader,rr)).start();
            new Thread(new RequestThread(blockingQueue)).start();
        } catch (Exception e) {
            kill();
            logger.error("init phantomjs fail! pid="+pid , e);
            throw new PhantomException("init phantomjs error!");
        }

    }

    private class RequestThread implements Runnable{
        private BlockingQueue<String> blockingQueue;

        private RequestThread(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while(flag) {
                try {
                    logger.info("phantom "+pid+" request runing ...");
                    String url = blockingQueue.take();
                    logger.info("phantom "+pid+" get url "+url+" ...");
                    String hashCode = pid + "_" + String.valueOf(url.hashCode()+ new Random().nextInt(1000));
                    exec(new HtmlInfo(hashCode, url, "D:/temp/" + hashCode + ".png"));
                } catch (InterruptedException e) {
                    logger.error("take url error!", e);
                } catch (IOException e) {
                    logger.error("take url error!", e);
                } catch (PhantomException e) {
                    logger.error("take url error!", e);
                }
            }
        }
    }
    private class ResponseThread implements Runnable{
        private BufferedReader reader;
        private ResponseHandler rr;
        private ResponseThread(BufferedReader reader,ResponseHandler rr) {
            this.reader = reader;
            this.rr = rr;
        }

        @Override
        public void run() {
            while(flag){
                try {
                    String tmp = reader.readLine();

                    if(null!=tmp){
                        if(tmp.startsWith(HTML_TO_IMG_SUCCESS)){
                            rr.handleMessage(tmp.substring(HTML_TO_IMG_SUCCESS.length()+1),
                                    HTML_TO_IMG_SUCCESS);
                        }else if(tmp.startsWith(HTML_TO_IMG_FAIL)){
                            rr.handleMessage(tmp.substring(HTML_TO_IMG_FAIL.length()+1),
                                    HTML_TO_IMG_FAIL);
                        }else{
                            logger.info(tmp);
                        }
                    }
                } catch (IOException e) {
                    logger.error("phantom response error!",e);
                }
            }
        }
    }

    //执行查询
    private void exec(HtmlInfo htmlInfo) throws IOException, PhantomException {
        if(null==htmlInfo){
            throw new PhantomException("htmlInfo is null");
        }
        if(StringUtils.isBlank(htmlInfo.getKey())){
            throw new PhantomException("htmlInfo.key is null");
        }
        if(StringUtils.isBlank(htmlInfo.getUrl())){
            throw new PhantomException("htmlInfo.url is null");
        }
        if(StringUtils.isBlank(htmlInfo.getSavePath())){
            throw new PhantomException("htmlInfo.savePath is null");
        }
        logger.info("phantom "+pid+" exe key=" + htmlInfo.getKey());
        writer.println(htmlInfo.toJson().replace("\"","'"));
        writer.flush();
    }

    //关闭IO
    private void close() {
        try {
            flag = false;

            if (in!=null) in.close();
            if (inReader!=null) inReader.close();
            if (reader!=null) reader.close();
            if (out!=null) out.close();
            if (writer!=null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //结束当前维护的进程
    public void kill() {
        try {
            //Windows下清除进程的命令，Linux则为kill -9 pid
            Runtime.getRuntime().exec("taskkill /F /PID " + pid);
//            writer.println("over");
//            writer.flush();
            close();
            logger.info("phantom pid="+pid+" closed!");
        } catch (Exception e) {
            logger.error("phantom pid="+pid+"closed error!",e);
        }
    }
}
