package kw.maj.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
        Image singleMode = new Image(Asset.getAsset().getTexture("white_chess.png"));
        singleMode.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setScreen(GameScreen.class);
            }
        });
        Image multMode = new Image(Asset.getAsset().getTexture("white_chess.png"));
        addActor(new Table(){{
            add(singleMode);
            row();
            add(multMode);
            pack();
            setPosition(Constant.GAMEWIDTH/2,Constant.GAMEHIGHT/2,Align.center);
        }});

    }
}
