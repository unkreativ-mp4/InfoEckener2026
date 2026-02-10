package net.eckener.dungeon_crawler.debug;

import com.badlogic.gdx.InputProcessor;

public class DebugOverlay {

    private final DebugLayout layout;
    private final DebugRenderer renderer;
    private final DebugInput input;

    public DebugOverlay(
        DebugLayout layout,
        DebugRenderer renderer,
        DebugInput input
    ) {
        this.layout = layout;
        this.renderer = renderer;
        this.input = input;
    }

    public void render() {
        if (!input.visible()) return;

        renderer.drawLeft(layout.leftColumn());
        renderer.drawRight(layout.rightColumn());
    }

    public InputProcessor input() {
        return input;
    }
}
