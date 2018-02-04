package com.demo.phantomjs.test;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class HtmlToImgUtil {
    private static Logger log = Logger.getLogger(HtmlToImgUtil.class);

    private static String HTML_TO_IMG_FAIL = "HTML_TO_IMG_FAIL";
    private static String HTML_TO_IMG_SUCCESS = "HTML_TO_IMG_SUCCESS";

    private static Runtime rt = Runtime.getRuntime();
    private static ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<String>(Executors.newFixedThreadPool(2));

    private static HtmlToImgUtil instance = new HtmlToImgUtil();

    public static HtmlToImgUtil getInstance() {
        return instance;
    }

    private class HtmlToImgTask implements Callable<String> {
        private String command;

        public HtmlToImgTask(String command) {
            this.command = command;
        }

        @Override
        public String call() throws Exception {
            try {
                Process p = rt.exec(command);
                InputStream is = p.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuffer sbf = new StringBuffer();
                String tmp = "";
                while ((tmp = br.readLine()) != null) {

                    if (HTML_TO_IMG_FAIL.equals(tmp)) {
                        log.error(HTML_TO_IMG_FAIL);
                        return tmp;
                    }
                    if (HTML_TO_IMG_SUCCESS.equals(tmp)) {
                        log.info(HTML_TO_IMG_SUCCESS);
                        return tmp;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return HTML_TO_IMG_FAIL;
        }
    }

    public static void excCommands(List<String> commands) {
        commands.forEach(command -> executorCompletionService.submit(HtmlToImgUtil.getInstance().new HtmlToImgTask(command)));
    }

    public static String excCommandWithResult(String command) {
        executorCompletionService.submit(HtmlToImgUtil.getInstance().new HtmlToImgTask(command));
        try {

            Future<String> f = executorCompletionService.take();
            String result = f.get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void excCommand(String command) {
        executorCompletionService.submit(HtmlToImgUtil.getInstance().new HtmlToImgTask(command));
        try {

            Future<String> f = executorCompletionService.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String createParams(String url, String imagePath, int viewWidth, int viewHeight, int waitTime) {
        //{'url':'http://www.baidu.com','savePath':'D:\\temp\\baidu.png','logPath':'d:\\temp\\js_log.log','viewWidth':'800','viewHeight':'600','ps_top':'0','ps_left':'0','ps_width':'800','ps_height':'600','waitTime':'2000'}
        StringBuilder sb = new StringBuilder();
        //命令
        sb.append("phantomjs").append(" ");
        //js文件位置
        sb.append("D:\\work_note\\htmlToImg\\sheng_chan\\htmlToImageUtil.js ").append(" ");
        //html URL
        sb.append("{'url':'" + url + "',");
        //日志保存位置
        sb.append("'logPath':'D:/work_note/htmlToImg/sheng_chan/js_log.log',");
        //图片保存位置
        sb.append("'savePath':'" + imagePath + "',");
        //浏览器宽度，浏览器高度
        sb.append("'viewWidth':'" + viewWidth + "','viewHeight':'" + viewHeight + "',");
        //需要切割的位置
        sb.append("'ps_top':'0','ps_left':'0','ps_width':'" + viewWidth + "','ps_height':'" + viewHeight + "',");
        //等待页面加载的时间
        sb.append("'waitTime':'" + waitTime * 1000 + "'}");
        log.info(sb.toString());
        return sb.toString();
    }

    public static void main(String[] args) {
        log.info("test");
        for(int i=0;i<100;i++) {
            String num = ""+i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<String> comannds = new ArrayList<>();
                    String url = "http://www.baidu.com";
                    url = "https://test-static.bestjlb.com/application?tid=44&type=20&orgId=1000000019&activeCode=APP_PIC_HanJiaTeHui&access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMDEwNDA1IiwiaXNvbGF0aW9uIjoiYmVzdGpsYiIsImV4cCI6MTUxNzM4NDI5NCwidHlwZSI6IkFORFJPSUQiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZjljMzQzNzUtNjUzYy00YWQwLTg4MDItMGVmM2NjNDc4MTY5In0.p3TjFC15YUrhgZ3LZHtLp8c9BH8ZzCNcAegg49YxPtTH6NKcZcZhVdx-W2ryodMcYjNPbdjldkzg_Wdy1gfraQ";
                    String imagePath = "D:/work_note/htmlToImg/sheng_chan/util0203_"+num+"_001.png";
                    int viewWidth = 2479;
                    int viewHeight = 3508;
                    int waitTime = 1;
                    comannds.add(createParams(url, imagePath, viewWidth, viewHeight, waitTime));
                    url = "https://test-static.bestjlb.com/application?tid=42&type=18&orgId=1000000019&activeCode=APP_GrabRedPacket&access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMDEwNDA1IiwiaXNvbGF0aW9uIjoiYmVzdGpsYiIsImV4cCI6MTUxNzI4MDQyMSwidHlwZSI6IkFORFJPSUQiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNTdjZDc5YjEtNGY3Yi00ZDAyLWFlMTItNGY5YTFiOTE0NjM3In0.NPSz3DWqE9-Av4u20Bkn4KAS-uCCFM0bB3TEG4zRbemL76d3JDUVX4ebCMX1pwAxNU9p3-RqEMSNE8cyJ9Ei6Q";
                    imagePath = "D:/work_note/htmlToImg/sheng_chan/util0203_"+num+"_002.png";
                    viewWidth = 2479;
                    viewHeight = 3508;
                    waitTime = 1;
                    comannds.add(createParams(url, imagePath, viewWidth, viewHeight, waitTime));

                    excCommands(comannds);
                }
            }).start();

        }
    }
}
