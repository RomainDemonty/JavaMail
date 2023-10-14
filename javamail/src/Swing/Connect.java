package Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect extends JFrame implements ActionListener {
    private JTextField email;
    private JTextField mdp;
    private JButton seConnecterButton;
    private JPanel Panel;

    public Connect() {
        setContentPane(Panel);
        Panel.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Connexion");
        setSize(600,400);
        seConnecterButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
