package utils;

import mainProgram.GameWin;
import mainProgram.OnLineMatch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Normal {
    GameWin gameWin;

    JFrame jm;
    Boolean jmgbcz[]={true};
    JLabel txla;
    JLabel pdsz;

    String userID;
    String username;

    Socket socket;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    public void initsoket(){
        try {
            socket=new Socket("127.0.0.1",3998);
            obputFW=new ObjectOutputStream(socket.getOutputStream());
            obgetFW=new ObjectInputStream(socket.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    public void closeSocket(){
        try {
            socket.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public Normal(String userID,String username,JLabel pdsz,GameWin gameWin){
        this.userID=userID;
        this.pdsz=pdsz;
        this.username=username;
        this.gameWin=gameWin;

        initsoket();
    }

    public void matchingInterface(){
        jm=new JFrame("匹配");
        jm.setLayout(null);
        jm.setResizable(false);
        jm.setBounds(900,400,400,200);

        txla=new JLabel();
        txla.setBounds(10,50,400,40);
        txla.setFont(new Font("宋体",Font.PLAIN,30));

        jm.add(txla);

        jm.repaint();
        jm.setVisible(true);
    }

    public void join(){
        try {
            matchingInterface();

            Keeptime keeptime=new Keeptime(jm,txla,obputFW,pdsz);
            keeptime.start();

            Connect connect=new Connect(socket,obputFW,obgetFW,userID,username,jm,jmgbcz,pdsz,keeptime,gameWin);
            connect.start();

            jm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        if (jmgbcz[0]){
                            InforMationSet putfor=new InforMationSet();
                            putfor.setUserid(userID);
                            putfor.setFunction("loseClose");

                            obputFW.writeObject((Object) putfor);

                            pdsz.setText("0");

                            keeptime.setBdjs(true);
                        }
                    }catch (Exception a){a.printStackTrace();}
                }
            });
        }catch (Exception e){e.printStackTrace();}
    }
}

class Connect extends Thread{
    GameWin gameWin;

    String userID;
    String username;
    Socket socket;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;
    JFrame jm;
    Boolean jmgbcz[];
    JLabel pdsz;
    Keeptime keeptime;

    public Connect(Socket socket,ObjectOutputStream obputFW,ObjectInputStream obgetFW,String userID,String username,JFrame jm,Boolean jmgbcz[],JLabel pdsz,Keeptime keeptime,GameWin gameWin){
        this.socket=socket;
        this.obgetFW=obgetFW;
        this.obputFW=obputFW;
        this.userID=userID;
        this.username=username;
        this.jm=jm;
        this.jmgbcz=jmgbcz;
        this.pdsz=pdsz;
        this.keeptime=keeptime;
        this.gameWin=gameWin;
    }

    public void closeSocket(){
        try {
            socket.close();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void run() {
        try {
            InforMationSet putinfor=new InforMationSet();
            putinfor.setFunction("Normal");
            putinfor.setUserid(userID);

            obputFW.writeObject((Object) putinfor);

            InforMationSet getinfor=(InforMationSet) obgetFW.readObject();

            if (getinfor.getFunction().equals("Normal_Successful")){
                int kind=getinfor.getKind();
                String desname=getinfor.getUsername();

                gameWin.setVisible(false);

                OnLineMatch onLineMatch=new OnLineMatch(kind,userID,socket,obputFW,obgetFW,username,desname,gameWin);
                onLineMatch.window();
            }
            if (getinfor.getFunction().equals("Normal_lose")){
                InforMationSet putfor=new InforMationSet();
                putfor.setUserid(userID);
                putfor.setFunction("loseClose");

                obputFW.writeObject((Object) putfor);
            }
            if (getinfor.getFunction().equals("Close")){
                closeSocket();
            }

            jmgbcz[0]=false;
            jm.dispose();
            pdsz.setText("0");
            keeptime.setBdjs(true);
        }catch (Exception e){e.printStackTrace();}
    }
}

class Keeptime extends Thread{
    int a=0;
    private boolean bdjs=false;

    JFrame jm;
    JLabel txla;
    ObjectOutputStream obputFW;
    JLabel pdsz;

    public Keeptime(JFrame jm, JLabel txla,ObjectOutputStream obputFW,JLabel pdsz){
        this.jm=jm;
        this.txla=txla;
        this.obputFW=obputFW;
        this.pdsz=pdsz;
    }

    public void setBdjs(boolean bdjs){
        this.bdjs=bdjs;
    }

    @Override
    public void run() {
        try {
            while (true){
                if (bdjs)break;

                a++;
                txla.setText("正在匹配，请稍等....("+a+")");
                jm.repaint();
                jm.setVisible(true);

                Keeptime.sleep(1000);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}