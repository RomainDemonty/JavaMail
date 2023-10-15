import Controller.controller;
import Swing.Connect;
import Swing.MiniOutlook;

public class Main {
    public static void main(String[] args) {
        controller.getInstance();
        System.out.println(controller.getInstance());
    }
}