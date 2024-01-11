package client_utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class UniversalPane extends JPanel {
    int kind;

    int qpjl[][];
    int winjudg[];
    int points[];
    String usernmae;
    String desname;

    Image blackqz;
    Image whiteqz;

    BufferedImage bufferImage;

    JButton w1la;
    JButton w2la;

    public UniversalPane(int kind,int qpjl[][],int winjudg[],int points[],String usernmae,String desname,Image blackqz,Image whiteqz){
        this.kind=kind;
        this.qpjl=qpjl;
        this.winjudg=winjudg;
        this.points=points;
        this.usernmae=usernmae;
        this.desname=desname;
        this.blackqz=blackqz;
        this.whiteqz=whiteqz;

        bufferImage=new BufferedImage(860, 830, BufferedImage.TYPE_INT_RGB);
    }

    void qzdraw(Graphics2D g2d)
    {
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
            {
                if (qpjl[i][j] != 0)
                {
                    int x = 100 + j * 40 - 20, y = 110 + i * 40 - 20;
                    if (qpjl[i][j] == 1)
                        g2d.drawImage(whiteqz,x,y,Color.cyan,null);
                    if (qpjl[i][j] == -1)
                        g2d.drawImage(blackqz,x,y,Color.cyan,null);
                }
            }
    }

    public void initModule(JPanel xxjp){
        w1la=new JButton();
        w1la.setBorderPainted(false);
        w1la.setContentAreaFilled(false);
        w1la.setFocusPainted(false);
        w1la.setFont(new Font("宋体",Font.PLAIN,35));
        w1la.setBounds(660,190,190,40);

        w2la=new JButton();
        w2la.setBorderPainted(false);
        w2la.setContentAreaFilled(false);
        w2la.setFocusPainted(false);
        w2la.setFont(new Font("宋体",Font.PLAIN,35));
        w2la.setBounds(660,630,190,40);

        xxjp.add(w1la);
        xxjp.add(w2la);
    }

    @Override
    protected void paintComponent(Graphics g) {
        try {
            Graphics2D g2d = bufferImage.createGraphics();

            File imagefile=new File("E:\\java\\java代码\\Gomoku\\client-side\\src\\main\\resource\\对战背景.jpg");
            Image backiamge= ImageIO.read(imagefile);
            g2d.drawImage(backiamge,0,0,Color.cyan,null);

            g2d.setColor(Color.red);
            g2d.setFont(new Font("宋体",Font.PLAIN,50));
            g2d.drawString("联 机 对 决",250,60);

            g2d.setColor(Color.black);
            g2d.setFont(new Font("宋体",Font.PLAIN,35));
            if (kind==-1) {g2d.drawString("玩家一(黑)",670,140);
                g2d.drawString("自己",720,180);
                w1la.setText(usernmae);
            }
            else {g2d.drawString("玩家一(黑)",670,140);
                g2d.drawString("对手",720,180);
                w1la.setText(desname);
            }

            g2d.setFont(new Font("宋体",Font.PLAIN,35));
            if (kind==1) {g2d.drawString("玩家二(白)",670,580);
                g2d.drawString("自己",720,620);
                w2la.setText(usernmae);
            }
            else  {g2d.drawString("玩家二(白)",670,580);
                g2d.drawString("对手",720,620);
                w2la.setText(desname);
            }

            for (int i=0;i<=14;i++){
                g2d.drawLine(100,110+40*i,660,110+40*i);
                g2d.drawLine(100+40*i,110,100+40*i,670);
            }

            qzdraw(g2d);

            g2d.setColor(Color.red);

            g2d.setFont(new Font("宋体",Font.PLAIN,100));
            if (winjudg[0]==-1){
                g2d.drawString("虽 败 犹 荣",105,385);
                g2d.drawString("("+Integer.toString(points[0])+")",260,485);
            }else if (winjudg[0]==1){
                g2d.drawString("胜    利",190,385);
                g2d.drawString("(+"+Integer.toString(points[0])+")",260,485);
            }else if (winjudg[0]==2){
                g2d.drawString("对 手 认 输",105,385);
                g2d.drawString("(+"+Integer.toString(points[0])+")",260,485);
            }

            g2d.dispose();

            //双缓冲绘图
            g.drawImage(bufferImage, 0, 0, null);
        }catch (Exception E){E.printStackTrace();}
    }
}
