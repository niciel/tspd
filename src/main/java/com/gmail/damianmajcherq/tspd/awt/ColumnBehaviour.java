package com.gmail.damianmajcherq.tspd.awt;

import java.awt.*;

public abstract class ColumnBehaviour {

    public final String name;


    public ColumnBehaviour(String name) {
        this.name = name;
    }

    public boolean isEditable() {
        return false;
    }

    public abstract Class getDataType();

    public Container getView(Object data,boolean isSelected, boolean hasFocus) {
        return null;
    }

}
