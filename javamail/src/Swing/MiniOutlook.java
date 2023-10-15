package Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MiniOutlook extends JFrame implements ActionListener {
    private JTabbedPane menu;
    private JPanel panel1;
    private JTextPane texte;
    private JTextField destinataire;
    private JTextField objet;
    private JButton ajouterUnePièceJointeButton;
    private JButton envoyerButton;
    private JButton actualiserButton;

    private String lien ;



    public MiniOutlook() {
        panel1.setVisible(true);
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MiniOutlook");
        setSize(800,600);

        ajouterUnePièceJointeButton.addActionListener(this);
        envoyerButton.addActionListener(this);

        // configurer la liaison avec le smails




        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ajouterUnePièceJointeButton)
        {
            ajouterPieceJointe();
            System.out.println(lien);
        }
        if(e.getSource()==envoyerButton)
        {
            envoyer();
        }
    }
    public void ajouterPieceJointe() {
        System.out.println("hello piece jointe");
        JFileChooser fileChooser = new JFileChooser();

        // Affiche la boîte de dialogue de sélection de fichier
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Récupère le chemin d'accès au fichier sélectionné
            lien = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Chemin d'accès au fichier sélectionné : " + lien);
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }
    public void envoyer() {
        System.out.println("test d'envois");
        System.out.println("destinataire = "+ destinataire.getText());
        System.out.println("objet = "+ objet.getText());
        System.out.println("text = "+ texte.getText());

        // ici que on va utiliser le systeme  mail et envoyer
        // pas oublier de reset les champs
    }

}

