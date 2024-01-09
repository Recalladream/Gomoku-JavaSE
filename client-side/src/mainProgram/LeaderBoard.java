package mainProgram;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LeaderBoard {
    String username;
    String userpm;
    String userjf;
    String win;
    String lose;

    Object tabinf[][];

    JLabel pdsz;
    public LeaderBoard(String username,String userpm,int userjf,int win,int lose,Object tabinf[][],JLabel pdsz){
        this.username=username;
        this.userpm=userpm;
        this.userjf=Integer.toString(userjf);
        this.win=Integer.toString(win);
        this.lose=Integer.toString(lose);
        this.tabinf=tabinf;
        this.pdsz=pdsz;
    }

    public void leader(){
        JFrame jm=new JFrame("排行榜");
        jm.setLayout(null);
        jm.setBounds(500,500,800,600);
        jm.setResizable(false);

        JLabel mzla=new JLabel(username);
        mzla.setHorizontalAlignment(JLabel.CENTER);
        mzla.setFont(new Font("宋体",Font.PLAIN,28));
        mzla.setBounds(0,30,160,40);

        JLabel pmla=new JLabel("第"+userpm+"名");
        pmla.setHorizontalAlignment(JLabel.CENTER);
        pmla.setFont(new Font("宋体",Font.PLAIN,28));
        pmla.setBounds(160,30,160,40);

        JLabel jfla=new JLabel(userjf+"积分");
        jfla.setHorizontalAlignment(JLabel.CENTER);
        jfla.setFont(new Font("宋体",Font.PLAIN,28));
        jfla.setBounds(320,30,160,40);

        JLabel winla=new JLabel(win+"胜");
        winla.setHorizontalAlignment(JLabel.CENTER);
        winla.setFont(new Font("宋体",Font.PLAIN,28));
        winla.setBounds(480,30,160,40);

        JLabel losela=new JLabel(lose+"败");
        losela.setHorizontalAlignment(JLabel.CENTER);
        losela.setFont(new Font("宋体",Font.PLAIN,28));
        losela.setBounds(640,30,160,40);

        String tabname[]={"排名","昵称","积分","胜场","败场"};

        DefaultTableModel model = new DefaultTableModel(tabinf,tabname);

        JTable xxtab=new JTable(model);
        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        xxtab.setDefaultRenderer(Object.class,r);
        xxtab.setRowHeight(40);
        xxtab.getTableHeader().setFont(new Font("宋体",Font.PLAIN,28));
        xxtab.setFont(new Font("宋体",Font.PLAIN,28));

        JScrollPane gdpan=new JScrollPane(xxtab,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
        );
        gdpan.setBounds(0,100,780,500);

        jm.add(mzla);
        jm.add(pmla);
        jm.add(jfla);
        jm.add(winla);
        jm.add(losela);
        jm.add(gdpan);

        jm.setVisible(true);

        jm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                pdsz.setText("0");
            }
        });
    }

}