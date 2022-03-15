package com.gmail.damianmajcherq.tspd.icons;

import javax.swing.*;
import java.awt.*;

public final class IconUtil {


    public static void paintEmployed(Graphics2D g, int x,int y ,int size){
        g.setColor(Color.BLUE);
        g.drawLine(x,y,x,y+size);
        g.drawLine(x,y,x+size,y);
        int h = size/2;
        g.drawLine(x,y+h,x+size,y+h);
        g.drawLine(x,y+size ,x+size,y+size);
    }

}
