package kw.maj.ai;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kw.gdx.asset.Asset;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class Ai{
    //
    public static boolean isBlack=false;//��־���ӵ���ɫ
    public static int[][] chessBoard=new int[17][17]; //�������ӵİڷ������0���ӣ�1���ӣ���1����
    private static HashSet<Point> toJudge=new HashSet<Point>(); // ai���ܻ�����ĵ�
    private static int dr[]=new int[]{-1,1,-1,1,0,0,-1,1}; // ��������
    private static int dc[]=new int[]{1,-1,-1,1,-1,1,0,0}; //��������
    public static final int MAXN=1<<28;
    public static final int MINN=-MAXN;
    private static int searchDeep=4;    //�������
    private static final int size=15;   //���̴�С
    public static boolean isFinished=false;

    private static Group group;
    public Ai(Group group) {
        this.group = group;
    }


    // ��ʼ����������ͼ
    public static void initChessBoard(){

        isBlack=true;
        toJudge.clear();
        for(int i=1;i<=15;++i)
            for(int j=1;j<=15;++j)
                chessBoard[i][j]=0;
        chessBoard[8][8]=1;
        for(int i=0;i<8;++i) {
            if (1 <= 8 + dc[i] && 8 + dc[i] <= size && 1 <= 8 + dr[i] && 8 + dr[i] <= size) {
                Point now = new Point(8 + dc[i], 8 + dr[i]);
                if (!toJudge.contains(now))
                    toJudge.add(now);
            }
        }
        setImage(8,8);
        isBlack=false;
    }

    static void setImage(int x,int y){
        String path = "cocos/AlertLayer/cancel.png";
        if (isBlack) {
            path = "cocos/AlertLayer/ok.png";
        }
        Image image = new Image(Asset.getAsset().getTexture(path));
        group.addActor(image);
        image.setSize(50,50);
        image.setPosition(x*50,y*50);
    }

    // ͨ������¼����õ�����λ�ý�������
    public static void putChess(int x,int y){
        chessBoard[y][x]=isBlack?1:-1;

        setImage(x,y);
        if(isEnd(x,y)){

            System.out.println("--------success--------------------");
            group.clearChildren();
            String s=Ai.isBlack?"����ʤ":"����ʤ";
            isBlack=true;
            initChessBoard();
        }
        else{
            Point p=new Point(x,y);
            if(toJudge.contains(p))
                toJudge.remove(p);
            for(int i=0;i<8;++i){
                Point now=new Point(p.x+dc[i],p.y+dr[i]);
                if(1<=now.x && now.x<=size && 1<=now.y && now.y<=size && chessBoard[now.y][now.x]==0)
                    toJudge.add(now);
            }
        }
    }

    // ai������ں���
    public static void myAI(){
        Node node=new Node();
        dfs(0,node,MINN,MAXN,null);
        Point now=node.bestChild.p;
        // toJudge.remove(now);
        putChess(now.x,now.y);
        isBlack=false;
    }

    // alpha beta dfs
    private static void dfs(int deep,Node root,int alpha,int beta,Point p){
        if(deep==searchDeep){
            root.mark=getMark();
            // System.out.printf("%d\t%d\t%d\n",p.x,p.y,root.mark);
            return;
        }
        ArrayList<Point> judgeSet=new ArrayList<Point>();
        Iterator it=toJudge.iterator();
        while(it.hasNext()){
            Point now=new Point((Point)it.next());
            judgeSet.add(now);
        }
        it=judgeSet.iterator();
        while(it.hasNext()){
            Point now=new Point((Point)it.next());
            Node node=new Node();
            node.setPoint(now);
            root.addChild(node);
            boolean flag=toJudge.contains(now);
            chessBoard[now.y][now.x]=((deep&1)==1)?-1:1;
            if(isEnd(now.x,now.y)){
                root.bestChild=node;
                root.mark=MAXN*chessBoard[now.y][now.x];
                chessBoard[now.y][now.x]=0;
                return;
            }

            boolean flags[]=new boolean[8]; //��ǻ���ʱҪ��Ҫɾ��
            Arrays.fill(flags,true);
            for(int i=0;i<8;++i){
                Point next=new Point(now.x+dc[i],now.y+dr[i]);
                if(1<=now.x+dc[i] && now.x+dc[i]<=size && 1<=now.y+dr[i] && now.y+dr[i]<=size && chessBoard[next.y][next.x]==0){
                    if(!toJudge.contains(next)){
                        toJudge.add(next);
                    }
                    else flags[i]=false;
                }
            }

            if(flag)
                toJudge.remove(now);
            dfs(deep+1,root.getLastChild(),alpha,beta,now);
            chessBoard[now.y][now.x]=0;
            if(flag)
                toJudge.add(now);
            for(int i=0;i<8;++i)
                if(flags[i])
                    toJudge.remove(new Point(now.x+dc[i],now.y+dr[i]));
            // alpha beta��֦
            // min��
            if((deep&1)==1){
                if(root.bestChild==null || root.getLastChild().mark<root.bestChild.mark){
                    root.bestChild=root.getLastChild();
                    root.mark=root.bestChild.mark;
                    if(root.mark<=MINN)
                        root.mark+=deep;
                    beta=Math.min(root.mark,beta);
                }
                if(root.mark<=alpha)
                    return;
            }
            // max��
            else{
                if(root.bestChild==null || root.getLastChild().mark>root.bestChild.mark){
                    root.bestChild=root.getLastChild();
                    root.mark=root.bestChild.mark;
                    if(root.mark==MAXN)
                        root.mark-=deep;
                    alpha=Math.max(root.mark,alpha);
                }
                if(root.mark>=beta)
                    return;
            }
        }
        // if(deep==0) System.out.printf("******************************************\n");
    }

    public static int getMark(){
        int res=0;
        for(int i=1;i<=size;++i){
            for(int j=1;j<=size;++j){
                if(chessBoard[i][j]!=0){
                    // ��
                    boolean flag1=false,flag2=false;
                    int x=j,y=i;
                    int cnt=1;
                    int col=x,row=y;
                    while(--col>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(col>0 && chessBoard[row][col]==0) flag1=true;
                    col=x;row=y;
                    while(++col<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(col<=size && chessBoard[row][col]==0) flag2=true;
                    if(flag1 && flag2)
                        res+=chessBoard[i][j]*cnt*cnt;
                    else if(flag1 || flag2) res+=chessBoard[i][j]*cnt*cnt/4;
                    if(cnt>=5) res=MAXN*chessBoard[i][j];
                    // ��
                    col=x;row=y;
                    cnt=1;flag1=false;flag2=false;
                    while(--row>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(row>0 && chessBoard[row][col]==0) flag1=true;
                    col=x;row=y;
                    while(++row<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(row<=size && chessBoard[row][col]==0) flag2=true;
                    if(flag1 && flag2)
                        res+=chessBoard[i][j]*cnt*cnt;
                    else if(flag1 || flag2)
                        res+=chessBoard[i][j]*cnt*cnt/4;
                    if(cnt>=5) res=MAXN*chessBoard[i][j];
                    // ��Խ���
                    col=x;row=y;
                    cnt=1;flag1=false;flag2=false;
                    while(--col>0 && --row>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(col>0 && row>0 && chessBoard[row][col]==0) flag1=true;
                    col=x;row=y;
                    while(++col<=size && ++row<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(col<=size && row<=size && chessBoard[row][col]==0) flag2=true;
                    if(flag1 && flag2)
                        res+=chessBoard[i][j]*cnt*cnt;
                    else if(flag1 || flag2) res+=chessBoard[i][j]*cnt*cnt/4;
                    if(cnt>=5) res=MAXN*chessBoard[i][j];
                    // �ҶԽ���
                    col=x;row=y;
                    cnt=1;flag1=false;flag2=false;
                    while(++row<=size && --col>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(row<=size && col>0 && chessBoard[row][col]==0) flag1=true;
                    col=x;row=y;
                    while(--row>0 && ++col<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
                    if(row>0 && col<=size && chessBoard[i][j]==0) flag2=true;
                    if(flag1 && flag2)
                        res+=chessBoard[i][j]*cnt*cnt;
                    else if(flag1 || flag2) res+=chessBoard[i][j]*cnt*cnt/4;
                    if(cnt>=5) res=MAXN*chessBoard[i][j];

                }
            }
        }
        return res;
    }

    // for debug
    public static void debug(){
        for(int i=1;i<=size;++i){
            for(int j=1;j<=size;++j){
                System.out.printf("%d\t",chessBoard[i][j]);
            }
            System.out.println("");
        }
    }

    // �ж��Ƿ�һ��ȡʤ
    public static boolean isEnd(int x,int y){
        // �ж�һ���Ƿ���������
        int cnt=1;
        int col=x,row=y;
        while(--col>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        col=x;row=y;
        while(++col<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        // �ж�һ���Ƿ���������
        col=x;row=y;
        cnt=1;
        while(--row>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        col=x;row=y;
        while(++row<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        // �ж���Խ����Ƿ���������
        col=x;row=y;
        cnt=1;
        while(--col>0 && --row>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        col=x;row=y;
        while(++col<=size && ++row<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        // �ж��ҶԽ����Ƿ���������
        col=x;row=y;
        cnt=1;
        while(++row<=size && --col>0 && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        col=x;row=y;
        while(--row>0 && ++col<=size && chessBoard[row][col]==chessBoard[y][x]) ++cnt;
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        return false;
    }


    public void userOption(int x,int y) {
        Ai.putChess(x, y);
        Ai.print();
        if (!Ai.isFinished) {
            Ai.isBlack = true;
            Ai.myAI();

            Ai.print();
        }
        Ai.isFinished = false;
    }

//    public static void main(String[] args) {
//        initChessBoard();
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            int x = scanner.nextInt();
//            int y = scanner.nextInt();
//            Ai.putChess(x, y);
//
//            Ai.print();
//
//            if (!Ai.isFinished) {
//                Ai.isBlack = true;
//                Ai.myAI();
//
//                Ai.print();
//            }
//            Ai.isFinished = false;
//        }
//    }

    public static void print(){
        System.out.println("----------------------------------------");
        int[][] chessBoard1 = chessBoard;
        for (int i = 0; i < chessBoard1.length; i++) {
            for (int i1 = 0; i1 < chessBoard1[0].length; i1++) {
                System.out.print(chessBoard1[i][i1]+"   ");
            }
            System.out.println();
        }

        System.out.println("----------------------------------------");
    }


}



// ���ڵ�
class Node{
    public Node bestChild=null;
    public ArrayList<Node> child=new ArrayList<Node>();
    public Point p=new Point();
    public int mark;
    Node(){
        this.child.clear();
        bestChild=null;
        mark=0;
    }
    public void setPoint(Point r){
        p.x=r.x;
        p.y=r.y;
    }
    public void addChild(Node r){
        this.child.add(r);
    }
    public Node getLastChild(){
        return child.get(child.size()-1);
    }

}