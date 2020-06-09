package client;

import band_data.MusicBand;
import commands.ClearCommand;
import server.ServerCommand;
import special.Constants;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;





public class MainFrame extends JFrame implements ActionListener, TableModelListener {
    Container container = getContentPane();
    JButton AddButton=new JButton("Add");
    JButton AddIfMaxButton=new JButton("Add if max");
    JButton AddIfMinButton=new JButton("Add if min");
    JButton RemoveGreaterButton=new JButton("Remove greater");
    JButton SumParticipantsButton=new JButton("Sum participants");
    JButton InfoButton=new JButton("Info");
    JButton HelpButton=new JButton("Help");
    JButton ClearButton=new JButton("Clear");
    JButton RUButton=new JButton("RU");
    JButton ENButton=new JButton("EN");
    JButton UAButton=new JButton("UA");
    JButton SLButton=new JButton("SL");
    JButton ESButton=new JButton("ES");
    ArrayList<String[]> t= new ArrayList<>();

    String[] col ={"name", "coord_x", "coord_y", "creation_date", "number of participants", "genre", "best album name", "best album length"};
    DefaultTableModel tableModel = new DefaultTableModel(col, 0);

    JTable table = new JTable(tableModel){
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }


    };

    JScrollPane scrollPane = new JScrollPane(table);

    MainFrame(){
        setLocationAndSize();
        setLayoutManager(); //To set the layout manager to null
        addComponentsToContainer();
        showData();
    }

    public void showData(){
        try {
            ServerCommand serverCommand = new ServerCommand("show", new String[0]);
            serverCommand.setUserLogin(Constants.getUserLogin());
            serverCommand.setUserPassword(Constants.getUserPassword());
            String message = serverCommand.serializeToString();

            Object[] received = ClientSide.sendGUIMessage(message);
            if(received[0].equals("message")){
                JOptionPane.showMessageDialog(null,received[1]);
            }
            if(received[0].equals("bands")){
                System.out.println(received[1]);
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    tableModel.removeRow(i);

                }
                ArrayList<MusicBand> bands = (ArrayList<MusicBand>)received[1];
                for (MusicBand band:
                     bands) {
                    tableModel.addRow(band.toTableRow());
                }


            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Error while sending message to server...");
        }
    }
    public void updateTableData(String[][] data){

        }

    public void setLayoutManager()
    {
        container.setLayout(null);
    }

    public void setLocationAndSize()
    {

        AddButton.setBounds(25,25,100,25);
        AddIfMaxButton.setBounds(150,25,100,25);
        AddIfMinButton.setBounds(275,25,100,25);
        ClearButton.setBounds(25,75,100,25);
        HelpButton.setBounds(150,75,100,25);
        InfoButton.setBounds(275,75,100,25);
        SumParticipantsButton.setBounds(25,125,150,25);
        RemoveGreaterButton.setBounds(225,125,150,25);
        RUButton.setBounds(500,25,50,25);
        ENButton.setBounds(500,50,50,25);
        UAButton.setBounds(500,75,50,25);
        SLButton.setBounds(500,100,50,25);
        ESButton.setBounds(500,125,50,25);
        scrollPane.setBounds(10,200,700,300);


    }
    public void addComponentsToContainer()
    {
        container.add(AddButton);
        container.add(AddIfMaxButton);
        container.add(AddIfMinButton);
        container.add(RemoveGreaterButton);
        container.add(SumParticipantsButton);
        container.add(ClearButton);
        container.add(InfoButton);
        container.add(HelpButton);
        container.add(RUButton);
        container.add(ENButton);
        container.add(UAButton);
        container.add(SLButton);
        container.add(ESButton);
        container.add(scrollPane);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
    }
}