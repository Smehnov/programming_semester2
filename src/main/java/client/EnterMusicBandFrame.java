package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterMusicBandFrame extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel NameText = new JLabel("Name*");
    JLabel XText = new JLabel("X*");
    JLabel YText = new JLabel("Y*");
    JLabel NumberParticipantsText = new JLabel("Number of participants*");
    JLabel GenreText = new JLabel("Music genre*");
    JLabel NameBestAlbumText = new JLabel("Name of the best album");
    JLabel LengthText = new JLabel("Length");
    JTextField NameField = new JTextField();
    JTextField XField = new JTextField();
    JTextField YField = new JTextField();
    JTextField NumberParticipantsField = new JTextField();
    String[] genres = {"JAZZ", "SOUL", "BLUES"};
    JComboBox comboBox = new JComboBox(genres);
    JTextField NameBestAlbumField = new JTextField();
    JTextField LengthField = new JTextField();
    JButton EnterButton = new JButton("Enter");

    EnterMusicBandFrame(){
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
    public void setLayoutManager() { container.setLayout(null); }
    public void setLocationAndSize() {
        NameText.setBounds(50,25,250,25);
        NameField.setBounds(50,50,250,25);
        XText.setBounds(50,75,100,25);
        XField.setBounds(50,100,100,25);
        YText.setBounds(200,75,100,25);
        YField.setBounds(200,100,100,25);
        NumberParticipantsText.setBounds(50,125,250,25);
        NumberParticipantsField.setBounds(50,150,250,25);
        GenreText.setBounds(50,175,250,25);
        comboBox.setBounds(50,200,250,25);
        NameBestAlbumText.setBounds(50,225,250,25);
        NameBestAlbumField.setBounds(50,250,250,25);
        LengthText.setBounds(50,275,250,25);
        LengthField.setBounds(50,300,250,25);
        EnterButton.setBounds(125,350,100,50);

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

    }
}