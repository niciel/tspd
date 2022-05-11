package com.gmail.damianmajcherq.tspd.awt;

import java.awt.*;

public class ColumnBehaviour {

    public final String name;
    public Class dataType;

    public ColumnBehaviour(String name , Class data) {
        this.name = name;
        this.dataType = data;
    }

    public boolean isEditable() {
        return false;
    }

    public Container getView(Object data,boolean isSelected, boolean hasFocus) {
        return null;
    }

}
