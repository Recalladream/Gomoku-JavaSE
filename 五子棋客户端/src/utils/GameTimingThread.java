package utils;

import mainProgram.OnLineMatch;

import javax.swing.*;
import java.io.ObjectOutputStream;

public class GameTimingThread extends Thread{
    String userid;

    OnLineMatch onLineMatch;

    ObjectOutputStream obputFW;

    JButton jsbut;

    Boolean xqzt[];
    int winjudg[];
    int points[];

    Boolean jsjskz[];
    int jsjl[];

    public GameTimingThread(String userid,JButton jsbut, Boolean jsjskz[], int jsjl[], Boolean xqzt[], int winjudg[],int points[],OnLineMatch onLineMatch,ObjectOutputStream obputFW){
        this.userid=userid;
        this.jsbut=jsbut;
        this.jsjskz=jsjskz;
        this.jsjl=jsjl;
        this.xqzt=xqzt;
        this.winjudg=winjudg;
        this.points=points;
        this.onLineMatch=onLineMatch;
        this.obputFW=obputFW;
    }

    @Override
    public void run() {
        try {
            while (true){
                jsbut.setText(Integer.toString(jsjl[0]));

                (jsjl[0])--;

                if (jsjl[0]<0||jsjskz[0]){

                    if (jsjl[0]<0){

                        if (xqzt[0]){
                            winjudg[0]=-1;
                            points[0]=RandomIntegral.getIntegral(false);
                        }else {
                            winjudg[0]=1;
                            points[0]=RandomIntegral.getIntegral(true);
                        }

                        InforMationSet putinf=new InforMationSet();
                        putinf.setFunction("computedintegral");
                        putinf.setUserid(userid);
                        putinf.setUserjf(points[0]);

                        obputFW.writeObject(putinf);

                        onLineMatch.repaint();
                    }

                    break;
                }

                Thread.sleep(1000);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}
