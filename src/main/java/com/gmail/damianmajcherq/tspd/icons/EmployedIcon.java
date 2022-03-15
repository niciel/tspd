package com.gmail.damianmajcherq.tspd.icons;

import javax.swing.*;
import java.awt.*;

public class EmployedIcon implements Icon {

    private int size;

    public EmployedIcon(int size) {
        this.size = size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        IconUtil.paintEmployed(g2d, x,y,this.size);
        g2d.dispose();
    }



    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}
