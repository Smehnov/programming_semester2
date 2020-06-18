package client;
import special.Dict;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

public class EnterMusicBand {
    public static void run(String type, String title, MainFrame mainFrame){
        EnterMusicBandFrame frame=new EnterMusicBandFrame(type, mainFrame);
        frame.setTitle(Dict.getTranslation(title));
        frame.setVisible(true);
        frame.setBounds(10,10,350,500);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);

    }

}
