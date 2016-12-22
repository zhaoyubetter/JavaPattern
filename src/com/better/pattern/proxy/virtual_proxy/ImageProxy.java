package com.better.pattern.proxy.virtual_proxy;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * 代理类
 * Created by zhaoyu on 2016/12/22.
 */
public class ImageProxy implements Icon {

    ImageIcon imageIcon;
    URL imageUrl;
    Thread loadThread;
    boolean isLoaded = false;

    public ImageProxy(URL imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int getIconWidth() {
        if (imageIcon != null) {
            return imageIcon.getIconWidth();
        }
        return 800;
    }

    @Override
    public int getIconHeight() {
        if (imageIcon != null) {
            return imageIcon.getIconHeight();
        }
        return 600;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (imageIcon != null) {
            imageIcon.paintIcon(c, g, x, y);
        } else {
            g.drawString("正在加载图片，请等待....", x + 300, y + 200);
            if (!isLoaded) {
                isLoaded = true;

                loadThread = new Thread(() -> {
                    try {
                        imageIcon = new ImageIcon(imageUrl, "Image Cover");
                        c.repaint();        // 重绘
                    } catch (Exception e) {

                    }
                });

                loadThread.start();
            }
        }
    }
}
