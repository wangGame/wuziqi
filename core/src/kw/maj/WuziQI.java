package kw.maj;

import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.resource.annotation.GameInfo;

import kw.maj.screen.LoadingScreen;

/**
 * 使用的画笔
 */
@GameInfo(width = 1280,height = 720,batch = Constant.COUPOLYGONBATCH)
public class WuziQI extends BaseGame {
    @Override
    public void create() {
        super.create();
        setScreen(new LoadingScreen(this));
    }
}
