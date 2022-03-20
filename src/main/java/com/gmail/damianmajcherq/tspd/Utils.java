package com.gmail.damianmajcherq.tspd;

import javax.swing.*;
import java.awt.*;

public class Utils {

    public static final int DEFAULT_FONT_SIZE;


    static {
        Font defaultFont = UIManager.getDefaults().getFont("TextPane.font");
        DEFAULT_FONT_SIZE = defaultFont.getSize();
    }
}
