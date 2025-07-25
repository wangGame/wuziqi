package kw.wzq.newai;

public class ConstanNum {
    //棋子
    public static final int EMPTY=0;
    public static final int  HUMEN=2;
    public static final int COM=1;

    public static int comColor = COM;
    public static int userColor = HUMEN;
    public static int userXianshou;

    //棋盘格数
    public static int GRID_NUMBER = 16;
    //模型打分
    /*
     * 棋型表示
     * 用一个6位数表示棋型，从高位到低位分别表示
     * 连五，活四，眠四，活三，活二/眠三，活一/眠二, 眠一
     */
    // 给单个棋型打分
    public static final int ONE=10,
    TWO=100,
    THREE=1000,
    FOUR=100000,
    FIVE=10000000,
    BLOCKED_ONE=1,
    BLOCKED_TWO=10,
    BLOCKED_THREE=100,
    BLOCKED_FOUR=10000;

    //ai设置
    public static final int countLimit=20; //gen函数返回的节点数量上限，超过之后将会按照分数进行截断
    public static final int timeLimit=100; // 时间限制，秒
    // 下面几个设置都是用来提升搜索速度的
    public static final int spreadLimit=1;// 单步延伸 长度限制

}
