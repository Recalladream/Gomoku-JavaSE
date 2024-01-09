package logininterface;

import mainProgram.GameWin;
import utils.AddRecord;
import utils.InforMationSet;
import utils.MD5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginInter {
    private static Socket server;
    private static ObjectOutputStream obputFW;
    private static ObjectInputStream obgetFW;

    private static JFrame loginjf;
    private static JPanel pan;
    private static JLabel bjLab;

    private static Box zbox;

    private static ImageIcon drimage;
    private static ImageIcon zcimage;
    private static ImageIcon zhbsimage;
    private static ImageIcon passbsimage;
    private static ImageIcon xsimage;
    private static ImageIcon ycimage;

    private static JLabel zccodesmg=new JLabel("!@#$%^&*");
    private static JLabel wzcodesmg=new JLabel("!@#$%^&*");
    private static JLabel wgcodesmg=new JLabel("!@#$%^&*");

    private static JButton zccodebut=new JButton("获得");
    private static JButton wzcodebut=new JButton("获得");
    private static JButton wgcodebut=new JButton("获得");

    public static void initsocket(){
        try {
            server=new Socket("127.0.0.1",3998);
            obputFW=new ObjectOutputStream(server.getOutputStream());
            obgetFW=new ObjectInputStream(server.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }

    public static void closeSocket(){
        try {
            server.close();
        }catch (Exception E){E.printStackTrace();}
    }

    public static void initImage(){
        bjLab=new JLabel();
        drimage=new ImageIcon("resource\\登录背景.png");
        zcimage = new ImageIcon("resource\\RegistrationBackground.png");
        zhbsimage=new ImageIcon("resource\\AccountIdentification.png");
        passbsimage=new ImageIcon("resource\\PasswordIdentification.png");
        ycimage=new ImageIcon("resource\\Hide.png");
        xsimage=new ImageIcon("resource\\Display.png");
    }

    public static void login(){
        initImage();
        initsocket();
        loginjf=new JFrame("登录界面");
        loginjf.setBounds(900,400,650,500);
        loginjf.setResizable(false);

        bjLab.setIcon(drimage);
        bjLab.setSize(drimage.getIconWidth(),drimage.getIconHeight());
        loginjf.getLayeredPane().add(bjLab, new Integer(Integer.MIN_VALUE));

        pan = (JPanel) loginjf.getContentPane();
        pan.setOpaque(false);
        pan.setLayout(new FlowLayout());

        zbox=Box.createVerticalBox();

        Box ubox=Box.createHorizontalBox();
        JLabel yh=new JLabel(zhbsimage);
        JTextField yhmk=new JTextField(15);
        yhmk.setFont(new Font("宋体",Font.PLAIN,25));
        JButton lsjl=new JButton("历史");
        lsjl.setFont(new Font("宋体",Font.PLAIN,25));
        lsjl.setBorderPainted(false);
        lsjl.setContentAreaFilled(false);
        ubox.add(yh);
        ubox.add(Box.createHorizontalStrut(20));
        ubox.add(yhmk);
        ubox.add(lsjl);

        Box mbox=Box.createHorizontalBox();
        JLabel mima=new JLabel(passbsimage);
        JPasswordField mamk=new JPasswordField(15);
        mamk.setEchoChar('*');
        mamk.setFont(new Font("宋体",Font.PLAIN,25));
        JButton ycmibut=new JButton(ycimage);
        ycmibut.setContentAreaFilled(false);
        ycmibut.setBorderPainted(false);
        JButton xsmibut=new JButton(xsimage);
        xsmibut.setContentAreaFilled(false);
        xsmibut.setBorderPainted(false);
        mbox.add(mima);
        mbox.add(Box.createHorizontalStrut(20));
        mbox.add(mamk);
        mbox.add(Box.createHorizontalStrut(15));
        mbox.add(ycmibut);

        Box gnbox=Box.createHorizontalBox();
        JCheckBox jzma=new JCheckBox("记住密码");
        jzma.setSelected(true);
        jzma.setFocusPainted(false);
        jzma.setFont(new Font("宋体",Font.PLAIN,25));
        JButton zuce=new JButton("注册");
        zuce.setFont(new Font("宋体",Font.PLAIN,25));
        zuce.setContentAreaFilled(false);
        zuce.setBorderPainted(false);
        JButton wjpass=new JButton("找回");
        wjpass.setFont(new Font("宋体",Font.PLAIN,25));
        wjpass.setContentAreaFilled(false);
        wjpass.setBorderPainted(false);

        gnbox.add(jzma);
        gnbox.add(zuce);
        gnbox.add(wjpass);

        Box annobox=Box.createHorizontalBox();
        JButton dru=new JButton("安全登录");
        dru.setFont(new Font("宋体",Font.PLAIN,25));
        dru.setContentAreaFilled(false);
        dru.setBorderPainted(false);

        annobox.add(dru);
        annobox.add(Box.createHorizontalStrut(20));

        zbox.add(Box.createVerticalStrut(240));
        zbox.add(ubox);
        zbox.add(Box.createVerticalStrut(12));
        zbox.add(mbox);
        zbox.add(Box.createVerticalStrut(8));
        zbox.add(gnbox);
        zbox.add(Box.createVerticalStrut(8));
        zbox.add(annobox);

        pan.add(zbox);

        loginjf.setVisible(true);

        lsjl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame lsjljm=new JFrame("登录历史");
                lsjljm.setBounds(800,600,400,400);
                lsjljm.setResizable(false);

                JPanel pan=new JPanel();
                JScrollPane gdpan=new JScrollPane(
                        pan,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );
                lsjljm.add(gdpan);

                Box box=Box.createVerticalBox();
                pan.add(box);

                CreateRecord creat=new CreateRecord(box,lsjljm,yhmk,mamk);
                creat.create();

                lsjljm.setVisible(true);
            }
        });

        zuce.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pan.remove(zbox);
                loginjf.repaint();
                loginjf.setVisible(true);
                Register register=new Register(loginjf,pan,bjLab,zccodesmg,zbox,drimage,zcimage,zccodebut,obputFW,obgetFW);
                register.registrationInterface();
            }
        });

        wjpass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pan.remove(zbox);
                loginjf.repaint();
                loginjf.setVisible(true);
                Retrieve retrieve=new Retrieve(loginjf,pan,bjLab,drimage,zbox,wzcodesmg,wgcodesmg,wzcodebut,wgcodebut,obputFW,obgetFW);
                retrieve.retrievalInterface();
            }
        });

        ycmibut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mbox.remove(ycmibut);
                mbox.add(xsmibut);
                mamk.setEchoChar((char)0);
                loginjf.repaint();
                loginjf.setVisible(true);
            }
        });

        xsmibut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mbox.remove(xsmibut);
                mbox.add(ycmibut);
                mamk.setEchoChar('*');
                loginjf.repaint();
                loginjf.setVisible(true);
            }
        });

        dru.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String account=yhmk.getText();
                    String password=mamk.getText();

                    if(account.equals("")||password.equals(""))
                        dru.setText("账号或密码不能为空");
                    else{
                        String jmmima= MD5.md5(password);

                        InforMationSet putinfjh=new InforMationSet();
                        putinfjh.setFunction("Login_check");
                        putinfjh.setUserid(account);
                        putinfjh.setUserpass(jmmima);

                        obputFW.writeObject(putinfjh);
                        obputFW.flush();

                        InforMationSet getinfjh=(InforMationSet) obgetFW.readObject();
                        String zt=getinfjh.getCzstate();

                        if(zt.equals("2")){dru.setText("密码错误");}
                        else
                        if(zt.equals("3")){dru.setText("账号不存在");}
                        else
                        if (zt.equals("4")){dru.setText("此账号已登录");}
                        else
                        {
                            loginjf.dispose();

                            if (jzma.isSelected()){
                                AddRecord addRecord=new AddRecord(zt,account,password);
                                addRecord.Joinrecord();
                            }

                            GameWin gameWin=new GameWin(account,zt);
                            gameWin.window();
                        }
                    }
                }catch (Exception a){a.printStackTrace();}
            }
            @Override
            public void mouseExited(MouseEvent e) {
                dru.setText("安全登录");
            }
        });

        loginjf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    InforMationSet putinf=new InforMationSet();
                    putinf.setFunction("Close");

                    obputFW.writeObject(putinf);

                    closeSocket();
                    System.exit(1);
                }catch (Exception a){a.printStackTrace();}
            }
        });
    }
}