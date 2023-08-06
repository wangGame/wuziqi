package com.tony.puzzle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import kw.maj.WuziQI;

public class DesktopLauncher {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title="maj";
        config.x = 1000;
        config.y = 0;
        config.width = (int) (1280);
        config.height = (int) (720);
        new LwjglApplication(new WuziQI(),config);

    }
}
