package net.eckener.dungeon_crawler.debug;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class DebugInput extends InputAdapter {

    private boolean visible = false;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.F3) {
            visible = !visible;
            System.out.println("hbudffhdf");
            return true;
        }
        return false;
    }

    public boolean visible() {
        return visible;
    }
}
