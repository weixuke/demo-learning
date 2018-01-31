package com.demo.htmlInput;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlInput {
    public static void main(String args[]){
        try {
            String url = "https://test-static.bestjlb.com/application?tid=42&type=18&orgId=1000000019&activeCode=APP_GrabRedPacket&access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMDEwNDA1IiwiaXNvbGF0aW9uIjoiYmVzdGpsYiIsImV4cCI6MTUxNzI4MDQyMSwidHlwZSI6IkFORFJPSUQiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNTdjZDc5YjEtNGY3Yi00ZDAyLWFlMTItNGY5YTFiOTE0NjM3In0.NPSz3DWqE9-Av4u20Bkn4KAS-uCCFM0bB3TEG4zRbemL76d3JDUVX4ebCMX1pwAxNU9p3-RqEMSNE8cyJ9Ei6Q";
            //url = "http://10.10.10.30:3000/";
            final WebClient webClient = new WebClient();

            // 1 启动JS
            webClient.getOptions().setJavaScriptEnabled(true);
            // 2 禁用Css，可避免自动二次请求CSS进行渲染
            webClient.getOptions().setCssEnabled(false);
            // 3 启动客户端重定向
            webClient.getOptions().setRedirectEnabled(true);

            // 4 js运行错误时，是否抛出异常
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            // 5 设置超时
            webClient.getOptions().setTimeout(50000);

            HtmlPage htmlPage = webClient.getPage(url);
            // 等待JS驱动dom完成获得还原后的网页
            webClient.waitForBackgroundJavaScript(10000);
            // 网页内容
            System.out.println(htmlPage.asXml());
            webClient.close();
        }catch (Exception e){

        }
    }
}
