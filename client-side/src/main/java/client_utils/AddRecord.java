package client_utils;

import java.io.*;

/**
 * 将用户登录记录存入记录文件
 */
public class AddRecord{

    String nickname;
    String accountNumber;
    String password;

    /**
     * 构造方法
     * @param nickname
     * @param accountNumber
     * @param password
     */
    public AddRecord(String nickname,String accountNumber,String password){
        this.nickname=nickname;
        this.accountNumber=accountNumber;
        this.password=password;
    }

    /**
     * 加入记录文件方法
     */
    public void Joinrecord(){
        try {
            int k=0,x=0;
            String []jl=new String[1000];

            String smg="E:\\java\\java代码\\Gomoku\\client-side\\src\\main\\resource\\LoginHistory.txt";
            File file=new File(smg);

            FileReader fileget=new FileReader(file);
            BufferedReader filegetbuf=new BufferedReader(fileget);

            while (filegetbuf.ready()){
                jl[x++]=filegetbuf.readLine();
            }
            file.delete();
            filegetbuf.close();

            for (int i=0;i<x;i+=3){
                String um=jl[i];
                String zh=jl[i+1];
                String pass=jl[i+2];
                if (zh.equals(accountNumber)){
                    jl[i]=nickname;
                    jl[i+2]=password;
                    k=1;
                    break;
                }
            }
            if (k==0){
                jl[x++]=nickname;
                jl[x++]=accountNumber;
                jl[x++]=password;
            }

            FileOutputStream creatfile=new FileOutputStream(file);
            creatfile.close();

            FileWriter fileput=new FileWriter(file,true);
            for (int i=0;i<x;i++) {
                fileput.write(jl[i]+ "\n");
            }
            fileput.close();

        }catch (Exception e){e.printStackTrace();}
    }
}
