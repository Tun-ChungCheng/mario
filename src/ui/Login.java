package ui;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel{
    JButton loginButton;

    public Login() {
        setBackground(Color.black);

        loginButton = new JButton("login");
        add(loginButton);
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
