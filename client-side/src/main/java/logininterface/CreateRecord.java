package logininterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class CreateRecord{
    //

    Box box;
    JFrame lsjljm;
    JTextField yhmk;
    JTextField mamk;

    CreateRecord(Box box,JFrame lsjljm,JTextField yhmk,JTextField mamk){
        this.box=box;
        this.lsjljm=lsjljm;
        this.yhmk=yhmk;
        this.mamk=mamk;
    }

    public void create(){
        try {
            int wz=0;
            String smg=new String("E:\\java\\java代码\\Gomoku\\client-side\\src\\main\\resource\\LoginHistory.txt");
            File file=new File(smg);
            FileReader fileget=new FileReader(file);
            BufferedReader filegetbuf=new BufferedReader(fileget);
            while (filegetbuf.ready()){
                String nc=filegetbuf.readLine();
                String zh=filegetbuf.readLine();
                String pass=filegetbuf.readLine();

                Recording creat=new Recording(box,lsjljm,yhmk,mamk,nc,zh,pass,wz);
                wz++;
            }
        }catch (Exception e){}
    }
}

