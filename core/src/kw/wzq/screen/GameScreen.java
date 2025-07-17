package kw.wzq.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;

import kw.wzq.group.WzqGroup;

public class GameScreen extends BaseScreen {
    private WzqGroup wzqGroup;
    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        initGameBg();
        initWzqView();
    }

    private void initWzqView() {
        wzqGroup = new WzqGroup();
        addActor(wzqGroup);
        wzqGroup.start();
        wzqGroup.setPosition(Constant.WIDTH/2,Constant.HIGHT/2, Align.center);
    }

    private void initGameBg() {
        Image bg = new Image(Asset.getAsset().getTexture("wood2.png"));
        addActor(bg);
        bg.setOrigin(Align.center);
        bg.setScale(Math.max((Constant.GAMEHIGHT / bg.getHeight()), (Constant.GAMEWIDTH / bg.getWidth())));
        bg.setPosition(Constant.GAMEWIDTH/2.0f,Constant.GAMEHIGHT/2.0f,Align.center);
    }
}
