package Controller;

import Swing.Connect;
import Swing.MiniOutlook;

import javax.swing.*;

public class controller {

    private static controller instance = new controller();

    public static controller getInstance()
    {
        return instance ;
    }
    private JFrame mainVue ;

    // Paramètres de connexion au serveur SMTP
    private String smtpServeur = "smtp.gmail.com";
    private  String username;
    private  String password;


    private controller (){
         mainVue = new MiniOutlook();
    }

    public String Connexion(String mail , String mdp){
        if(mail.equals("cedricromain577@gmail.com"))
        {
            if(mdp.equals("lgltdzyyadcqnbpe") || mdp.equals("sozmoxmiamrfqzih"))
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

        if(lien != null)
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
