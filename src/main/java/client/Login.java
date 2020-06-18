package client;

import javax.swing.*;
import java.awt.*;

public class Login {
    public static void main(String[] a){
        //Creating object of LoginFrame class and setting some of its properties
        System.out.println(new JLabel() instanceof Component);
        LoginFrame frame=new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10,10,350,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }

}