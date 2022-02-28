package com.gmail.damianmajcherq.tspd.tags;

import com.gmail.damianmajcherq.tspd.awt.SimpleTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TagDialog extends JDialog  implements ActionListener , KeyListener {

    private List<String> tags;
    private SimpleTableModel tabelModel;
    private JTable table;

    private SqlTagSearch sqlTag;

    public TagDialog(Frame parent , SqlTagSearch sql) {
        super(parent,"tag editor" , true);
        this.sqlTag = sql;
        Point loc = parent.getLocation();
        setLocation(loc.x+80,loc.y+80);
        JPanel frame = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{0.5d,0.5d};
        layout.rowWeights = new double[]{1d};
        frame.setLayout(layout);


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        Container c = initLeft();
        c.setPreferredSize(new Dimension(250,400));
        frame.add(c, constraints);

        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        c = initRight();
        c.setPreferredSize(new Dimension(250,400));

        frame.add(c, constraints);


        getContentPane().add(frame);
        pack();



    }


    private Container initRight() {
        this.tags = new ArrayList<>();
        this.tabelModel = new SimpleTableModel(new String[]{"nr","tag"},new Class[]{Integer.class,String.class}) {
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0)
                    return columnIndex;
                return tags.get(rowIndex);//TODO referencje
            }

            @Override
            public int getRowCount() {
                return tags.size();
            }
        };
        this.table = new JTable(this.tabelModel);
        JScrollPane scroll = new JScrollPane(this.table);
        return scroll;
    }


    private JButton bottomAdd;
    private JTextField textSearch;

    protected Container initLeft() {
        Container c = new Container();
        c.setLayout(new BorderLayout());
        JTextPane text = new JTextPane();
        text.setText("search:");
        text.setEditable(false);
        c.add(text,BorderLayout.LINE_START);

        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(100,text.getHeight()));
        this.textSearch = textField;
        textField.setColumns(1);
        textField.addKeyListener(this);
        c.add(textField,BorderLayout.CENTER);

        this.bottomAdd = new JButton("add");
        this.bottomAdd.addActionListener(this);
        this.bottomAdd.setActionCommand("addNewTag");
        c.add(this.bottomAdd,BorderLayout.PAGE_END);
        return c;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case ("addNewTag") : {
                String tag = this.textSearch.getText();
                if (tag != null && tag.isEmpty() == false && Pattern.matches("[a-zA-Z0-9]+",tag)) {
                    System.out.println("size " + this.tags.size());
                    this.tags.add(tag);
                    this.sqlTag.addTag(tag);
                    this.tabelModel.fireTableDataChanged();
                }
                break;
            }
        }
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        //TOTO
        String to = this.textSearch.getText();
        if (to != null && to.isEmpty() == false) {
            if (Pattern.matches("[a-zA-Z0-9]+",to)) {
                System.out.println("serch: '" + to +"'");
                this.tags = this.sqlTag.SearchTags(to);
                if (tags == null) {
//                    TODO error on sql query close program ?
                    this.tags = new ArrayList<>();
                }
                System.out.println("size " + this.tags.size());
            }
            else {
                //TODO some kind of check validation
                this.tags = new ArrayList<>();
            }
        }
        else {
            this.tags = sqlTag.getAllTags();
        }
        this.tabelModel.fireTableDataChanged();
    }
}