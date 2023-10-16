package Controller;

import Swing.Connect;
import Swing.MiniOutlook;
import model.ThreadNotif;

import javax.activation.*;
import javax.mail.internet.*;
import javax.swing.*;
import javax.mail.*;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
                ThreadNotif thn= new ThreadNotif(username,password,15000) ;
                thn.start();
                return "correct" ;
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
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));

                if (lien != null ) {
                    Multipart multipart = new MimeMultipart();
                    MimeBodyPart mbp =  new MimeBodyPart();
                    mbp.setText(text);
                    multipart.addBodyPart(mbp);

                    //boucler le petit bout de code ci dessous pour en envoyer plusioeurs pj
                    //necessite un tab de pj dans le controlleur
                    MimeBodyPart pieceJointe = new MimeBodyPart();
                    FileDataSource source = new FileDataSource(lien);
                    pieceJointe.setDataHandler(new DataHandler(source));
                    pieceJointe.setFileName(source.getFile().getName());
                    multipart.addBodyPart(pieceJointe);


                    message.setContent(multipart);
                }
                else {
                    message.setText(text);
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

    public void Recevoir()
    {
        Properties props = new Properties();
        props.put("mail.store.protocol", "pop3");
        props.put("mail.pop3.host", "pop.gmail.com");
        props.put("file.encoding","iso8859-1");
        props.put("mail.pop3.port", "995");

        Session session = javax.mail.Session.getDefaultInstance(props);
        try {
            Store store = session.getStore("pop3s");
            store.connect("pop.gmail.com",username,password);
            Folder f = store.getFolder("INBOX") ;
            f.open(Folder.READ_ONLY);
            Message msgList[] = f.getMessages() ;
            Multipart mp ;
            int length = f.getMessages().length;
            System.out.println(length);
            for(Message m : msgList)
            {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("Expéditeur : " + m.getFrom()[0]);
                System.out.println("Sujet : " + m.getSubject());
                if (m.isMimeType("multipart/*")) {
                    mp = (Multipart) m.getContent();
                    for (int i = 0; i < mp.getCount(); i++) {

                        Part p = mp.getBodyPart(i);

                        if (p.isMimeType("text/plain")) {
                            System.out.println("text : " + p.getContent().toString());
                        } else {
                            if (p.getDisposition() != null && p.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
                                InputStream is = p.getInputStream();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                int c;
                                while ((c = is.read()) != -1) {
                                    baos.write(c);
                                }
                                baos.flush();
                                String nf = p.getFileName();
                                FileOutputStream fos = new FileOutputStream(nf);
                                baos.writeTo(fos);
                                fos.close();
                                System.out.println("Pièce attachée " + nf + " récupérée");
                            }

                        }
                    }
                }else {
                        System.out.println("text : " + m.getContent().toString());
                    }
                }
        } catch (NoSuchProviderException e) {
            System.out.println(" erreur provider ");
        } catch (MessagingException e) {
            System.out.println("erreur messaging  " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void notifier()
    {
           JOptionPane.showMessageDialog(null, "Vous avez de nouveau(x) mail(s)", "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

}
