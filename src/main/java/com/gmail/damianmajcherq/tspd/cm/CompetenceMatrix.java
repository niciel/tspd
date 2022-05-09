package com.gmail.damianmajcherq.tspd.cm;

import com.gmail.damianmajcherq.tspd.ITSPDModule;
import com.gmail.damianmajcherq.tspd.MainSystem;
import com.gmail.damianmajcherq.tspd.awt.CachedJTable;
import com.gmail.damianmajcherq.tspd.awt.CachedTableModel;
import com.gmail.damianmajcherq.tspd.awt.ColumnBehaviour;
import com.gmail.damianmajcherq.tspd.awt.SqlCellRenderer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class CompetenceMatrix implements ITSPDModule {

    @Override
    public void preStart(MainSystem main) {

    }

    @Override
    public void onStart(MainSystem main) {

    }

    @Override
    public void initFrames(MainSystem main) {
        Container c = initCompetenceTabPane();
        main.registerTabPane("competences", c, null);
    }

    private JPanel left;

    protected Container initCompetenceTabPane(){
        Container main = new Container();
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{0.5d,0.5d};
        layout.rowWeights = new double[]{0.1d,0.1d,0.8d};
        main.setLayout(layout);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;

        this.left = new JPanel();
        this.LeftSiteInit(this.left);

        main.add(this.left , c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 2;


        c.fill = GridBagConstraints.BOTH;
        main.add(InitTable(),c);

        return main;
    }

    private JTable groupsTable;
    private Container leftUpperButtons;

    protected void LeftSiteInit(JPanel c) {
        this.leftUpperButtons = new Container();
        FlowLayout flow = new FlowLayout();
        flow.setVgap(5);
        flow.setHgap(5);
        this.leftUpperButtons.setLayout(flow);



        TableModel model = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return 100;
            }

            @Override
            public int getColumnCount() {
                return 4;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0)
                    return rowIndex;
                //if (columnIndex == 1)
                //System.out.println("get : " + rowIndex) ;
                return 1;
            }

            @Override
            public String getColumnName(int column) {
                return "nazwa " + column;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return Integer.class;
            }
        };
        this.groupsTable = new JTable(model){
            private SqlCellRenderer cr = new SqlCellRenderer();

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 0){
                    return cr;
                }
                return super.getCellRenderer(row, column);
            }
        };
        JScrollPane scroll = new JScrollPane(this.groupsTable){
        };
        c.add(scroll);
        this.groupsTable.setFillsViewportHeight(true);
    }


    protected Container InitTable() {
        List<ColumnBehaviour> list = new ArrayList<ColumnBehaviour>();
        list.add(new ColumnBehaviour("nr") {
            @Override
            public Class getDataType() {
                return String.class;
            }
        });

        CachedTableModel model = new CachedTableModel(100,list) {

            @Override
            public boolean fetchData(int lowestRow, int highestRow, @NotNull Object[][] data) {
                //System.out.println("check " + lowestRow + " a " +highestRow);
                for (int i = lowestRow ; i < highestRow;i++){
                    data[i] = new Object[]{lowestRow+i};
                }
                return true;
            }

            @Override
            public int getRowCount() {
                return 100;
            }
        };
        CachedJTable table = new CachedJTable(model);
        /*

        String[] columns = new String[]{"pierwsza","druga","trzecia"};
        Object[][] data = new Object[][]{
                {1,2,3},
                {4,5,6}
        };
        JTable table = new JTable(data,columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JScrollPane scroll = new JScrollPane(table);
        return scroll;
        */


        JScrollPane scroll = new JScrollPane(table);
        return scroll;
    }




}
