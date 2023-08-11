package kw.maj.group;

import static kw.maj.newai.ConstanNum.GRID_NUMBER;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;

import java.util.ArrayList;

import kw.maj.ai.Ai;
import kw.maj.newai.Board;
import kw.maj.newai.ConstanNum;
import kw.maj.newai.Point;

public class WzqGroup extends Group {
    Group group;
    private  Board board;
    int chessArray [][];
    public WzqGroup(){
        setSize(700,700);
        setDebug(true);
        Image image = new Image(Asset.getAsset().getTexture("board.jpg"));
        addActor(image);
        image.setSize(getWidth(),getHeight());
        group = new Group();
        group.setSize(650,650);
        addActor(group);
        group.setDebug(true);
        group.setPosition(getWidth()/2,getHeight()/2, Align.center);
        init();
        group.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                int xx = (int) ((x-25) / 46);
                int yy = (int) ((y-25) / 46);
                chessArray[xx][yy] = ConstanNum.HUMEN;
                board.put(xx, yy, ConstanNum.HUMEN);
                setImage(ConstanNum.HUMEN,xx,yy);
                boolean end = isEnd(yy, xx);
                if (end){
                    System.out.println("用户 -----------success");
                }else {
                    group.setTouchable(Touchable.disabled);
                    group.addAction(Actions.delay(3, Actions.run(() -> {
                        compute();
                    })));
                }
            }
        });
    }

    public void compute(){

        Point point = board.findPoint(ConstanNum.COM, ConstanNum.searchDeep);
        board.put(point.x, point.y, ConstanNum.COM);
        chessArray[point.x][point.y] = ConstanNum.COM;
        setImage(ConstanNum.COM, point.x, point.y);
        boolean end = isEnd(point.y, point.x);
        if (end){
            System.out.println("-----------success");
        }
        group.setTouchable(Touchable.enabled);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        if (isFinished){
//            xxx += delta;
//            if (xxx>5){
//                isFinished=false;
//                init();
//                xxx = 0;
//
//            }
//            return;
//        }
//        xx += delta;
//        if (xx >1){
//            if (ff) {
//                Point point = board.findPoint(ConstanNum.HUMEN, ConstanNum.searchDeep);
//                board.put(point.x, point.y, ConstanNum.HUMEN);
//                chessArray[point.x][point.y] = ConstanNum.HUMEN;
//                setImage(ConstanNum.HUMEN, point.x, point.y);
//                boolean end = isEnd(point.y, point.x);
//                if (end){
//                    System.out.println("-----------success");
//                }
//            }else {
//                Point point = board.findPoint(ConstanNum.COM, ConstanNum.searchDeep);
//                board.put(point.x, point.y, ConstanNum.COM);
//                chessArray[point.x][point.y] = ConstanNum.COM;
//                setImage(ConstanNum.COM, point.x, point.y);
//                boolean end = isEnd( point.y,point.x);
//                if (end) {
//                    System.out.println("-----------success");
//                }
//            }
//            ff = !ff;
//            xx = 0;
//        }
    }

    private void init() {
        group.clearChildren();
        board = new Board();
        board.init();
      /*  int randomY=6;
        int randomX=6;*/
        int randomY=getRandom();
        int randomX=getRandom();

        chessArray = new int[GRID_NUMBER][GRID_NUMBER];
        board.put(randomX, randomY, ConstanNum.COM);
        chessArray[randomX][randomY] = ConstanNum.COM;
        setImage(ConstanNum.COM,randomX,randomY);
    }


    float size = GRID_NUMBER - 2;
    boolean isFinished = false;
    ArrayList<Point> success  = new ArrayList<>();
    public  boolean isEnd(int x,int y){
        success.clear();
        //记数   Point 记录 x y    行列

        //<-------------
        int cnt=1;
        int col=x;
        int row=y;
        success.add(new Point(col,row,1));
        while(--col>0 && chessArray[row][col]==chessArray[y][x]) {
            ++cnt;
            success.add(new Point(col,row,1));
        }

        //------------->
        col=x;
        row=y;
        while(++col<=size && chessArray[row][col]==chessArray[y][x]){
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
        while(++row<=size && chessArray[row][col]==chessArray[y][x]){
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
        while(++col<=size && ++row<=size && chessArray[row][col]==chessArray[y][x]){
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
        while(++row<=size && --col>0 && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row));
        }
        //右下
        col=x;
        row=y;
        while(--row>0 && ++col<=size && chessArray[row][col]==chessArray[y][x]){
            ++cnt;
            success.add(new Point(col,row));
        }
        if(cnt>=5){
            isFinished=true;
            return true;
        }
        return false;
    }

    private static int getRandom(){
        return (int)(Math.random()*10+2);
    }

    void setImage(int u,int x,int y){
        String path = "black_chess.png";
        if (u == 1) {
            path = "white_chess.png";
        }
        Image image = new Image(Asset.getAsset().getTexture(path));
        image.setName("image"+x+""+y);
        group.addActor(image);
        float xx = 46;
        image.setSize(xx,xx);
        image.setPosition(x*xx+ 50,y*xx+ 50, Align.center);
    }


}
