package client;

import band_data.MusicBand;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;
import server.ServerCommand;
import special.Constants;
import special.Dict;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
public class MainFrame extends JFrame implements ActionListener, TableModelListener {
    Container container = getContentPane();
    JButton AddButton = new JButton(Dict.getTranslation("Add"));
    JButton AddIfMaxButton = new JButton(Dict.getTranslation("Add if max"));
    JButton AddIfMinButton = new JButton(Dict.getTranslation("Add if min"));
    JButton RemoveGreaterButton = new JButton(Dict.getTranslation("Remove greater"));
    JButton SumParticipantsButton = new JButton(Dict.getTranslation("Sum participants"));
    JButton InfoButton = new JButton(Dict.getTranslation("Info"));
    JButton HelpButton = new JButton(Dict.getTranslation("Help"));
    JButton ClearButton = new JButton(Dict.getTranslation("Clear"));
    JButton RUButton = new JButton("RU");
    JButton UAButton = new JButton("UA");
    JButton SLButton = new JButton("SL");
    JButton ESButton = new JButton("ES");
    MainFrame mainFrame = this;
    ArrayList<String[]> t = new ArrayList<>();
    ArrayList<JLabel> notes = new ArrayList<>();
    JLabelNote rotatingNote = null;


    ArrayList<MusicBand> musicBands = new ArrayList<>();
    String[] col = {"id", "name", "coord_x", "coord_y", "creation_date", "number of participants", "genre", "best album name", "best album length"};
    DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return Long.class;
                case 1:
                    return String.class;
                case 2:
                    return Double.class;
                case 3:
                    return Float.class;
                case 4:
                    return java.time.LocalDateTime.class;
                case 5:
                    return Long.class;
                case 6:
                    return String.class;
                case 7:
                    return String.class;
                case 8:
                    return Long.class;
                default:
                    return String.class;
            }
        }
    };
    List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);

    class RotateNote extends TimerTask {
        int step = 0;

        public void run() {
            if( step>=32){
                this.cancel();
                rotatingNote.setAngle(0);
            }
            if (rotatingNote != null) {
                rotatingNote.setAngle(rotatingNote.getAngle() + Math.PI / 32);
                rotatingNote.repaint();
                step+=1;
            }
        }
    }

    Graphics2D graphics;
    JTable table = new JTable(tableModel) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);


    public class JPanelForDrawing extends JPanel {
        Graphics2D g2;

        public void drawNote(Graphics2D g2, double x, double y) {

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);


            g2 = (Graphics2D) g;
            for (MusicBand band :
                    musicBands) {
                drawNote(g2, band.getCoordinates().getX(), band.getCoordinates().getY());
            }
            g2.setColor(Color.BLACK);

            g2.drawRect(0, 0, 200, 180);
        }
    }

    public class JLabelNote extends JLabel {

        public JLabelNote(ImageIcon img) {
            super(img);
        }

        public JLabelNote() {
            super();
        }

        private long id = 0;
        private double angle = 0;

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public void rotate() {
            rotatingNote = this;
            Timer timer = new Timer();
            timer.schedule(new RotateNote(), 0, 50);
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

    }


    JScrollPane scrollPane = new JScrollPane(table);

    JPanel mapPanel = new JPanel(null);


    MainFrame() {


        addEventListeners();

        setLocationAndSize();
        setLayoutManager(); //To set the layout manager to null
        addComponentsToContainer();
        showData();
        table.setRowSorter(sorter);


        sorter.setSortKeys(sortKeys);


    }

    void addNotes() {
        for (JLabel pic : notes) {
            mapPanel.add(pic);
        }
    }

    void clearNotes() {
        for (JLabel pic : notes) {
            mapPanel.remove(pic);
        }
        notes.clear();
    }

    void updateText() {
        AddButton.setText(Dict.getTranslation("Add"));
        AddIfMaxButton.setText(Dict.getTranslation("Add if max"));
        AddIfMinButton.setText(Dict.getTranslation("Add if min"));
        RemoveGreaterButton.setText(Dict.getTranslation("Remove greater"));
        SumParticipantsButton.setText(Dict.getTranslation("Sum participants"));
        InfoButton.setText(Dict.getTranslation("Info"));
        HelpButton.setText(Dict.getTranslation("Help"));
        ClearButton.setText(Dict.getTranslation("Clear"));

    }

    void addEventListeners() {
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {

                int r = table.rowAtPoint(e.getPoint());
                if (r >= 0 && r < table.getRowCount()) {
                    table.setRowSelectionInterval(r, r);
                } else {
                    table.clearSelection();
                }


                int rowindex = table.getSelectedRow();
                if (rowindex < 0) {
                    return;
                }
                if (e.getButton() == 3) {
                    System.out.println(rowindex);
                    long band_id = (Long) tableModel.getValueAt(rowindex, 0);
                    System.out.println(band_id);

                    JPopupMenu popup = new JPopupMenu();
                    JMenuItem editItem = new JMenuItem("Edit");
                    JMenuItem deleteItem = new JMenuItem("Delete");
                    popup.add(editItem);
                    popup.add(deleteItem);

                    editItem.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            super.mouseReleased(e);
                            System.out.println("Updating band with id " + band_id);

                            EnterMusicBand.run("update_" + band_id, Dict.getTranslation("Edit"), mainFrame);
                        }
                    });

                    deleteItem.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            super.mouseReleased(e);
                            System.out.println("Deleting band with id " + band_id);

                            try {
                                String[] commandParams = {band_id + ""};

                                ServerCommand serverCommand = new ServerCommand("remove_by_id", commandParams);
                                serverCommand.setUserLogin(Constants.getUserLogin());
                                serverCommand.setUserPassword(Constants.getUserPassword());
                                String message = serverCommand.serializeToString();

                                String received = ClientSide.sendMessage(message);
                                JOptionPane.showMessageDialog(null, received);
                            } catch (IOException ee) {
                                ee.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                            }
                            showData();

                        }
                    });


                    popup.setVisible(true);
                    popup.show(e.getComponent(), e.getX(), e.getY());

                }
            }
        });


        ClearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

                try {
                    String[] commandParams = null;

                    ServerCommand serverCommand = new ServerCommand("clear", commandParams);
                    serverCommand.setUserLogin(Constants.getUserLogin());
                    serverCommand.setUserPassword(Constants.getUserPassword());
                    String message = serverCommand.serializeToString();

                    String received = ClientSide.sendMessage(message);
                    JOptionPane.showMessageDialog(null, received);
                } catch (IOException ee) {
                    ee.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                }

                showData();

            }
        });

        AddButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                EnterMusicBand.run("add", "Add band", mainFrame);
            }
        });
        InfoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    ServerCommand serverCommand = new ServerCommand("info", new String[0]);
                    serverCommand.setUserLogin(Constants.getUserLogin());
                    serverCommand.setUserPassword(Constants.getUserPassword());
                    String message = serverCommand.serializeToString();

                    String received = ClientSide.sendMessage(message);

                    JOptionPane.showMessageDialog(null, received);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error while sending message to server...");
                }
            }
        });

        HelpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    String[] commandParams = null;

                    ServerCommand serverCommand = new ServerCommand("help", commandParams);
                    serverCommand.setUserLogin(Constants.getUserLogin());
                    serverCommand.setUserPassword(Constants.getUserPassword());
                    String message = serverCommand.serializeToString();

                    String received = ClientSide.sendMessage(message);
                    JOptionPane.showMessageDialog(null, received);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                }
            }
        });
        RUButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Dict.setCurrentLang("ru");
                updateText();
                showData();
            }
        });
        UAButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Dict.setCurrentLang("ua");
                updateText();
                showData();
            }
        });
        SLButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Dict.setCurrentLang("sl");
                updateText();
                showData();
            }
        });
        ESButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Dict.setCurrentLang("es");
                updateText();
                showData();
            }
        });

        SumParticipantsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    String[] commandParams = null;

                    ServerCommand serverCommand = new ServerCommand("sum_of_number_of_participants", commandParams);
                    serverCommand.setUserLogin(Constants.getUserLogin());
                    serverCommand.setUserPassword(Constants.getUserPassword());
                    String message = serverCommand.serializeToString();

                    String received = ClientSide.sendMessage(message);
                    JOptionPane.showMessageDialog(null, received);

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                }
            }
        });

    }

    public void clickedOnBandId(long id) {
        for (MusicBand band : musicBands) {
            if (band.getId() == id) {
                JOptionPane.showMessageDialog(null, band.toString());

                break;
            }
        }
    }

    public void showData() {
        try {
            ServerCommand serverCommand = new ServerCommand("show", new String[0]);
            serverCommand.setUserLogin(Constants.getUserLogin());
            serverCommand.setUserPassword(Constants.getUserPassword());
            String message = serverCommand.serializeToString();

            Object[] received = ClientSide.sendGUIMessage(message);
            if (received[0].equals("message")) {
                JOptionPane.showMessageDialog(null, received[1]);
            }
            if (received[0].equals("bands")) {
                System.out.println(received[1]);
                int rowCount = tableModel.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    try {
                        tableModel.removeRow(0);
                    } catch (IndexOutOfBoundsException e) {
                        //жизнь боль
                    }

                }

                ArrayList<MusicBand> bands = (ArrayList<MusicBand>) received[1];
                boolean should_rotate = bands.size()-musicBands.size()==1;

                musicBands.clear();

                File f = new File("note.png");
                BufferedImage img = ImageIO.read(f);
                clearNotes();
                long max_id = -1;


                for (MusicBand band :
                        bands) {
                    musicBands.add(band);
                    tableModel.addRow(band.toTableRow());

                    JLabelNote picLabel = new JLabelNote() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            Graphics2D g2 = (Graphics2D) g;

                            g2.rotate(this.getAngle(), img.getWidth() / 2, img.getHeight() / 2);
                            g2.drawImage(img, 0, 0, null);
                        }
                    };
                    picLabel.repaint();


                    picLabel.setId(band.getId());
                    picLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            super.mouseReleased(e);
                            clickedOnBandId(picLabel.getId());
                        }
                    });
                    picLabel.setBounds((int) (band.getCoordinates().getX() + 0), (int) (band.getCoordinates().getY() + 0), 16, 16);
                    if (should_rotate && band.getId()>max_id) {

                        picLabel.rotate();
                        max_id=band.getId();
                    }
                    System.out.println(picLabel);
                    notes.add(picLabel);

//                    mapPanel.drawNote(band.getCoordinates().getX(), band.getCoordinates().getY());
                }

                addNotes();
                mapPanel.repaint();


            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while sending message to server...");
        }
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {

        AddButton.setBounds(25, 25, 160, 25);
        AddIfMaxButton.setBounds(180, 25, 160, 25);
        AddIfMinButton.setBounds(335, 25, 160, 25);
        ClearButton.setBounds(25, 75, 160, 25);
        HelpButton.setBounds(180, 75, 160, 25);
        InfoButton.setBounds(335, 75, 160, 25);
        SumParticipantsButton.setBounds(25, 125, 160, 25);
        RemoveGreaterButton.setBounds(180, 125, 160, 25);
        RUButton.setBounds(500, 25, 50, 25);
        UAButton.setBounds(500, 50, 50, 25);
        SLButton.setBounds(500, 75, 50, 25);
        ESButton.setBounds(500, 100, 50, 25);
        scrollPane.setBounds(10, 200, 850, 300);
        mapPanel.setBounds(550, 10, 300, 190);
    }

    public void addComponentsToContainer() {
        container.add(AddButton);
        container.add(AddIfMaxButton);
        container.add(AddIfMinButton);
        container.add(RemoveGreaterButton);
        container.add(SumParticipantsButton);
        container.add(ClearButton);
        container.add(InfoButton);
        container.add(HelpButton);
        container.add(RUButton);
        container.add(UAButton);
        container.add(SLButton);
        container.add(ESButton);
        container.add(scrollPane);
        container.add(mapPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel) e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
    }
}