package kw.maj;

import com.badlogic.gdx.graphics.Color;
import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.resource.annotation.GameInfo;

import kw.maj.screen.LoadingScreen;

/**
 * 使用的画笔
 */
@GameInfo(width = 720,height = 1280,batch = Constant.COUPOLYGONBATCH)
public class WuziQIGame extends BaseGame {
    public WuziQIGame(){
        Constant.viewColor = Color.WHITE;
    }

    @Override
    public void create() {
        super.create();
        setScreen(new LoadingScreen(this));
    }
}
