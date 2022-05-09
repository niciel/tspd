package com.gmail.damianmajcherq.tspd.tags;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TagDialog extends JDialog  implements ActionListener , KeyListener {

    private List<String> tags;
    private DefaultTableModel tabelModel;
    private JTable table;
    private JPopupMenu editMenu;
    private SqlTagSearch sqlTag;

    private int selectedRow = 0;

    public TagDialog(Frame parent , SqlTagSearch sql) {
        super(parent,"tag editor" , true);
        this.sqlTag = sql;
        this.editMenu = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Remove");
        removeItem.setActionCommand("removeTag");
        removeItem.addActionListener(this);
        this.editMenu.add(removeItem);
        Point loc = parent.getLocation();
        setLocation(loc.x+80,loc.y+80);
        JPanel frame = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        layout.columnWeights = new double[]{0.5d,0.5d};
        layout.rowWeights = new double[]{0.1d,0.8d,0.1d};
        frame.setLayout(layout);

        initLeft(frame);
        initRight(frame);

        getContentPane().add(frame);
        pack();



    }

    private void initRight(JPanel frame) {

//        TODO
    }


    private Container createTagTable() {
        this.tags = new ArrayList<>();
        this.tags = this.sqlTag.getAllTags();
        DefaultTableModel mod = new DefaultTableModel(new String[]{"nr","tag"},10);

        this.tabelModel = new DefaultTableModel(new String[]{"nr","tag"},10) {
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex == 0)
                    return rowIndex;
                return tags.get(rowIndex);//TODO referencje
            }

            @Override
            public int getRowCount() {
                return tags.size();
            }
        };
        this.table = new JTable(this.tabelModel);
        this.table.setDragEnabled(true);

        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        selectedRow = row;
                        editMenu.show(e.getComponent(),e.getX()+10,e.getY());
                    }
                }
            }
        });
        TableColumn column = this.table.getColumn("nr");
        column.setPreferredWidth(50);
        column.setMaxWidth(50);
        JScrollPane scroll = new JScrollPane(this.table);
        return scroll;
    }


    private JButton bottomAdd;
    private JTextField textSearch;
    private JTextPane foundInfo;

    protected void initLeft(JPanel frame) {
        Container c = new Container();
        c.setLayout(new FlowLayout());
        JTextPane text = new JTextPane();
        text.setText("search:");
        text.setEditable(false);
        c.add(text);


        JTextField textField = new JTextField();
        this.textSearch = textField;
        textField.setColumns(20);
        textField.addKeyListener(this);
        c.add(textField);

        this.foundInfo = new JTextPane();
        this.foundInfo.setEditable(false);
        c.add(this.foundInfo);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        frame.add(c,constraints);



        constraints.gridx = 0;
        constraints.gridy = 1;
        frame.add(createTagTable() , constraints);


        this.bottomAdd = new JButton("add");
        this.bottomAdd.addActionListener(this);
        this.bottomAdd.setActionCommand("addNewTag");
        constraints.gridx = 0;
        constraints.gridy = 2;
        frame.add(this.bottomAdd,constraints);

    }


    protected void updateTableContnet(){
        String tag = this.textSearch.getText();
        if (tag != null && tag.isEmpty() == false) {
            if (Pattern.matches("[a-zA-Z0-9]+",tag)){
                this.tags = this.sqlTag.SearchTags(tag);
                this.tabelModel.fireTableDataChanged();
            }
            else {
                this.textSearch.setText("");
            }
        }
        else {
            this.tags = this.sqlTag.getAllTags();
            this.tabelModel.fireTableDataChanged();
        }
//        TODO get table size;
        this.foundInfo.setText(tags.size()+"/NaN" );
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case ("addNewTag") : {
                String tag = this.textSearch.getText();
                if (tag != null && tag.isEmpty() == false && Pattern.matches("[a-zA-Z0-9]+",tag)) {
                    if (this.tags.stream().filter(f-> !f.equalsIgnoreCase(tag)).findAny().isEmpty()) {
                        this.sqlTag.addTag(tag);
                        updateTableContnet();
                    }
                }
                break;
            }
            case ("removeTag") : {
                this.sqlTag.removeTag(this.tags.get(selectedRow));
                this.updateTableContnet();
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
        this.updateTableContnet();
    }
}
