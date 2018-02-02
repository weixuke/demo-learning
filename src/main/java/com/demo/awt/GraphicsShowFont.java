package com.demo.awt;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphicsShowFont {
    public static void main(String args[]){
        //show();
        //yPic();
        try {
            mergeImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将宽度相同的图片，竖向追加在一起 ##注意：宽度必须相同
     *
     *            文件路径列表
     *            输出路径
     */
    public static void yPic() {// 纵向处理图片

        List<String> piclist = new ArrayList<>();
        piclist.add("D:\\work_note\\htmlToImg\\demo_phantomjs\\img_gao_qing.png");
        piclist.add("D:\\work_note\\htmlToImg\\demo_phantomjs\\img_gao_qing_copy.png");
        String outPath = "D:\\work_note\\htmlToImg\\demo_phantomjs\\img_gao_qing_hebing.png";

        if (piclist == null || piclist.size() <= 0) {
            System.out.println("图片数组为空!");
            return;
        }
        try {
            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0, // 临时的高度，主要保存每个高度
                    picNum = piclist.size();// 图片的数量
            File fileImg = null; // 保存读取出的图片
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage buffer = null; // 保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            for (int i = 0; i < picNum; i++) {
                fileImg = new File(piclist.get(i));
                buffer = ImageIO.read(fileImg);
                heightArray[i] = _height = buffer.getHeight();// 图片高度
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度
                }
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0) _height += __height; // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
            }
            File outFile = new File(outPath);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mergeImage() throws IOException {
        String path = "D:\\work_note\\htmlToImg\\demo_phantomjs\\";
        File file1 = new File(path, "img_gao_qing.png");
        File file2 = new File(path, "img_gao_qing_copy.png");

        BufferedImage image1 = ImageIO.read(file1);
        BufferedImage image2 = ImageIO.read(file2);

        BufferedImage combined = new BufferedImage(image1.getWidth() * 2, image1.getHeight(), BufferedImage.TYPE_INT_RGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image1, 0, 0, null);
        g.drawImage(image2, image1.getWidth(), 0, null);

        // Save as new image
        ImageIO.write(combined, "JPG", new File(path, "hebing.jpg"));
    }

    public static void show(){
        try {
            String text = "文字居中";
            int width = 500;
            int height = 400;
            // 创建BufferedImage对象
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 获取Graphics2D
            Graphics2D g2d = image.createGraphics();
            // 画图
            g2d.setBackground(new Color(255,255,255));
            //g2d.setPaint(new Color(0,0,0));
            g2d.setColor(Color.BLACK);
            g2d.clearRect(0, 0, width, height);
            Font font=new Font("微软雅黑",Font.PLAIN,20);

            g2d.setFont(font);
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 计算文字长度，计算居中的x点坐标
            FontMetrics fm = g2d.getFontMetrics(font);
            int textWidth = fm.stringWidth(text);
            int widthX = (width - textWidth) / 2;
            // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g2d.drawString(text,widthX,100);
            // 释放对象
            g2d.dispose();

//            g2d = image.createGraphics();
//            g2d.setColor(Color.BLACK);
//            Font font1=new Font("Verdana",Font.PLAIN,30);
            // 保存文件
            ImageIO.write(image, "jpg", new File("./test.jpg"));
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
