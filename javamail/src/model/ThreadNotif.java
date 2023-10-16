package model;
import Controller.controller;
import Swing.MiniOutlook;

import javax.mail.*;

import java.util.Properties;

public class ThreadNotif extends Thread {

    private int time;
    private int currentsize;
    private String username ;
    private String password ;

    public ThreadNotif(String u , String p, int t) {
        this.username = u ;
        this.password = p ;
        this.time = t;
        this.currentsize = -1;
    }

    @Override
    public void run() {
        int tampon =0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                tampon = actualiser();
                System.out.println(tampon);
                if ( tampon!= currentsize && currentsize!=-1) {
                    controller.getInstance().notifier();
                }
                currentsize = tampon;
                Thread.sleep(time);
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    private int actualiser() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("file.encoding", "iso8859-1");

        Session session = javax.mail.Session.getDefaultInstance(props);
        try {
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", username, password);
            Folder f = store.getFolder("INBOX");
            f.open(Folder.READ_ONLY);
            int taille = f.getMessages().length;
            return taille ;

        } catch (NoSuchProviderException e) {
            System.out.println(" erreur provider ");
        } catch (MessagingException e) {
            System.out.println("erreur messaging  " + e.getMessage());
        }

        return -1;
    }
}

