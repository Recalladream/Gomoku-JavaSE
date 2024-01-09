package utils;

public class VictoryCheck {
    public Boolean check(int qpjl[][],int kind,int fx,int fy){
        int js,f1,f2,jsx1,jsy1,jsx2,jsy2;

        for (int i=0;i<4;i++){
            js=1;
            jsx1=fx;jsy1=fy;
            jsx2=fx;jsy2=fy;
            f1=0;f2=0;

            for (int j=0;j<4;j++){
                if (i==0){
                    jsx1++;
                    jsx2--;

                    if (jsx1<=14&&f1==0){
                        if (qpjl[jsy1][jsx1]==kind){
                            js++;
                        }else {
                            f1=1;
                        }
                    }
                    if (jsx2>=0&&f2==0) {
                        if (qpjl[jsy2][jsx2]==kind){
                            js++;
                        }else {
                            f2=1;
                        }
                    }

                }else if (i==1){
                    jsx1++;jsy1++;
                    jsx2--;jsy2--;

                    if (jsx1<=14&&jsy1<=14&&f1==0){
                        if (qpjl[jsy1][jsx1]==kind){
                            js++;
                        }else{
                            f1=1;
                        }
                    }

                    if (jsx2>=0&&jsy2>=0&&f2==0){
                        if (qpjl[jsy2][jsx2]==kind){
                            js++;
                        }else{
                            f1=1;
                        }
                    }
                }else if (i==2){
                    jsy1++;
                    jsy2--;

                    if (jsy1<=14&&f1==0){
                        if (qpjl[jsy1][jsx1]==kind){
                            js++;
                        }else{
                            f1=1;
                        }
                    }

                    if (jsy2>=0&&f2==0){
                        if (qpjl[jsy2][jsx2]==kind){
                            js++;
                        }else{
                            f1=1;
                        }
                    }
                }else if (i==3){
                    jsx1--;jsy1++;
                    jsx2++;jsy2--;

                    if (jsx1>=0&&jsy1<=14&&f1==0){
                        if (qpjl[jsy1][jsx1]==kind){
                            js++;
                        }else{
                            f1=1;
                        }
                    }

                    if (jsx2<=14&&jsy2>=0&&f2==0){
                        if (qpjl[jsy2][jsx2]==kind){
                            js++;
                        }else{
                            f2=1;
                        }
                    }
                }
            }

            if (js>=5){
                return true;
            }
        }
        return false;
    }
}
