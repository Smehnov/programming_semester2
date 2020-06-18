package client;

import band_data.*;
import server.ServerCommand;
import special.Constants;
import special.Dict;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.bind.JAXBException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class EnterMusicBandFrame extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel NameText = new JLabel(Dict.getTranslation("Name") + "*");
    JLabel XText = new JLabel(Dict.getTranslation("X") + "*");
    JLabel YText = new JLabel(Dict.getTranslation("Y") + "*");
    JLabel NumberParticipantsText = new JLabel(Dict.getTranslation("Number of participants") + "*");
    JLabel GenreText = new JLabel(Dict.getTranslation("Music genre") + "*");
    JLabel NameBestAlbumText = new JLabel(Dict.getTranslation("Name of the best album"));
    JLabel LengthText = new JLabel(Dict.getTranslation("Length"));
    JTextField NameField = new JTextField();
    JTextField XField = new JTextField();
    JTextField YField = new JTextField();
    JTextField NumberParticipantsField = new JTextField();
    String[] genres = {"JAZZ", "SOUL", "BLUES"};
    JComboBox comboBox = new JComboBox(genres);
    JTextField NameBestAlbumField = new JTextField();
    JTextField LengthField = new JTextField();
    JButton EnterButton = new JButton(Dict.getTranslation("Enter"));
    String type;
    MainFrame mainFrame;

    boolean nameIsOk = false;
    boolean xIsOk = false;
    boolean yIsOk = false;
    boolean numberIsOk = false;
    boolean albumNameIsOk = false;
    boolean albumLengthIsOk = false;
    boolean albumDataIsOk = false;


    EnterMusicBandFrame(String type, MainFrame mainFrame) {
        this.type = type;
        this.mainFrame = mainFrame;
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        LengthField.setVisible(false);
        LengthText.setVisible(false);
        mainFrame.setVisible(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mainFrame.setVisible(true);
            }
        });

        NameBestAlbumField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String albumName = NameBestAlbumField.getText().trim();
                if (albumName.length() > 0) {
                    albumNameIsOk = true;
                } else {
                    albumNameIsOk = false;
                }

                if (albumNameIsOk) {
                    LengthField.setVisible(true);
                    LengthText.setVisible(true);
                } else {
                    LengthField.setVisible(false);
                    LengthText.setVisible(false);
                }

            }
        });

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        NameText.setBounds(50, 25, 250, 25);
        NameField.setBounds(50, 50, 250, 25);
        XText.setBounds(50, 75, 100, 25);
        XField.setBounds(50, 100, 100, 25);
        YText.setBounds(200, 75, 100, 25);
        YField.setBounds(200, 100, 100, 25);
        NumberParticipantsText.setBounds(50, 125, 250, 25);
        NumberParticipantsField.setBounds(50, 150, 250, 25);
        GenreText.setBounds(50, 175, 250, 25);
        comboBox.setBounds(50, 200, 250, 25);
        NameBestAlbumText.setBounds(50, 225, 250, 25);
        NameBestAlbumField.setBounds(50, 250, 250, 25);
        LengthText.setBounds(50, 275, 250, 25);
        LengthField.setBounds(50, 300, 250, 25);
        EnterButton.setBounds(125, 350, 100, 50);


    }

    public void addComponentsToContainer() {
        //Adding each components to the Container
        container.add(NameText);
        container.add(NameField);
        container.add(XText);
        container.add(XField);
        container.add(YText);
        container.add(YField);
        container.add(NumberParticipantsText);
        container.add(NumberParticipantsField);
        container.add(GenreText);
        container.add(comboBox);
        container.add(NameBestAlbumText);
        container.add(NameBestAlbumField);
        container.add(LengthText);
        container.add(LengthField);
        container.add(EnterButton);
    }

    public void addActionEvent() {
        EnterButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == EnterButton) {
            String bandName = NameField.getText().trim();
            nameIsOk = bandName.length() > 0;

            if (!nameIsOk) {
                NameField.setBackground(new Color(190, 115, 106));
            } else {
                NameField.setBackground(new Color(255, 255, 255));
            }
            Double bandX = 0d;
            Float bandY = 0f;
            try {
                bandX = Double.parseDouble(XField.getText().trim());
                xIsOk = true;
                XField.setBackground(new Color(255, 255, 255));

            } catch (NumberFormatException ex) {
                xIsOk = false;
                XField.setBackground(new Color(190, 115, 106));
            }

            try {
                bandY = Float.parseFloat(YField.getText().trim());
                yIsOk = true;
                YField.setBackground(new Color(255, 255, 255));

            } catch (NumberFormatException ex) {
                yIsOk = false;
                YField.setBackground(new Color(190, 115, 106));
            }

            int bandNumberOfParticipants = 0;

            try {
                bandNumberOfParticipants = Integer.parseInt(NumberParticipantsField.getText().trim());
                numberIsOk = bandNumberOfParticipants > 0;
            } catch (NumberFormatException ex) {
                numberIsOk = false;
            }
            if (!numberIsOk) {
                NumberParticipantsField.setBackground(new Color(190, 115, 106));
            } else {
                NumberParticipantsField.setBackground(new Color(255, 255, 255));
            }

            String albumName = NameBestAlbumField.getText().trim();
            long albumLength = 0;

            if (albumName.length() > 0) {
                albumNameIsOk = true;
                try {
                    albumLength = Long.parseLong(LengthField.getText().trim());
                    albumLengthIsOk = albumLength > 0;

                } catch (NumberFormatException ex) {
                    albumLengthIsOk = false;

                }

                if (!albumLengthIsOk) {
                    LengthField.setBackground(new Color(190, 115, 106));
                } else {
                    LengthField.setBackground(new Color(255, 255, 255));

                }
            } else {
                albumLengthIsOk = false;
            }

            if ((albumNameIsOk && albumLengthIsOk) || (albumName.length() == 0)) {
                albumDataIsOk = true;
            }
            MusicGenre genre = MusicGenre.valueOf((String) comboBox.getSelectedItem());
            if (nameIsOk && xIsOk && yIsOk && numberIsOk && albumDataIsOk) {
                MusicBand band = new MusicBand();
                band.setName(bandName);
                band.setCoordinates(new Coordinates(bandX, bandY));
                band.setNumberOfParticipants(bandNumberOfParticipants);
                band.setGenre(genre);
                if (albumNameIsOk && albumLengthIsOk) {
                    band.setBestAlbum(new Album(albumName, albumLength));
                }

                //EXECUTE COMMAND BY TYPE
                switch (type) {
                    case "add":
                        //TODO ADD
                        try {
                            String[] commandParams = new String[1];

                            commandParams[0] = MusicBandsDataXMLSerializer.serializeMusicBand(band);
                            ServerCommand serverCommand = new ServerCommand("add", commandParams);
                            serverCommand.setUserLogin(Constants.getUserLogin());
                            serverCommand.setUserPassword(Constants.getUserPassword());
                            String message = serverCommand.serializeToString();

                            String received = ClientSide.sendMessage(message);
                            JOptionPane.showMessageDialog(null, received);

                            mainFrame.showData();
                            this.setVisible(false);
                            mainFrame.setVisible(true);

                            this.dispose();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                        } catch (JAXBException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't serialize music band :(");
                        }
                        break;
                    case "add_if_max":
                        try {
                            String[] commandParams = new String[1];

                            commandParams[0] = MusicBandsDataXMLSerializer.serializeMusicBand(band);
                            ServerCommand serverCommand = new ServerCommand("add_if_max", commandParams);
                            serverCommand.setUserLogin(Constants.getUserLogin());
                            serverCommand.setUserPassword(Constants.getUserPassword());
                            String message = serverCommand.serializeToString();

                            String received = ClientSide.sendMessage(message);
                            JOptionPane.showMessageDialog(null, received);

                            mainFrame.showData();
                            this.setVisible(false);
                            mainFrame.setVisible(true);

                            this.dispose();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                        } catch (JAXBException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't serialize music band :(");
                        }
                        break;
                    case "add_if_min":
                        try {
                            String[] commandParams = new String[1];

                            commandParams[0] = MusicBandsDataXMLSerializer.serializeMusicBand(band);
                            ServerCommand serverCommand = new ServerCommand("add_if_min", commandParams);
                            serverCommand.setUserLogin(Constants.getUserLogin());
                            serverCommand.setUserPassword(Constants.getUserPassword());
                            String message = serverCommand.serializeToString();

                            String received = ClientSide.sendMessage(message);
                            JOptionPane.showMessageDialog(null, received);

                            mainFrame.showData();
                            this.setVisible(false);
                            mainFrame.setVisible(true);

                            this.dispose();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                        } catch (JAXBException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't serialize music band :(");
                        }
                        break;
                    case "remove_greater":
                        try {
                            String[] commandParams = new String[1];

                            commandParams[0] = MusicBandsDataXMLSerializer.serializeMusicBand(band);
                            ServerCommand serverCommand = new ServerCommand("remove_greater", commandParams);
                            serverCommand.setUserLogin(Constants.getUserLogin());
                            serverCommand.setUserPassword(Constants.getUserPassword());
                            String message = serverCommand.serializeToString();

                            String received = ClientSide.sendMessage(message);
                            JOptionPane.showMessageDialog(null, received);

                            mainFrame.showData();
                            this.setVisible(false);
                            mainFrame.setVisible(true);

                            this.dispose();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                        } catch (JAXBException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Can't serialize music band :(");
                        }
                        break;
                    default:
                        if (type.startsWith("update_")) {
                            long band_id = Long.parseLong(type.replace("update_", ""));
                            try {
                                String[] commandParams = new String[2];
                                commandParams[0] = band_id+"";
                                commandParams[1] = MusicBandsDataXMLSerializer.serializeMusicBand(band);
                                ServerCommand serverCommand = new ServerCommand("update", commandParams);
                                serverCommand.setUserLogin(Constants.getUserLogin());
                                serverCommand.setUserPassword(Constants.getUserPassword());
                                String message = serverCommand.serializeToString();

                                String received = ClientSide.sendMessage(message);
                                JOptionPane.showMessageDialog(null, received);

                                mainFrame.showData();
                                this.setVisible(false);
                                mainFrame.setVisible(true);

                                this.dispose();

                            } catch (IOException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Can't connect to server, try to enter command again");
                            } catch (JAXBException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(null, "Can't serialize music band :(");
                            }
                        }
                }


            }


        }
    }
}