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


}
