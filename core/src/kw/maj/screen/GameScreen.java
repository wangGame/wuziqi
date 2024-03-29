package kw.maj.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.screen.BaseScreen;

import kw.maj.group.WzqGroup;
import kw.maj.newai.ConstanNum;

public class GameScreen extends BaseScreen {
    public GameScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        Image bg = new Image(Asset.getAsset().getTexture("wood2.png"));
        addActor(bg);
        bg.setOrigin(Align.center);
        float max = Math.max((Constant.GAMEHIGHT / bg.getHeight()), (Constant.GAMEWIDTH / bg.getWidth()));
        bg.setScale(max);
        bg.setPosition(Constant.GAMEWIDTH/2.0f,Constant.GAMEHIGHT/2.0f,Align.center);

        WzqGroup group = new WzqGroup();
        addActor(group);
        group.setPosition(Constant.WIDTH/2,Constant.HIGHT/2, Align.center);

        Image blackBtn = new Image(Asset.getAsset().getTexture("white_chess.png")){{
            addListener(new OrdinaryButtonListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ConstanNum.comColor = ConstanNum.HUMEN;
                    ConstanNum.userColor = ConstanNum.COM;
                }
            });
        }};
        Image whiteBtn = new Image(Asset.getAsset().getTexture("white_chess.png")){
            {
                addListener(new OrdinaryButtonListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        ConstanNum.comColor = ConstanNum.COM;
                        ConstanNum.userColor = ConstanNum.HUMEN;
                    }
                });
            }
        };
        Image xianshou = new Image(Asset.getAsset().getTexture("white_chess.png")){{
            addListener(new OrdinaryButtonListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    ConstanNum.userXianshou = 1;
                }
            });
        }};
        Image houshou = new Image(Asset.getAsset().getTexture("white_chess.png")){
            {
                addListener(new OrdinaryButtonListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        ConstanNum.userXianshou = 0;
                    }
                });
            }
        };
        Image start = new Image(Asset.getAsset().getTexture("white_chess.png"));
        start.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                group.start();
            }
        });
        addActor(new Table(){{
            add(blackBtn);
            add(whiteBtn);

            add(xianshou);
            add(houshou);

            add(start);
            pack();
            setPosition(Constant.GAMEWIDTH/2,100,Align.center);
        }});


    }
}
