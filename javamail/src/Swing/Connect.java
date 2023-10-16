package Swing;

import Controller.controller;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connect extends JFrame implements ActionListener {
    private JTextField email;
    private JTextField mdp;
    private JButton seConnecterButton;
    private JPanel Panel;
    private JLabel Erreur;

    public Connect() {

        email.setText("cedricromain577@gmail.com");

        setContentPane(Panel);
        Panel.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Connexion");
        setSize(600,400);
        seConnecterButton.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==seConnecterButton)
        {
            try {
                seConnecter();
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void seConnecter() throws MessagingException {
        Erreur.setText(controller.getInstance().Connexion(email.getText(),mdp.getText()));
    }

}
