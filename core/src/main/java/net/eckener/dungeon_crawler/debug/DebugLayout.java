package net.eckener.dungeon_crawler.debug;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

/**
 * Controls the layout of the {@link DebugOverlay}
 */
public class DebugLayout {

    private final DebugStats stats;

    public DebugLayout(DebugStats stats) {
        this.stats = stats;
    }

    /**
     * Assembles the left side of the {@link DebugOverlay}
     * @return a list of strings to render
     */
    public List<String> leftColumn() {
        List<String> l = new ArrayList<>();

        l.add("FPS: " + stats.fps());
        l.add(String.format("Delta: %.4f", stats.delta()));
        l.add("Resolution: " +
            stats.screenWidth() + "x" + stats.screenHeight());

        l.add("Java Heap: " + stats.javaHeapMB() + " MB");

        l.add(String.format(
            "Cam: %.1f, %.1f  Zoom: %.2f",
            stats.camX(),
            stats.camY(),
            stats.zoom()
        ));

        return l;
    }
    /**
     * Assembles the right side of the {@link DebugOverlay}
     * @return a list of strings to render
     */
    public List<String> rightColumn() {
        List<String> r = new ArrayList<>();

        r.add("Platform: " + Gdx.app.getType());
        r.add("Version: " + Gdx.app.getVersion());

        return r;
    }
}
