import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.table.*;
import javax.swing.JScrollPane;
import java.util.LinkedList;
import java.lang.Exception;
import javax.swing.JFileChooser;
import java.io.File;

public class MyFrame extends JFrame implements KeyListener{
    private JPanel contentPane;
    private JButton addButton;
    private JButton deleteButton;
    private JButton sortButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField textId;
    private JTextField textCreator;
    private JTextField textFrequency;
    private JTextField textRam;
    private JTable table;
    private JButton updateButton;
    private JScrollPane scrollPane;

    @Override
    public void keyTyped(KeyEvent e) {
        updateButton.setEnabled( ((e.getSource()==textId || e.getSource()==textCreator ||
                e.getSource()==textFrequency||e.getSource()==textRam)&&(table.getSelectedRow()>=0)) );
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    public MyFrame(){
        super("Notebook models");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 280);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblId = new JLabel("Id");
        lblId.setBounds(10, 11, 29, 14);
        contentPane.add(lblId);

        JLabel lblCreator = new JLabel("Creator");
        lblCreator.setBounds(120, 11, 46, 14);
        contentPane.add(lblCreator);

        JLabel lblFrequency = new JLabel("Frequency");
        lblFrequency.setBounds(230, 11, 72, 14);
        contentPane.add(lblFrequency);

        JLabel lblRam = new JLabel("RAM");
        lblRam.setBounds(340, 11, 72, 14);
        contentPane.add(lblRam);

        textId = new JTextField();
        textId.setBounds(10, 30, 100, 20);
        textId.setText("001");
        contentPane.add(textId);
        textId.setColumns(10);
        textId.addKeyListener(this);

        textCreator = new JTextField();
        textCreator.setText("Asus");
        textCreator.setBounds(120,30,100,20);
        contentPane.add(textCreator);
        textCreator.setColumns(10);
        textCreator.addKeyListener(this);

        textFrequency = new JTextField();
        textFrequency.setBounds(230,30,100,20);
        textFrequency.setText("2.8");
        contentPane.add(textFrequency);
        textFrequency.setColumns(10);
        textFrequency.addKeyListener(this);

        textRam = new JTextField();
        textRam.setBounds(340,30,100,20);
        textRam.setText("8");
        contentPane.add(textRam);
        textRam.setColumns(10);
        textRam.addKeyListener(this);

        final LinkedList <Notebook> tbl=new LinkedList <Notebook> ();
        tbl.add(new Notebook("001","Asus",2.8,8));//добавление элементов в коллекцию
        final NotebookTableModel ntb=new NotebookTableModel(tbl); // объявление переменной экземпляра класса модели таблицы

        JButton btnAdd = new JButton("Добавить");
        btnAdd.setBounds(340,60,120,20);
        contentPane.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ntb.addRow(new Notebook(textId.getText(), textCreator.getText(),
                            Double.parseDouble(textFrequency.getText()), Double.parseDouble(textRam.getText())));
                }
                catch (Exception exc){}
            }
        });

        updateButton = new JButton("Изменить");
        updateButton.setBounds(340,120,120,20);
        updateButton.setEnabled(false);
        contentPane.add(updateButton);
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (table.getSelectedRow() >= 0) ntb.updateRow(table.getSelectedRow(),
                            new Notebook(textId.getText(), textCreator.getText(),
                                    Double.parseDouble(textFrequency.getText()),
                                    Double.parseDouble(textRam.getText())),table);
                    else JOptionPane.showMessageDialog(MyFrame.this, "Не выбрана ни одна строка таблицы");
                }
                catch (Exception ecx){}
            }
        });

        deleteButton = new JButton("Удалить");
        deleteButton.setBounds(340,90,120,20);
        contentPane.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!ntb.deleteRow(Double.parseDouble(textRam.getText())))
                    JOptionPane.showMessageDialog(MyFrame.this,"Ноутбук с '"+textRam.getText()+"' оперативной памяти не найден!");;
            }
        });

        sortButton = new JButton("Сортировка");
        sortButton.setBounds(340,150,120,20);
        contentPane.add(sortButton);
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ntb.sort();
            }
        });

        saveButton = new JButton("Сохранить");
        saveButton.setBounds(340,180,120,20);
        contentPane.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(MyFrame.this, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    try {
                        FileWriter fr = new FileWriter(file);
                        fr.write(tbl.toString());
                        fr.close();
                    }
                    catch (Exception ex){}
                }
            }
        });

        loadButton = new JButton("Загрузить");
        loadButton.setBounds(340,210,120,20);
        contentPane.add(loadButton);
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(MyFrame.this, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileopen.getSelectedFile();
                    try {
                        LinkedListReader.readFileToLinkedList(file, tbl,table);

                    }
                    catch (Exception ex){}
                }
            }
        });

        table = new JTable(ntb);
        table.setBounds(10, 60, 300, 60);
        table.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e){
                int row=table.getSelectedRow();
                textId.setText(table.getValueAt(row, 0).toString());
                textCreator.setText(table.getValueAt(row, 1).toString());
                textFrequency.setText(table.getValueAt(row, 2).toString());
                textRam.setText(table.getValueAt(row, 3).toString());
                updateButton.setEnabled(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {};});
        contentPane.add(table);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 60, 320, 180);
        contentPane.add(scrollPane);

    }
}