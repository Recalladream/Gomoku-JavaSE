package client_utils;

import javax.swing.*;

/**
 * 这是一个线程，用来获得验证码后倒计时
 */
public class CountDownThread extends Thread{
    int js;
    String b;
    JButton codebut;
    JLabel jlcodema;

    /**
     * 构造方法
     * @param js
     * @param codebut
     * @param jlcodema
     */
    public CountDownThread(int js,JButton codebut,JLabel jlcodema){
        this.js=js;
        this.codebut=codebut;
        this.jlcodema=jlcodema;
    }

    /**
     * 线程执行体
     */
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