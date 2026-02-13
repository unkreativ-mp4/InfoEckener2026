package net.eckener.dungeon_crawler.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Gathers the statistics whhich are displayed in the {@link DebugOverlay}
 */
public class DebugStats {

    private final OrthographicCamera camera;

    public DebugStats(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * @return the FPS
     */
    public int fps() {
        return Gdx.graphics.getFramesPerSecond();
    }

    /**
     * @return the Frametime
     */
    public float delta() {
        return Gdx.graphics.getDeltaTime();
    }

    /**
     * @return the screen width
     */
    public int screenWidth() {
        return Gdx.graphics.getWidth();
    }

    /**
     * @return the screen height
     */
    public int screenHeight() {
        return Gdx.graphics.getHeight();
    }

    /**
     * @return the javaHeap in megabytes
     */
    public long javaHeapMB() {
        return Gdx.app.getJavaHeap() / 1024 / 1024;
    }

    /**
     * @return the x-Position of the {@link OrthographicCamera}
     */
    public float camX() {
        return camera.position.x;
    }

    /**
     * @return the y-Position of the {@link OrthographicCamera}
     */
    public float camY() {
        return camera.position.y;
    }

    /**
     * @return the zoom of the {@link OrthographicCamera}
     */
    public float zoom() {
        return camera.zoom;
    }
}
