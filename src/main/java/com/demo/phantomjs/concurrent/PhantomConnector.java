package com.demo.phantomjs.concurrent;

import com.demo.phantomjs.domain.HtmlInfo;
import com.demo.phantomjs.domain.HtmlToImgConfig;
import org.apache.log4j.Logger;

import java.io.*;

public class PhantomConnector {
    private static Logger logger = Logger.getLogger(PhantomjsConnector_test.class);
    private String pid;        //进程PID
    private OutputStream out;
    private PrintWriter writer;
    private InputStream in;
    private InputStreamReader inReader;
    private BufferedReader reader;

    private static boolean flag = true;

    private String key;

    public PhantomConnector(String js_path,HtmlToImgConfig config,Runnable task) {
        if(null==config){
            throw new NullPointerException("config");
        }
        try {
            System.out.println("phantomjs "+js_path+" "+"\""+(config.toJson().replace("\"","'"))+"\"");
            Process process = Runtime.getRuntime().exec("phantomjs "+js_path+" "+"\""+(config.toJson().replace("\"","'"))+"\"");    //通过命令行启动phantomjs
            //初始化IO流
            in = process.getInputStream();
            inReader = new InputStreamReader(in, "utf-8");
            reader = new BufferedReader(inReader);
            pid = reader.readLine();        //从phantomjs脚本中获取本进程的PID

            out = process.getOutputStream();
            writer = new PrintWriter(out);

            new Thread(task).start();
            logger.info("init phantomjs sucess! pid="+pid);
        } catch (Exception e) {
            close();
            logger.info("init phantomjs fail! pid="+pid , e);
        }
    }

    private class ResponseThread implements Runnable{
        private BufferedReader reader;

        public ResponseThread(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void run() {
            while(flag){
                try {
                    logger.info(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //执行查询
    public void exec(HtmlInfo htmlInfo) throws IOException {
        writer.println("\""+(htmlInfo.toJson().replace("\"","'"))+"\"");        //把args输出到phantomjs
        writer.flush();                //立即输出
        //String result = reader.readLine();   //读取phantomjs的输出
        String result = "";

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
            close();    //先关闭IO流
            Runtime.getRuntime().exec("taskkill /F /PID " + pid);    //Windows下清除进程的命令，Linux则为kill -9 pid
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPid() {
        return pid;
    }

    public static void main(String[] args) {
        String js_path = "D:\\work_note\\htmlToImg\\sheng_chan\\util01.js";
        HtmlToImgConfig config = new HtmlToImgConfig();

        config.setKey("pid_123Key");
        config.setLogPath("D:/work_note/htmlToImg/sheng_chan/log.log");
        config.setSavePath("001.png");
        config.setViewWidth(800);
        config.setViewHeight(600);
        config.setPs_top(0);
        config.setPs_left(0);
        config.setPs_width(800);
        config.setPs_height(600);
        config.setWaitTime(1000);

        PhantomConnector pc = new PhantomConnector(js_path,config);

        HtmlInfo htmlInfo = new HtmlInfo("001","http://www.baidu.com","test.png","");

        try {
            pc.exec(htmlInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
