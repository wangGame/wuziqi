package kw.wzq.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.screen.BaseScreen;

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
        addActor(singleMode);
        singleMode.setPosition(Constant.GAMEWIDTH/2f,Constant.GAMEHIGHT/2f,Align.center);
    }
}
