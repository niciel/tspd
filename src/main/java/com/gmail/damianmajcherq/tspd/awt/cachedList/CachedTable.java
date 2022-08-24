package com.gmail.damianmajcherq.tspd.awt.cachedList;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CachedTable extends JPanel implements AncestorListener {

    private JScrollPane scroll;
    private JTable table;
    private CachedTableModel model;
    private Timer syncRepeating;

    private boolean suspended = false;


    public CachedTable(CachedTableModel model) {
        this.addAncestorListener(this);

        this.model = model;
        this.table = new JTable(model);
        this.scroll = new JScrollPane(table);
        this.setLayout(new GridLayout(1,1));
        this.add(scroll);
        this.setBorder(BorderFactory.createEmptyBorder());

        this.syncRepeating = new Timer(0,this::syncOperationEDT);
    }


    public int counter = 0;

    protected void syncOperationEDT(ActionEvent actionEvent) {
        if (counter >= 1000) {
            this.model.tickTackToe();
            counter = 0;
        }
        counter++;
        this.updateView();
    }
    int min=0,max=0;
    protected void updateView() {
        Rectangle d = this.scroll.getViewport().getViewRect();
        int a = table.rowAtPoint(new Point(0,d.y));
        int b = table.rowAtPoint(new Point(0,d.y + d.height-1));
        if (a != min || b != max) {
            this.model.UpdateViewSize(a,b);
            this.min = a;
            this.max = b;
        }
    }

    @Override
    public void ancestorAdded(AncestorEvent event) {
        this.model.fireRowCountChanged();
        updateView();
        this.model.fireTableDataChanged();
        //this.table.setEnabled(false);
        //this.scroll.setEnabled(false);
        //this.scroll.setWheelScrollingEnabled(false);
        //this.scroll.getVerticalScrollBar().setEnabled(false);
        this.syncRepeating.start();

    }

    @Override
    public void ancestorRemoved(AncestorEvent event) {
        this.syncRepeating.stop();
    }

    @Override
    public void ancestorMoved(AncestorEvent event) {}




}
