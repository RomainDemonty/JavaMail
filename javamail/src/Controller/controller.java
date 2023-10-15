package Controller;

import Swing.Connect;
import Swing.MiniOutlook;

import javax.activation.*;
import javax.mail.internet.*;
import javax.swing.*;
import javax.mail.*;


import java.util.Properties;



public class controller {

    private static controller instance = new controller();

    public static controller getInstance()
    {
        return instance ;
    }
    private JFrame mainVue ;

    // Paramètres de connexion au serveur SMTP
    private String smtpServeur = "smtp.gmail.com";
    private String smtpPort = "587" ;
    private  String username;
    private  String password;


    private controller (){
         mainVue = new Connect();
    }

    public String Connexion(String mail , String mdp){
        if(mail.equals("cedricromain577@gmail.com"))
        {
            if(mdp.equals("lgltdzyyadcqnbpe") || mdp.equals("ulfkcbjrzwftkfwt"))
            {
                mainVue.dispose();
                mainVue = new MiniOutlook();
                password = mdp;
                username = mail;
                return "corect" ;
            }
            else
            {
                return "mdp incorrect" ;
            }
        }
        else{
           return("email incorrect");
        }
    }

    public String Envoyer(String destinataire , String objet , String text , String lien){
        if(destinataire == null)
        {
            return "Le destinataire ne peut pas être vide !";
        }
        else
        {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpServeur);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = javax.mail.Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setSubject(objet);
                message.setText(text);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));



                if (lien != null ) {
                    MimeBodyPart pieceJointe = new MimeBodyPart();
                    FileDataSource source = new FileDataSource(lien);
                    pieceJointe.setDataHandler(new DataHandler(source));
                    pieceJointe.setFileName(source.getFile().getName());

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(pieceJointe);

                    message.setContent(multipart);
                }

                Transport.send(message);

                System.out.println("E-mail envoyé avec succès !");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }


            if(lien == null)
            {
                System.out.println("test d'envois sans liens");
                System.out.println("destinataire = "+ destinataire);
                System.out.println("objet = "+ objet);
                System.out.println("text = "+ text);
            }
            else{
                System.out.println("test d'envois avec liens");
                System.out.println("destinataire = "+ destinataire);
                System.out.println("objet = "+ objet);
                System.out.println("text = "+ text);
                System.out.println("Chemin d'accès au fichier sélectionné : " + lien);
            }
            return "";
        }



}
