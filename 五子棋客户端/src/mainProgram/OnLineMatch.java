package mainProgram;

import utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class OnLineMatch extends JFrame {
    GameWin gameWin;

    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    int kind;
    int points[]=new int[1];
    Boolean jsjskz[]=new Boolean[1];
    Boolean xqzt[]=new Boolean[1];
    int winjudg[]=new int[1];

    String userID;
    String usernmae;
    String desname;

    Image blackqz;
    Image whiteqz;
    int qzx;
    int qzy;
    int qpjl[][]=new int[15][15];
    int qznum[]=new int[1];
    int qzjl[][]=new int[15][15];

    JButton jsmzbut;
    JButton jsbut;
    int jsjl[]=new int[1];

    public void initqz(){
        try {
            File blackqzfile=new File("resource\\黑子.jpg");
            File whiteqzfile=new File("resource\\白子.jpg");

            blackqz=ImageIO.read(blackqzfile);
            whiteqz=ImageIO.read(whiteqzfile);
        }catch (Exception e){e.printStackTrace();}
    }

    public OnLineMatch(int kind,String userID,Socket socket,ObjectOutputStream obputFW,ObjectInputStream obgetFW,String usernmae,String desname,GameWin gameWin){
        this.kind=kind;
        this.userID=userID;
        this.server=socket;
        this.obputFW=obputFW;
        this.obgetFW=obgetFW;
        this.usernmae=usernmae;
        this.desname=desname;
        this.gameWin=gameWin;

        initqz();

        switch (kind){
            case -1:xqzt[0]=true;break;
            case 1:xqzt[0]=false;break;
        }
    }

    public void transFerPiece(){
        try {
            InforMationSet putinf=new InforMationSet();
            putinf.setFunction("Transfer_piece");
            putinf.setUserid(userID);
            putinf.setKind(kind);
            putinf.setQzx(qzx);
            putinf.setQzy(qzy);

            obputFW.writeObject(putinf);
        }catch (Exception e){e.printStackTrace();}
    }

    public void window(){
        //设置联机对决窗口属性
        this.setTitle("五子棋");
        this.setBounds(850,300,860,830);
        this.setResizable(false);
        this.setLayout(null);

        UniversalPane xxjp=new UniversalPane(kind,qpjl,winjudg,points,usernmae,desname,blackqz,whiteqz);
        xxjp.setLayout(null);
        xxjp.setBounds(0,0,860,830);

        xxjp.initModule(xxjp);
        initModule(xxjp);

        this.add(xxjp);

        this.setVisible(true);

        jsjskz[0]=false;
        jsjl[0]=60;

        receivingPiece rec=new receivingPiece(qpjl,qzjl,qznum,xqzt,winjudg,points,userID,usernmae,jsmzbut,jsbut,jsjskz,jsjl,OnLineMatch.this,server,obgetFW,obputFW);
        rec.start();

        GameTimingThread gtt=new GameTimingThread(userID,jsbut,jsjskz,jsjl,xqzt,winjudg,points,OnLineMatch.this,obputFW);
        gtt.start();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                qzx=e.getX();
                qzy=e.getY();

                if ((qzx>=100&&qzx<=660)&&(qzy>=150&&qzy<=710)){
                    if (xqzt[0]&&winjudg[0]==0){
                        qzjs(kind);
                        repaint();
                    }
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    points[0]=RandomIntegral.getIntegral(false);
                    jsjskz[0]=true;
                    gameWin.setVisible(true);

                    InforMationSet putinf=new InforMationSet();
                    putinf.setFunction("succlose");
                    putinf.setUserid(userID);
                    putinf.setWinjudg(winjudg[0]);
                    putinf.setKind(kind);
                    putinf.setUserjf(points[0]);

                    obputFW.writeObject(putinf);
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    void qzjs(int kind)
    {
        try {
            int fx = (qzx - 100 + 20) / 40, fy = (qzy - 150 + 20) / 40;
            if (qpjl[fy][fx] == 0)
            {
                qpjl[fy][fx] =kind;
                qzjl[fy][fx] =++(qznum[0]);
                xqzt[0]=false;
                transFerPiece();

                jsmzbut.setText(desname);
                jsjl[0]=60;

                if ((new VictoryCheck()).check(qpjl,kind,fx,fy)){
                    points[0]= RandomIntegral.getIntegral(true);
                    winjudg[0]=1;
                    jsjskz[0]=true;

                    InforMationSet putinf=new InforMationSet();
                    putinf.setFunction("computedintegral");
                    putinf.setUserid(userID);
                    putinf.setUserjf(points[0]);

                    obputFW.writeObject(putinf);
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    void qzdraw(Graphics2D g2d)
    {
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
            {
                if (qpjl[i][j] != 0)
                {
                    int x = 100 + j * 40 - 20, y = 150 + i * 40 - 20;
                    if (qpjl[i][j] == 1)
                        g2d.drawImage(whiteqz,x,y,Color.cyan,null);
                    if (qpjl[i][j] == -1)
                        g2d.drawImage(blackqz,x,y,Color.cyan,null);
                }
            }
    }

    public int[] penitenceCheck(JButton hqbut){
        int hqjl[]=new int[4];
        int max1=0,max2=0;

        for (int i=0;i<15;i++)
            for (int j=0;j<15;j++){
                int value=qzjl[i][j];
                if (value>max1){
                    max1=value;
                    hqjl[0]=i;
                    hqjl[1]=j;
                }else if (value>max2){
                    max2=value;
                    hqjl[2]=i;
                    hqjl[3]=j;
                }
            }

        qzjl[hqjl[0]][hqjl[1]]=qpjl[hqjl[0]][hqjl[1]]=0;
        qzjl[hqjl[2]][hqjl[3]]=qpjl[hqjl[2]][hqjl[3]]=0;
        qznum[0]-=2;

        repaint();

        return hqjl;
    }

    public void initModule(JPanel xxjp){
        jsmzbut=new JButton();
        switch (kind){
            case -1:jsmzbut.setText(usernmae);break;
            case 1:jsmzbut.setText(desname);break;
        }
        jsmzbut.setBorderPainted(false);
        jsmzbut.setContentAreaFilled(false);
        jsmzbut.setFocusPainted(false);
        jsmzbut.setFont(new Font("宋体",Font.PLAIN,35));
        jsmzbut.setBounds(660,350,190,40);

        jsbut=new JButton("");
        jsbut.setContentAreaFilled(false);
        jsbut.setFocusPainted(false);
        jsbut.setFont(new Font("宋体",Font.PLAIN,35));
        jsbut.setBounds(700,390,120,40);

        JButton hqbut=new JButton("悔棋1");
        hqbut.setContentAreaFilled(false);
        hqbut.setFont(new Font("宋体",Font.PLAIN,30));
        hqbut.setBounds(340,710,120,40);

        xxjp.add(jsmzbut);
        xxjp.add(jsbut);
        xxjp.add(hqbut);

        hqbut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (winjudg[0]==0&&xqzt[0]&&"悔棋1".equals(hqbut.getText())&&qznum[0]>=2){
                        hqbut.setText("悔棋0");
                        int hqjl[]=penitenceCheck(hqbut);

                        InforMationSet putinf=new InforMationSet();
                        putinf.setFunction("penitence");
                        putinf.setUserid(userID);
                        putinf.setKind(kind);
                        putinf.setHqjl(hqjl);

                        obputFW.writeObject(putinf);

                        jsjl[0]=60;
                    }
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }
}

class receivingPiece extends Thread{
    int qpjl[][];
    int qzjl[][];
    int qznum[];
    Boolean jsjskz[];
    Boolean xqzt[];
    int winjudg[];
    int points[];
    OnLineMatch onLineMatch;

    Socket server;
    ObjectInputStream obgetFW;
    ObjectOutputStream obputFW;

    String userid;
    String username;
    JButton jsmzbut;
    JButton jsbut;
    int jsjl[];

    public receivingPiece(int qpjl[][],int qzjl[][],int qznum[],Boolean xqzt[],int winjudg[],int points[],String userid,String username,JButton jsmzbut,JButton jsbut,Boolean jsjskz[],int jsjl[],OnLineMatch onLineMatch,Socket server,ObjectInputStream obgetFW,ObjectOutputStream obputFW){
        this.qpjl=qpjl;
        this.qzjl=qzjl;
        this.qznum=qznum;
        this.xqzt=xqzt;
        this.winjudg=winjudg;
        this.points=points;
        this.onLineMatch=onLineMatch;
        this.server=server;
        this.obgetFW=obgetFW;
        this.obputFW=obputFW;
        this.userid=userid;
        this.username=username;
        this.jsmzbut=jsmzbut;
        this.jsbut=jsbut;
        this.jsjskz=jsjskz;
        this.jsjl=jsjl;
    }

    void qzjs(int kind,int qzx,int qzy)
    {
        try {
            int fx = (qzx - 100 + 20) / 40, fy = (qzy - 150 + 20) / 40;
            if (qpjl[fy][fx] == 0)
            {
                qpjl[fy][fx] =kind;
                qzjl[fy][fx] =++(qznum[0]);
                xqzt[0]=true;

                jsmzbut.setText(username);
                jsjl[0]=60;

                if ((new VictoryCheck()).check(qpjl,kind,fx,fy)){
                    points[0]=RandomIntegral.getIntegral(false);
                    winjudg[0]=-1;
                    jsjskz[0]=true;

                    InforMationSet putinf=new InforMationSet();
                    putinf.setFunction("computedintegral");
                    putinf.setUserid(userid);
                    putinf.setUserjf(points[0]);

                    obputFW.writeObject(putinf);
                }

                onLineMatch.repaint();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void runaway(){
        try {
            points[0]=RandomIntegral.getIntegral(true);
            winjudg[0]=2;
            jsjskz[0]=true;

            InforMationSet putinf=new InforMationSet();
            putinf.setFunction("computedintegral");
            putinf.setUserid(userid);
            putinf.setUserjf(points[0]);

            obputFW.writeObject(putinf);

            onLineMatch.repaint();
        }catch (Exception e){e.printStackTrace();}
    }

    public void penitence(int hqjl[]){
        qzjl[hqjl[0]][hqjl[1]]=qpjl[hqjl[0]][hqjl[1]]=0;
        qzjl[hqjl[2]][hqjl[3]]=qpjl[hqjl[2]][hqjl[3]]=0;
        qznum[0]-=2;

        jsjl[0]=60;

        onLineMatch.repaint();
    }

    public void closeSocket(){
        try {
            server.close();
        }catch (Exception E){E.printStackTrace();}
    }

    @Override
    public void run() {
        try {
            while (true){
                InforMationSet infget=(InforMationSet) obgetFW.readObject();
                String func=infget.getFunction();

                if (func==null){
                    qzjs(infget.getKind(),infget.getQzx(),infget.getQzy());
                }
                else if ("close".equals(func)){
                    closeSocket();
                    break;
                }else if ("runaway".equals(func)){
                    runaway();

                    break;
                }else if ("penitence".equals(func)){
                    penitence(infget.getHqjl());
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }
}