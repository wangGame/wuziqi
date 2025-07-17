package kw.wzq.group;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kw.wzq.newai.GameLogicBoard;
import kw.wzq.newai.ConstanNum;
import kw.wzq.newai.Point;

public class WzqGroup extends Group {
    private Group touchGroup;
    private GameLogicBoard board;
    private int chessArray [][];
    private int tableSize;
    private boolean isFinished = false;
    private ArrayList<Point> success  = new ArrayList<>();
    private ExecutorService executorService;
    public WzqGroup(){
        executorService = Executors.newFixedThreadPool(1);
        setSize(700,700);
        Image image = new Image(Asset.getAsset().getTexture("board.jpg"));
        addActor(image);
        image.setSize(getWidth(),getHeight());

        this.tableSize =ConstanNum.GRID_NUMBER - 2;

        touchGroup = new Group();
        touchGroup.setSize(650,650);
        addActor(touchGroup);
        touchGroup.setPosition(getWidth()/2,getHeight()/2, Align.center);
        touchGroup.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                int xx = (int) ((x-25) / 46);
                int yy = (int) ((y-25) / 46);
                chessArray[xx][yy] = ConstanNum.HUMEN;
                board.put(xx, yy, ConstanNum.HUMEN);

                board.printScore();

                setImage(ConstanNum.userColor,xx,yy);
                boolean end = isEnd(yy, xx);
                if (end){
                    touchGroup.setTouchable(Touchable.disabled);
                }else {
                    touchGroup.setTouchable(Touchable.disabled);
                    compute();
                }
            }
        });
    }


    private Point aiPoint;
    public void compute(){

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                aiPoint = board.findPoint(ConstanNum.COM, 12);
                addAction(Actions.delay(0,Actions.run(()->{
                    board.put(aiPoint.getX(), aiPoint.getY(), ConstanNum.COM);
                    board.printScore();
                    chessArray[aiPoint.getX()][aiPoint.getY()] = ConstanNum.COM;
                    setImage(ConstanNum.comColor, aiPoint.getX(), aiPoint.getY());
                    boolean end = isEnd(aiPoint.getY(), aiPoint.getX());
                    if (end){
                        System.out.println("computer success!");
                        touchGroup.setTouchable(Touchable.disabled);
                        return;
                    }
                    touchGroup.setTouchable(Touchable.enabled);
                    aiPoint = null;
                })));
            }
        });
    }

    private void init() {
        touchGroup.clearChildren();
        board = new GameLogicBoard();
        board.init();
        chessArray = new int[ConstanNum.GRID_NUMBER][ConstanNum.GRID_NUMBER];
    }

    public  boolean isEnd(int x,int y){
        success.clear();
        //记数   Point 记录 x y    行列
        //<-------------
        int cnt=1;
        int col=x;
        int row=y;
        //当前位置
        success.add(new Point(col,row,1));
        //同色  x减去
        while(--col>0 && chessArray[row][col]==chessArray[y][x]) {
            ++cnt;
            success.add(new Point(col,row,1));
        }

        //------------->
        col=x;
        row=y;
        while(++col<= tableSize && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row,1));
        }
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        success.clear();

        //          /\
        //         /|\
        //          |
        //          |
        //          |
        //          |
        //          |
        //          |
        //          |

        col=x;
        row=y;
        cnt=1;
        success.add(new Point(col,row));
        while(--row>0 && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row));
        }
        col=x;
        row=y;
        while(++row<= tableSize && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row));
        }
        if(cnt>=5){
            isFinished=true;
            return true;
        }

        success.clear();
        //左下
        col=x;
        row=y;
        cnt=1;
        success.add(new Point(col,row,1));
        while(--col>0 && --row>0 && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row,1));
        }
        //右上
        col=x;
        row=y;
        while(++col<= tableSize && ++row<= tableSize && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row));
        }
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        success.clear();
        //左上
        col=x;
        row=y;
        cnt=1;
        success.add(new Point(col,row));
        while(++row<= tableSize && --col>0 && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row));
        }
        //右下
        col=x;
        row=y;
        while(--row>0 && ++col<= tableSize && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row));
        }
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        return false;
    }

    void setImage(int u,int x,int y){
        String path = "black_chess.png";
        if (u == 1) {
            path = "white_chess.png";
        }
        Image image = new Image(Asset.getAsset().getTexture(path));
        image.setName("image"+x+""+y);
        touchGroup.addActor(image);
        float xx = 46;
        image.setSize(xx,xx);
        image.setPosition(x*xx+ 50,y*xx+ 50, Align.center);
    }

    public void start() {
        init();
        touchGroup.setTouchable(Touchable.enabled);
    }

}
