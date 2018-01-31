package com.demo.phantomjs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test_HtmlToImg {
    public static void main(String args[]){
        try{
            Runtime rt = Runtime.getRuntime();
            StringBuilder sb = new StringBuilder();
            //命令
            sb.append("phantomjs").append(" ");

            //js文件位置
            sb.append("D:\\work_note\\htmlToImg\\demo_phantomjs\\p_demo01.js ").append(" ");
            //参数 url
            sb.append("http://www.baidu.com").append(" ");
            //参数 输入文件的位置
            sb.append("D:\\work_note\\htmlToImg\\demo_phantomjs\\baidu_java.pdf").append(" ");

            Process p = rt.exec(sb.toString());//这里我的codes.js是保存在c盘下面的phantomjs目录
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer sbf = new StringBuffer();
            String tmp = "";
            while((tmp = br.readLine())!=null){
                sbf.append(tmp);
            }
            System.out.println(sbf.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
