package com.farid;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.ansi;

public class Main {

    public static void main(String[] args){

        Frame frame = new Frame(new ImageListener() {
            @Override
            public void onImageSelected(String path, String brightness_mapping, Boolean invert, Ansi.Color color) {
                Photo_to_ASCII(path, brightness_mapping, invert, color);

            }
        });

    }

    public static void Photo_to_ASCII(String path, String brightness_mapping, Boolean invert, Ansi.Color color) {

        AnsiConsole.systemInstall();
        BufferedImage img = null;
        int brightness;

        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("ERR");
        }


        BufferedImage newImage = toBufferedImage(img,img.getWidth(),img.getHeight());


        int width = newImage.getWidth();
        int height = newImage.getHeight();

        int[][] brightness_arr = new int[width][height];
        String ASCII = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
        Map<Integer, Character> ASCII_map = new HashMap<>();
        String[][] ASCII_art_arr = new String[height][width];


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color c = new Color(newImage.getRGB(j, i));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int inv_red = 255 - c.getRed();
                int inv_green = 255 - c.getGreen();
                int inv_blue = 255 - c.getBlue();
                if (Objects.equals(brightness_mapping, "Average Mapping")) {
                    brightness = Math.round(((float) (red+green+blue))/3);
                } else if (Objects.equals(brightness_mapping, "Min Max Mapping")) {
                    brightness = Math.round(((float) (Math.max(red, Math.max(green, blue)) + Math.min(red, Math.min(green, blue))) / 2));
                }else{
                    brightness = (int) Math.round(0.21*red + 0.72*green + 0.07*blue);
                }
                int inv_brightness = (int) (0.21*inv_red + 0.72*inv_green + 0.07*inv_blue);
                brightness_arr[j][i] = invert ? inv_brightness : brightness;
            }
        }

        for (int i = 0; i < ASCII.length(); i++) {
            char ch = ASCII.charAt(i);
            ASCII_map.put(i,ch);
        }


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j ++) {
                char ch = ASCII_map.get(Math.round((float) (brightness_arr[j][i]) /4));
                ASCII_art_arr[i][j] = "" + ch + ch + ch;
            }
        }




        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                System.out.print(ASCII_art_arr[i][j]);
                System.out.print(ansi().fg(color).a(ASCII_art_arr[i][j]));
            }
            System.out.println();
        }
        AnsiConsole.systemUninstall();
    }

    public static BufferedImage toBufferedImage(Image img,int width,int height) {
        float aspectRatio = (float) height / width;
        int targetHeight = Math.round(300 * aspectRatio);
        Image tmp = img.getScaledInstance(300, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage bimage = new BufferedImage(
                300,
                targetHeight,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = bimage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return bimage;
    }
}