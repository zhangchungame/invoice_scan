package com.dandinglong;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BuildImageDemo {
    public static void main(String[] args) {
        String backgroundPath = "f:/share_template.jpg";
        String qrCodePath = "f:/aaa.jpg";
        String avatarPath = "f:/132.jpg";
        String message01 ="张春--少儿编程";
        String outPutPath="f:/end.jpg";
        overlapImage(backgroundPath,qrCodePath,avatarPath,message01,outPutPath);
    }

    public static String overlapImage(String backgroundPath,String qrCodePath,String avatarPath,String message01,String outPutPath){
        try {
            //设置图片大小
//            BufferedImage background = resizeImage(618,1000, ImageIO.read(new File("这里是背景图片的路径！")));
            BufferedImage background = ImageIO.read(new File(backgroundPath));
//            BufferedImage qrCode = resizeImage(150,150,ImageIO.read(new File("这里是插入二维码图片的路径！")));
            BufferedImage qrCode = resizeImage(150,150,ImageIO.read(new File(qrCodePath)));
            BufferedImage avatar = resizeImage(50,50,ImageIO.read(new File(avatarPath)));
            //在背景图片中添加入需要写入的信息，例如：扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！
            //String message = "扫描下方二维码，欢迎大家添加我的淘宝返利机器人，居家必备，省钱购物专属小秘书！";
            Graphics2D g = background.createGraphics();
            g.setColor(Color.ORANGE);
            g.setFont(new Font("微软雅黑",Font.BOLD,20));
            g.drawString(message01,90 ,240);
            //在背景图片上添加二维码图片
            g.drawImage(qrCode, 400, 300, qrCode.getWidth(), qrCode.getHeight(), null);
            g.drawImage(avatar, 30, 200, avatar.getWidth(), avatar.getHeight(), null);
            g.dispose();
//            ImageIO.write(background, "jpg", new File("这里是一个输出图片的路径"));
            ImageIO.write(background, "jpg", new File(outPutPath));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage resizeImage(int x, int y, BufferedImage bfi){
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(
                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }

}
