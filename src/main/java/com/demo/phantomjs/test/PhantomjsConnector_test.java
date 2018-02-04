package com.demo.phantomjs.test;

import org.apache.log4j.Logger;

import java.io.*;

public class PhantomjsConnector_test {
    private static Logger log = Logger.getLogger(PhantomjsConnector_test.class);
    private String pid;        //进程PID
    private OutputStream out;
    private PrintWriter writer;
    private InputStream in;
    private InputStreamReader inReader;
    private BufferedReader reader;
    private static String HTML_TO_IMG_FAIL = "HTML_TO_IMG_FAIL";
    private static String HTML_TO_IMG_SUCCESS = "HTML_TO_IMG_SUCCESS";

    private static String JS_PATH = "D:\\work_note\\htmlToImg\\sheng_chan\\util01.js";

    public PhantomjsConnector_test() {
        try {
            Process process = Runtime.getRuntime().exec("phantomjs "+JS_PATH);    //通过命令行启动phantomjs
            //初始化IO流
            in = process.getInputStream();

            //BufferedReader br = new BufferedReader(new InputStreamReader(is));

            inReader = new InputStreamReader(in, "utf-8");
            reader = new BufferedReader(inReader);
            pid = reader.readLine();        //从phantomjs脚本中获取本进程的PID
            log.info("pid="+pid);
            out = process.getOutputStream();
            writer = new PrintWriter(out);

            readResponse();
        } catch (Exception e) {
            close();
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

    //执行查询
    public void exec(String args) throws IOException {
        log.info(args);
        writer.println(args);        //把args输出到phantomjs
        writer.flush();                //立即输出
        //String result = reader.readLine();   //读取phantomjs的输出


    }

    //关闭IO
    private void close() {
        try {
            if (in!=null) in.close();
            if (inReader!=null) inReader.close();
            if (reader!=null) reader.close();
            if (out!=null) out.close();
            if (writer!=null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readResponse(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(reader!=null){
                        try {
                            String result = reader.readLine();
                            if(HTML_TO_IMG_FAIL.equals(result)||
                                    HTML_TO_IMG_SUCCESS.equals(result)){
                                log.info(result);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
    public static void main(String args[]){

        for(int m=0;m<1;m++) {
            String m_str = ""+m;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    log.info(m_str+" start");
                    PhantomjsConnector_test pc = new PhantomjsConnector_test();
                    log.info(m_str+" opend");
                    for (int i = 0; i < 1; i++) {
                        try {
                            String url = "http://www.baidu.com";
                            pc.exec("{'url':'" + url + "','savePath':'D:/work_note/htmlToImg/sheng_chan/util0203_" + i + "_"+m_str+"_001.png'}");
                            //log.info("1_"+result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {
                            String url = "http://www.baidu.com";
                            pc.exec("{'url':'" + url + "','savePath':'D:/work_note/htmlToImg/sheng_chan/util0203_" + i + "_"+m_str+"_002.png'}");
                            //log.info("2_"+result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    log.info(m_str+" end");
                }
            }).start();

        }


    }
}
