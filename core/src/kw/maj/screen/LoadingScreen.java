package kw.maj.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.screen.BaseScreen;
import com.kw.gdx.utils.Assert;

import kw.maj.group.WzqGroup;

public class LoadingScreen extends BaseScreen {

    public LoadingScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        WzqGroup group = new WzqGroup();
        addActor(group);
        group.setPosition(Constant.WIDTH/2,Constant.HIGHT/2, Align.center);
    }
}
