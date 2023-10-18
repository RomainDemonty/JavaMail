package Swing;

import Controller.controller;

import javax.mail.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Enumeration;

import static jdk.jfr.consumer.EventStream.openFile;

public class MiniOutlook extends JFrame implements ActionListener {
    private JTabbedPane menu;
    private JPanel panel1;
    private JTextPane texte;
    private JTextField destinataire;
    private JTextField objet;
    private JButton ajouterUnePièceJointeButton;
    private JButton envoyerButton;
    private JButton actualiserButton;
    private JLabel Erreur;
    private JScrollPane mailListScrollPane;
    private JScrollPane mailScrollPane;
    private JLabel mailfrom;
    private JLabel mailsubject;
    private JLabel mailTexte;
    private JLabel pieceJointe;
    private JLabel headermail;
    private JPanel panelmail;
    private JPanel maillistpanel;
    private JButton Ouvrir;
    private JLabel nomPJ;

    private String lien ;

   private  ButtonGroup buttonGroup = new ButtonGroup();

   private String selectedFileName ;

    public MiniOutlook() throws MessagingException {

        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MiniOutlook");
        setSize(800,600);
        Ouvrir.setVisible(false);

        ajouterUnePièceJointeButton.addActionListener(this);
        envoyerButton.addActionListener(this);
        actualiserButton.addActionListener(this);
        Ouvrir.addActionListener(this);
        // configurer la liaison avec le smails


        maillistpanel.setLayout(new GridLayout(0,1));
        for( int i = 0 ; i < controller.getInstance().getListMsg().size() ; i++)
        {
            Message m = controller.getInstance().getListMsg().get(i);
           JRadioButton jrb= new JRadioButton("<html> FROM : " + m.getFrom()[0].toString() +"<br>" +
                    "Subject : " +  m.getSubject() + "</html>");
           jrb.addActionListener(this);
            maillistpanel.add(jrb);
            buttonGroup.add((jrb));
        }
        mailListScrollPane.setViewportView(maillistpanel);


        panel1.setVisible(true);
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
        if(e.getSource()==actualiserButton)
        {
            try {
                recevoir();
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() instanceof JRadioButton)
        {
            try {
                ouvrirMail((JRadioButton) e.getSource());
            } catch (MessagingException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if(e.getSource() ==  Ouvrir)
        {
            openFile(selectedFileName);
        }

        this.getContentPane().repaint();
        this.getContentPane().revalidate();
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
            nomPJ.setText(lien);
        } else {
            System.out.println("Aucun fichier sélectionné.");
        }
    }
    public void envoyer() {
        Erreur.setText(controller.getInstance().Envoyer(destinataire.getText(),objet.getText(),texte.getText(),lien));
        if(Erreur.getText()=="E-mail envoyé avec succès !")
        {
            objet.setText("");
            texte.setText("");
            destinataire.setText("");
            nomPJ.setText("");
            lien=null;
        }

    }
    public void  recevoir() throws MessagingException {
        int tmp = controller.getInstance().getListMsg().size();

        controller.getInstance().Recevoir();

        maillistpanel.removeAll();
        for (int i = 0; i < controller.getInstance().getListMsg().size(); i++) {
            Message m = controller.getInstance().getListMsg().get(i);
            JRadioButton jrd = new JRadioButton("<html> FROM : " + m.getFrom()[0].toString() + "<br>" +  "Subject : " + m.getSubject() + "</html>");
            maillistpanel.add(jrd);
            buttonGroup.add(jrd);
            jrd.addActionListener(this);

        }
    }

    public void ouvrirMail( JRadioButton e ) throws MessagingException, IOException {
       JPanel jlist = (JPanel) mailListScrollPane.getViewport().getComponent(0);
        pieceJointe.setText("");
        mailTexte.setText("");
        headermail.setText("<html>");
        Multipart mp ;
        Ouvrir.setVisible(false);
        for (int i = 0 ; i<controller.getInstance().getListMsg().size();i++)
        {
            Message m = controller.getInstance().getListMsg().get(i);
            if (e.equals( jlist.getComponent(i)) )
            {
                System.out.println(e.getText());
                mailfrom.setText ("Expediteur : " +  m.getFrom()[0].toString());
               mailsubject.setText ("Objet : " + m.getSubject().toString());

                if (m.isMimeType("multipart/*")) {
                    mp = (Multipart) m.getContent();

                    for (int k = 0; k < mp.getCount(); k++) {

                        Part p = mp.getBodyPart(k);


                        if (p.isMimeType("text/plain")) {
                            mailTexte.setText ("text : " + p.getContent().toString());
                        } else {
                            if (p.getDisposition() != null && p.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {

                               pieceJointe.setText (pieceJointe.getText()+ "Piece Jointe : " + p.getFileName());
                               selectedFileName = p.getFileName();
                               System.out.println(selectedFileName);
                               Ouvrir.setVisible(true);
                            }
                        }
                    }
                }else {
                    mailTexte.setText ("text   : " + m.getContent().toString());
                }

                Enumeration en = m.getAllHeaders();

                while (en.hasMoreElements())
                {
                    Header h = (Header)en.nextElement();
                    headermail.setText( headermail.getText() + "\n" +   h.getName() + " -- >" + h.getValue()+"<br>" );
                }
                headermail.setText(headermail.getText()+"</html>");
            }


        }


    }

    private void openFile(String fileName) {
        String projectRoot = System.getProperty("user.dir")+ "\\Image";
        System.out.println(projectRoot);
        File file = new File(projectRoot, fileName);

        if (file.exists()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fichier introuvable : " + fileName);
        }
    }


}

