//package kw.maj.ai;
//
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.Group;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
//import com.badlogic.gdx.scenes.scene2d.actions.Actions;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.utils.Align;
//import com.kw.gdx.asset.Asset;
//
//import java.awt.Point;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Iterator;
//
//public class Ai{
//    //棋盘宽高
//    private static final int size=14;
//    //棋子的颜色
//    public static boolean isBlack=false;
//    //期盼表格的大小，0和15不用   有效1-14
//    public static int[][] chessBoard=new int[size+2][size+2];
//    //ai可以放置的点，  这个主要是在点周围进行放置，
//    private static HashSet<Point> toJudge=new HashSet<Point>();
//    //8方向  行
//    private static int dr[]=new int[]{-1,1,-1,1,0,0,-1,1};
//    //列
//    private static int dc[]=new int[]{1,-1,-1,1,-1,1,0,0};
//    //得分
//    public static final int MAXN=1<<28;
//    public static final int MINN=-MAXN;
//    //深度
//    private static int searchDeep=4;
//    //success
//    public static boolean isFinished=false;
//    //
//    private static Group group;
//    public Ai(Group group) {
//        this.group = group;
//    }
//
//    //初始化
//    public  void initChessBoard(){
//        //开始黑棋
//        isBlack=false;
//        toJudge.clear();
//        //初始化棋盘
//        for(int i=1;i<=size;++i) {
//            for (int j = 1; j <= size; ++j) {
//                chessBoard[i][j] = 0;
//            }
//        }
//        //第一个放置点
//        chessBoard[8][8]=1;
//        //上下左右可以放置的棋子
//        for(int i=0;i<8;++i) {
//            if (1 <= 8 + dc[i]
//                    && 8 + dc[i] <= size
//                    && 1 <= 8 + dr[i]
//                    && 8 + dr[i] <= size) {
//                Point now = new Point(8 + dc[i], 8 + dr[i]);
//                if (!toJudge.contains(now))
//                    toJudge.add(now);
//            }
//        }
//        setImage(8,8);
//    }
//
//     void setImage(int x,int y){
//        String path = "black_chess.png";
//        if (isBlack) {
//            path = "white_chess.png";
//        }
//        Image image = new Image(Asset.getAsset().getTexture(path));
//        image.setName("image"+x+""+y);
//        group.addActor(image);
//        float xx = 46;
//        image.setSize(xx,xx);
//        image.setPosition(x*xx+ 50,y*xx+ 50, Align.center);
//    }
//
//    //放置棋子
//    public  boolean putChess(int x,int y){
//        if (chessBoard[y][x]!=0){
//            System.out.println("-------error---------------------");  //放置点不是0，说明计算出问题了
//        }
//        chessBoard[y][x]=isBlack?1:-1;
//        setImage(x,y);
//        if(isEnd(x,y)){
//            isBlack=true;
//            for (Point point : success) {
//                Actor actor = group.findActor("image" + point.x + "" + point.y);
//                if (actor!=null) {
//                    actor.addAction(Actions.sequence(Actions.repeat(5,Actions.sequence(
//                            Actions.fadeOut(0.4f),
//                            Actions.fadeIn(0.5f)
//                    )),Actions.run(()->{
//                        initChessBoard();
//                    })));
//                }
//            }
//            group.setTouchable(Touchable.disabled);
//            return true;
//        }
//        else{
//            Point p=new Point(x,y);
//            if(toJudge.contains(p))
//                toJudge.remove(p);
//            for(int i=0;i<8;++i){
//                Point now=new Point(p.x+dc[i],p.y+dr[i]);
//                if(1<=now.x
//                        && now.x<=size
//                        && 1<=now.y
//                        && now.y<=size
//                        && chessBoard[now.y][now.x]==0)
//                    toJudge.add(now);
//            }
//        }
//        return false;
//    }
//
//
//    public  void myAI(){
//        isBlack=!isBlack;
//        Node node=new Node();
//        dfs(0,node,MINN,MAXN,null);
//        Point now=node.bestChild.p;
//         toJudge.remove(now);
//        boolean b = putChess(now.x, now.y);
//
//
//        if (!b) {
//            group.addAction(Actions.delay(1, Actions.run(() -> {
//                myAI();
//            })));
//        }
//    }
//
//    // alpha beta dfs
//    private  void dfs(int deep,Node root,int alpha,int beta,Point p){
//        //深度到了，就返回          如果赢了也要返回
//        if(deep==searchDeep){
//            root.mark=getMark();
//            System.out.printf("%d\t%d\t%d\n",p.x,p.y,root.mark);
//            return;
//        }
//
//        //将可以遍历的点复制出来
//        ArrayList<Point> judgeSet=new ArrayList<Point>();
//        Iterator it=toJudge.iterator();
//        while(it.hasNext()){
//            Point now=new Point((Point)it.next());
//            judgeSet.add(now);
//        }
//        it=judgeSet.iterator();
//
//        //遍历临时点
//        while(it.hasNext()){
//            Point now=new Point((Point)it.next());
//            Node node=new Node();
//            node.setPoint(now);
//            root.addChild(node);//假定使用这个点
//            boolean flag=toJudge.contains(now);
//            chessBoard[now.y][now.x]=((deep&1)==1)?-1:1;
//            //成功就返回
//            if(isEnd(now.x,now.y)){
//                root.bestChild=node;
//                root.mark=MAXN*chessBoard[now.y][now.x];
//                chessBoard[now.y][now.x]=0;
//                return;
//            }
//
//            boolean flags[]=new boolean[8];//点周围有8个点，如果没包含在之前点集合里面的，在使用之后需还原
//            Arrays.fill(flags,true);
//            for(int i=0;i<8;++i){
//                Point next=new Point(now.x+dc[i],now.y+dr[i]);
//                if(1<=now.x+dc[i] && now.x+dc[i]<=size
//                        && 1<=now.y+dr[i] && now.y+dr[i]<=size
//                        && chessBoard[next.y][next.x]==0){
//                    if(!toJudge.contains(next)){
//                        toJudge.add(next);
//                    } else {
//                        flags[i]=false;
//                    }
//                }
//            }
//            //这个点刚才使用的时候已经使用掉了
//            if(flag) {
//                toJudge.remove(now);
//            }
//            //进入下一次
//            dfs(deep+1,root.getLastChild(),alpha,beta,now);
//            //恢复
//            chessBoard[now.y][now.x]=0;
//            //
//            if(flag) {
//                toJudge.add(now);
//            }
//            for(int i=0;i<8;++i) {
//                if (flags[i]) {
//                    toJudge.remove(new Point(now.x + dc[i], now.y + dr[i]));
//                }
//            }
//            // alpha beta��֦
//            // min��
//            if((deep&1)==1){
//                if(root.bestChild==null || root.getLastChild().mark<root.bestChild.mark){
//                    root.bestChild=root.getLastChild();
//                    root.mark=root.bestChild.mark;
//                    if(root.mark<=MINN) {
//                        root.mark += deep;
//                    }
//                    beta=Math.min(root.mark,beta);
//                }
//                if(root.mark<=alpha) {
//                    return;
//                }
//            } else{
//                if(root.bestChild==null || root.getLastChild().mark>root.bestChild.mark){
//                    root.bestChild=root.getLastChild();
//                    root.mark=root.bestChild.mark;
//                    if(root.mark>=MAXN) {
//                        root.mark -= deep;
//                    }
//                    alpha=Math.max(root.mark,alpha);
//                }
//                if(root.mark>=beta) {
//                    return;
//                }
//            }
//        }
//        // if(deep==0) System.out.printf("******************************************\n");
//    }
//
//    public  int getMark(){
//        //计算结果
//        int res=0;
//        for(int i=1;i<=size;++i){
//            for(int j=1;j<=size;++j){
//                //找到一个不为0的
//                if(chessBoard[i][j]!=0){
//                    //记录一边是不是被堵死
//                    boolean flag1=false;
//                    boolean flag2=false;
//                    int x=j;
//                    int y=i;
//                    int cnt=1;
//                    int col=x;
//                    int row=y;
//                    while(--col>0 && chessBoard[row][col]==chessBoard[y][x]) {
//                        ++cnt;
//                    }
//                    if(col>0 && chessBoard[row][col]==0){
//                        flag1=true;
//                    }
//
//                    col=x;
//                    row=y;
//                    while(++col<=size && chessBoard[row][col]==chessBoard[y][x]){
//                        ++cnt;
//                    }
//                    if(col<=size && chessBoard[row][col]==0){
//                        flag2=true;
//                    }
//
//                    //一边堵死分数都会大打折扣
//                    if(flag1 && flag2) {
//                        res += chessBoard[i][j] * cnt * cnt;
//                    } else if(flag1 || flag2){
//                        res+=chessBoard[i][j]*cnt*cnt/4;
//                    }
//                    if(cnt>=5) {
//                        res=MAXN*chessBoard[i][j];
//                    }
//
//
//                    col=x;
//                    row=y;
//                    cnt=1;
//                    flag1=false;
//                    flag2=false;
//                    while(--row>0 && chessBoard[row][col]==chessBoard[y][x]){
//                        ++cnt;
//                    }
//                    if(row>0 && chessBoard[row][col]==0) {
//                        flag1=true;
//                    }
//                    col=x;
//                    row=y;
//                    while(++row<=size && chessBoard[row][col]==chessBoard[y][x]) {
//                        ++cnt;
//                    }
//                    if(row<=size && chessBoard[row][col]==0) {
//                        flag2=true;
//                    }
//                    if(flag1 && flag2) {
//                        res += chessBoard[i][j] * cnt * cnt;
//                    } else if(flag1 || flag2){
//                        res+=chessBoard[i][j]*cnt*cnt/4;
//                    }
//
//                    if(cnt>=5){
//                        res=MAXN*chessBoard[i][j];
//                    }
//
//
//                    col=x;
//                    row=y;
//                    cnt=1;
//                    flag1=false;
//                    flag2=false;
//                    while(--col>0 && --row>0 && chessBoard[row][col]==chessBoard[y][x]){
//                        ++cnt;
//                    }
//                    if(col>0 && row>0 && chessBoard[row][col]==0) {
//                        flag1=true;
//                    }
//                    col=x;
//                    row=y;
//                    while(++col<=size && ++row<=size && chessBoard[row][col]==chessBoard[y][x]) {
//                        ++cnt;
//                    }
//                    if(col<=size && row<=size && chessBoard[row][col]==0){
//                        flag2=true;
//                    }
//                    if(flag1 && flag2){
//                        res+=chessBoard[i][j]*cnt*cnt;
//                    }else if(flag1 || flag2){
//                        res+=chessBoard[i][j]*cnt*cnt/4;
//                    }
//                    if(cnt>=5){
//                        res=MAXN*chessBoard[i][j];
//                    }
//
//                    col=x;
//                    row=y;
//                    cnt=1;
//                    flag1=false;
//                    flag2=false;
//                    while(++row<=size && --col>0 && chessBoard[row][col]==chessBoard[y][x]){
//                        ++cnt;
//                    }
//                    if(row<=size && col>0 && chessBoard[row][col]==0){
//                        flag1=true;
//                    }
//                    col=x;
//                    row=y;
//                    while(--row>0 && ++col<=size && chessBoard[row][col]==chessBoard[y][x]){
//                        ++cnt;
//                    }
//                    if(row>0 && col<=size && chessBoard[i][j]==0) {
//                        flag2=true;
//                    }
//                    if(flag1 && flag2) {
//                        res += chessBoard[i][j] * cnt * cnt;
//                    } else if(flag1 || flag2){
//                        res+=chessBoard[i][j]*cnt*cnt/4;
//                    }
//                    if(cnt>=5) res=MAXN*chessBoard[i][j];
//                }
//            }
//        }
//        return res;
//    }
//
//    // for debug
//    public  void debug(){
//        for(int i=1;i<=size;++i){
//            for(int j=1;j<=size;++j){
//                System.out.printf("%d\t",chessBoard[i][j]);
//            }
//            System.out.println("");
//        }
//    }
//
//    ArrayList<Point> success = new ArrayList<>();
//    //放置棋子进行判断是否成功
//    public  boolean isEnd(int x,int y){
//        success.clear();
//        //记数   Point 记录 x y    行列
//
//        //<-------------
//        int cnt=1;
//        int col=x;
//        int row=y;
//        success.add(new Point(col,row));
//        while(--col>0 && chessBoard[row][col]==chessBoard[y][x]) {
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//
//        //------------->
//        col=x;
//        row=y;
//        while(++col<=size && chessBoard[row][col]==chessBoard[y][x]){
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//        if(cnt>=5){
//            isFinished=true;
//            return true;
//        }
//        success.clear();
//
//        //          /\
//        //         /|\
//        //          |
//        //          |
//        //          |
//        //          |
//        //          |
//        //          |
//        //          |
//
//        col=x;
//        row=y;
//        cnt=1;
//        success.add(new Point(col,row));
//        while(--row>0 && chessBoard[row][col]==chessBoard[y][x]){
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//        col=x;
//        row=y;
//        while(++row<=size && chessBoard[row][col]==chessBoard[y][x]){
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//        if(cnt>=5){
//            isFinished=true;
//            return true;
//        }
//
//        success.clear();
//        //左下
//        col=x;
//        row=y;
//        cnt=1;
//        success.add(new Point(col,row));
//        while(--col>0 && --row>0 && chessBoard[row][col]==chessBoard[y][x]){
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//        //右上
//        col=x;
//        row=y;
//        while(++col<=size && ++row<=size && chessBoard[row][col]==chessBoard[y][x]){
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//        if(cnt>=5){
//            isFinished=true;
//            return true;
//        }
//
//        success.clear();
//
//        //左上
//        col=x;
//        row=y;
//        cnt=1;
//        success.add(new Point(col,row));
//        while(++row<=size && --col>0 && chessBoard[row][col]==chessBoard[y][x]){
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//        //右下
//        col=x;
//        row=y;
//        while(--row>0 && ++col<=size && chessBoard[row][col]==chessBoard[y][x]){
//            ++cnt;
//            success.add(new Point(col,row));
//        }
//        if(cnt>=5){
//            isFinished=true;
//            return true;
//        }
//        return false;
//    }
//
//
//    public void userOption(int x,int y) {
//        isBlack=!isBlack;
//        if (x>=1&&x<=size && y>=1&&y<=size) {
//            if (chessBoard[y][x] == 0) {
//                putChess(x, y);
//                print();
//                if (!Ai.isFinished) {
//                    Ai.isBlack = true;
//                    myAI();
//                    print();
//                }
//                Ai.isFinished = false;
//            }
//        }
//
//    }
//
////    public  void main(String[] args) {
////        initChessBoard();
////        Scanner scanner = new Scanner(System.in);
////        while (true) {
////            int x = scanner.nextInt();
////            int y = scanner.nextInt();
////            Ai.putChess(x, y);
////
////            Ai.print();
////
////            if (!Ai.isFinished) {
////                Ai.isBlack = true;
////                Ai.myAI();
////
////                Ai.print();
////            }
////            Ai.isFinished = false;
////        }
////    }
//
//    public  void print(){
//        System.out.println("----------------------------------------");
//        int[][] chessBoard1 = chessBoard;
//        for (int i = 0; i < chessBoard1.length; i++) {
//            for (int i1 = 0; i1 < chessBoard1[0].length; i1++) {
//                System.out.print(chessBoard1[i][i1]+"   ");
//            }
//            System.out.println();
//        }
//
//        System.out.println("----------------------------------------");
//    }
//
//
//}
//
//
//
//// ���ڵ�
//class Node{
//    public Node bestChild=null;
//    public ArrayList<Node> child=new ArrayList<Node>();
//    public Point p=new Point();
//    public int mark;
//    Node(){
//        this.child.clear();
//        bestChild=null;
//        mark=0;
//    }
//    public void setPoint(Point r){
//        p.x=r.x;
//        p.y=r.y;
//    }
//    public void addChild(Node r){
//        this.child.add(r);
//    }
//    public Node getLastChild(){
//        return child.get(child.size()-1);
//    }
//
//}