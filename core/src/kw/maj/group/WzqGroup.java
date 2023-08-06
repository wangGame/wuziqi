package kw.maj.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

import kw.maj.ai.Ai;

public class WzqGroup extends Group {
    public WzqGroup(){
        setSize(700,700);
        setDebug(true);
        Image image = new Image(Asset.getAsset().getTexture("board.jpg"));
        addActor(image);
        image.setSize(getWidth(),getHeight());
        Group group = new Group();
        group.setSize(650,650);
        addActor(group);
        group.setDebug(true);
        group.setPosition(getWidth()/2,getHeight()/2, Align.center);
        Ai ai = new Ai(group);
        ai.initChessBoard();
        group.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                int v = (int) (x / 50);
                int v1 = (int) (y / 50);
                ai.userOption(v,v1);
            }
        });
    }
}
