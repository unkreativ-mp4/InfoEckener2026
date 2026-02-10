package net.eckener.dungeon_crawler.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class DebugStats {

    private final OrthographicCamera camera;

    public DebugStats(OrthographicCamera camera) {
        this.camera = camera;
    }

    public int fps() {
        return Gdx.graphics.getFramesPerSecond();
    }

    public float delta() {
        return Gdx.graphics.getDeltaTime();
    }

    public int screenWidth() {
        return Gdx.graphics.getWidth();
    }

    public int screenHeight() {
        return Gdx.graphics.getHeight();
    }

    public long javaHeapMB() {
        return Gdx.app.getJavaHeap() / 1024 / 1024;
    }

    public float camX() {
        return camera.position.x;
    }

    public float camY() {
        return camera.position.y;
    }

    public float zoom() {
        return camera.zoom;
    }
}
