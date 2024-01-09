package utils;

import javax.swing.*;

public class CountDownThread extends Thread{
    int js;
    String b;
    JButton codebut;
    JLabel jlcodema;

    public CountDownThread(int js,JButton codebut,JLabel jlcodema){
        this.js=js;
        this.codebut=codebut;
        this.jlcodema=jlcodema;
    }
    public void run(){
        try {
            while (true){

                codebut.setText(Integer.toString(js));

                if (js<=0)break;

                js--;

                codebut.repaint();
                Thread.sleep(1000);
            }
            codebut.setText("获得");
            jlcodema.setText("!@#$%^&*");
        }catch (Exception e){}
    }
}