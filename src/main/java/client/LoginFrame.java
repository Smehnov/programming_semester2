package client;

import server.ServerCommand;
import special.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Pattern;

public class LoginFrame extends JFrame implements ActionListener {
    Container container = getContentPane();
    JLabel infoText = new JLabel("Enter username and password to sign in/register");
    JLabel infoUsername = new JLabel("Username characters: a-z, A-Z, 0-9. Length>=3");
    JLabel infoPassword = new JLabel("Password length must be >=3? don't use spaces.");
    JLabel userLabel = new JLabel("Username");
    JLabel passwordLabel = new JLabel("Password");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton signinButton = new JButton("SIGN IN");
    JButton registerButton = new JButton("Register");
    JCheckBox showPassword = new JCheckBox("Show Password");
    boolean usernameIsOk = false;
    boolean passwordIsOk = false;


    LoginFrame() {
        //Calling methods inside constructor.
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        //Setting location and Size of each components using setBounds() method.
        infoText.setBounds(10, 0, 400, 40);
        infoUsername.setBounds(10, 40, 400, 40);
        infoPassword.setBounds(10, 80, 400, 40);
        userLabel.setBounds(150, 120, 100, 30);
        passwordLabel.setBounds(150, 200, 100, 30);
        userTextField.setBounds(100, 160, 150, 30);
        passwordField.setBounds(100, 240, 150, 30);
        showPassword.setBounds(100, 270, 150, 30);
        signinButton.setBounds(50, 320, 100, 30);
        registerButton.setBounds(200, 320, 100, 30);


    }

    public void addComponentsToContainer() {
        //Adding each components to the Container
        container.add(infoUsername);
        container.add(infoPassword);
        container.add(infoText);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(signinButton);
        container.add(registerButton);
    }

    public void addActionEvent() {
        //adding Action listener to components
        signinButton.addActionListener(this);
        registerButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Coding Part of SIGNIN button
        if (e.getSource() == signinButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText().trim();
            pwdText = new String(passwordField.getPassword());
            System.out.println("username " + userText);
            System.out.println("password " + pwdText);

            if (Pattern.matches("^[a-zA-Z0-9._-]{3,}$", userText)) {
                usernameIsOk = true;
            }else{
                usernameIsOk = false;
            }

            if (!pwdText.contains(" ") && pwdText.length()>=3){
                passwordIsOk = true;
            }else{
                passwordIsOk = false;
            }

            if (!usernameIsOk) {
                userTextField.setBackground(new Color(190, 115, 106));
            } else {
                userTextField.setBackground(new Color(255, 255, 255));
            }

            if (!passwordIsOk) {
                passwordField.setBackground(new Color(190, 115, 106));
            }else{
                passwordField.setBackground(new Color(255, 255, 255));
            }

            if (usernameIsOk && passwordIsOk) {
                Constants.setUserLogin(userText);
                Constants.setUserPassword(pwdText);
                try {
                    ServerCommand serverCommand = new ServerCommand("login", new String[0]);
                    serverCommand.setUserLogin(Constants.getUserLogin());
                    serverCommand.setUserPassword(Constants.getUserPassword());
                    String message = serverCommand.serializeToString();

                    String received = ClientSide.sendMessage(message);
                    System.out.println(received);

                    if (received.equals("Password invalid") || received.equals("This user doesn't exist")){
                        System.out.printf("Wrong username or password");
                        JOptionPane.showMessageDialog(null, "Wrong username or password");
//                        System.out.println("Exit...");
//                        System.exit(0);
                    }
                    if(received.equals("Can't connect to server") || received.equals("Server is unavailable")){
                        System.out.println("Can't connect to server");
                        JOptionPane.showMessageDialog(null, "Can't connect to server");

                    }

                    if(received.equals("Userdata is ok")){
                        MainWindow.main(new String[]{});
                        this.setVisible(false);
                        this.dispose();
                    }

                } catch (IOException |java.nio.channels.UnresolvedAddressException ee) {
                    System.out.println("Error while sending message to server...");

                    JOptionPane.showMessageDialog(null, "Can't connect to server");
                }
            }


        }
        //Coding Part of Register button
        if (e.getSource() == registerButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = new String(passwordField.getPassword());
            System.out.println("username " + userText);
            System.out.println("password " + pwdText);
        }
        //Coding Part of showPassword JCheckBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

    }
}


