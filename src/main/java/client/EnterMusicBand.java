package client;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class EnterMusicBand {
    public static void main(String[] a){
        EnterMusicBandFrame frame=new EnterMusicBandFrame();
        frame.setTitle("Add button");
        frame.setVisible(true);
        frame.setBounds(10,10,350,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }

}
