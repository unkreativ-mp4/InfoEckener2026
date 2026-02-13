package net.eckener.dungeon_crawler.debug;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Listens for the key presses needed for the {@link DebugOverlay}
 */
public class DebugInput extends InputAdapter {

    private boolean visible = false;

    /**
     * Checks in this case if F3 was pressed
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the correct key was pressed
     */
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.F3) {
            visible = !visible;
            return true;
        }
        return false;
    }

    /**
     * @return whether the {@link DebugOverlay} should be visible
     */
    public boolean visible() {
        return visible;
    }
}
