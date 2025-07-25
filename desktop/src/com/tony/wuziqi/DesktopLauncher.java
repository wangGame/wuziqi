package com.tony.wuziqi;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import kw.wzq.WuziQIGame;

public class DesktopLauncher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title="wuziqi";
        config.x = 1000;
        config.y = 0;
        config.width = (int) (360*1.5f);
        config.height = (int) (640*1.5f);
        new LwjglApplication(new WuziQIGame(),config);

    }
}
