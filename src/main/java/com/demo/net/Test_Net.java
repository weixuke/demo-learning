package com.demo.net;

import sun.misc.IOUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class Test_Net {
    public static void main(String args[]){
        try {
            String str_url = "http://10.10.10.30:3000/";
            URL url = new URL(str_url);
            URLConnection urlcon = url.openConnection();
            InputStream is = urlcon.getInputStream();
            StringBuilder out = new StringBuilder();
            Reader in = new InputStreamReader(is, "UTF-8");
            int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }

            System.out.println(out.toString());
        }catch (Exception e){

        }

    }
}
