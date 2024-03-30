package kw.wzq.newai;

public class Zobirst {
    //table[2][]
    private int []com;
    private int []hum;
    private int len=ConstanNum.GRID_NUMBER;
    private int code;

    public Zobirst(){
        int len2=len*len;
        com=new int[len2];
        hum=new int[len2];
        //用户和电脑
        for(int i=0;i<len2;i++){
            com[i]=rand();
            hum[i]=rand();
        }
        code=rand();
    }

    public int getCode(){
        return code;
    }

    //计算code没有问题
    public int go(int x,int y,int role){
        int index=len*x+y;
        code^=(role==ConstanNum.COM ? com[index]:hum[index]);
        return code;
    }

    private int rand(){
        return (int)(Math.random()*1000000000);
    }
}
