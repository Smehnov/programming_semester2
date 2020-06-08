package client;

import javax.swing.*;

public class MainWindow {
    public static void main(String[] a){
        MainFrame frame=new MainFrame();
        frame.setTitle("Main window");
        frame.setVisible(true);
        frame.setBounds(0,0,800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }

}