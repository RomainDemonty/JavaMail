package Swing;

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
        setContentPane(Panel);
        Panel.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Connexion");
        setSize(600,400);
        seConnecterButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==seConnecterButton)
        {
            seConnecter();
        }
    }

    public void seConnecter(){
        if(email.getText().equals("CedricRomain577@gmail.com"))
        {
            if(mdp.getText().equals("lgltdzyyadcqnbpe") || mdp.getText().equals("sozmoxmiamrfqzih"))
            {
                Erreur.setText("corect");
                MiniOutlook min = new MiniOutlook();
                min.setVisible(true);
            }
            else
            {
                Erreur.setText("mot de passe incorect");
            }
        }
        else{
            Erreur.setText("email incorect");
        }
    }
}
