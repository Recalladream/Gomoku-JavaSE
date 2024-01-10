package mainProgram;

import utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameWin extends JFrame {
    Socket server;
    ObjectOutputStream obputFW;
    ObjectInputStream obgetFW;

    String userID;
    String username;

    int mouse_x;
    int mouse_y;

    BufferedImage bufferedImage;

    JLabel pdsz=new JLabel("0");

    public GameWin(String account, String username){
        this.userID=account;
        this.username=username;
        this.bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);

        initSocket();
    }

    public void initSocket(){
        try {
            server=new Socket("127.0.0.1",3998);
            obputFW=new ObjectOutputStream(server.getOutputStream());
            obgetFW=new ObjectInputStream(server.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    public void closeSocket(){
        try {
            server.close();
        }catch (Exception e){e.printStackTrace();}
    }

    public void window(){
        //设置窗口属性
        this.setVisible(true);
        this.setTitle("五子棋");
        this.setBounds(850,300,1000,1000);
        this.setResizable(false);
        //设置鼠标事件
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouse_x=e.getX();
                mouse_y=e.getY();
                if((mouse_x>=350&&mouse_x<=650)&&(mouse_y>=225&&mouse_y<=300)){
                    if (pdsz.getText().equals("0")){
                        try {
                            pdsz.setText("1");
                            InforMationSet putinf=new InforMationSet();
                            putinf.setFunction("leaderboard");
                            putinf.setUserid(userID);

                            obputFW.writeObject(putinf);

                            InforMationSet getinf=(InforMationSet) obgetFW.readObject();
                            LeaderBoard lb=new LeaderBoard(username,getinf.getUserpm(),getinf.getUserjf(),getinf.getWin(),getinf.getLose(),getinf.getTabinf(),pdsz);
                            lb.leader();
                        }catch (Exception a){a.printStackTrace();}
                    }
                }

                if((mouse_x>=350&&mouse_x<=650)&&(mouse_y>=525&&mouse_y<=600)){
                    if (pdsz.getText().equals("0")){
                        pdsz.setText("1");
                        Normal normal=new Normal(userID,username,pdsz,GameWin.this);
                        normal.join();
                    }
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    InforMationSet putinf=new InforMationSet();
                    putinf.setFunction("off_line");
                    putinf.setUserid(userID);

                    obputFW.writeObject(putinf);

                    closeSocket();
                    System.exit(1);
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }

    @Override
    public void paint(Graphics g){
        try {
            Graphics2D g2d = bufferedImage.createGraphics();

            //绘制背景
            File imagefile=new File("resource\\初始背景.jpg");
            Image backimage=ImageIO.read(imagefile);
            g2d.drawImage(backimage,0,0,Color.cyan,null);
            //绘制选择菜单
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("宋体",Font.PLAIN,80));
            g2d.drawString("排-行-榜",350,300);
            g2d.drawString("联机对决",350,600);

            g2d.dispose();
            //双缓冲绘图
            g.drawImage(bufferedImage,0,0,null);
        }catch (Exception E){E.printStackTrace();}
    }
}
