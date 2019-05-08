package demo;

public class Configuration {

    private int targetHeight;
    private int targetWidth;
    private int targetFps;
    private int targetFrameTime;
    private KeybindSet keyMapping;
    private boolean UIvisibility = true;
    private boolean debug = true;
    private boolean frozenRender = false;
    private boolean frozenGameSessionThread = false;
    private boolean perfTestOn = false;
    private ImageLoader imgLoader;

    public Configuration() {
        this.targetHeight = 312;
        this.targetWidth = 500;
        this.targetFps = 100;
        this.targetFrameTime = getFrameTimeFromFps(this.targetFps);
    }

    public void setTargetHeight(int height) {
        this.targetHeight = height;
    }

    public int getTargetHeight() {
        return this.targetHeight;
    }

    public void setTargetWidth(int width) {
        this.targetWidth = width;
    }

    public int getTargetWidth() {
        return this.targetWidth;
    }

    public void setTargetFps(int fps) {
        this.targetFps = fps;
        this.targetFrameTime = getFrameTimeFromFps(fps);
    }

    public int getTargetFps() {
        return this.targetFps;
    }

    public int getTargetFrameTime() {
        return this.targetFrameTime;
    }

    private int getFrameTimeFromFps(int fps) {
        return 1000000000 / fps;
    }

    public void setKeyBinds(KeybindSet keyBinds) {
        this.keyMapping = keyBinds;
    }

    public KeybindSet getKeyBinds() {
        return this.keyMapping;
    }


    public void setUIVisibility(boolean state) {
        this.UIvisibility = state;
    }

    public boolean getUIVisibility() {
        return this.UIvisibility;
    }

    public void setDebug(boolean state) {
        this.debug = state;
    }

    public boolean getDebug() {
        return this.debug;
    }

    public boolean getFrozenRender() {
        return this.frozenRender;
    }

    public void setFrozenRender(boolean val) {
        this.frozenRender = val;
    }

    public boolean getFrozenGameSessionThread() {
        return this.frozenGameSessionThread;
    }

    public void setFrozenGameSessionThread(boolean val) {
        this.frozenGameSessionThread = val;
    }

    public boolean getPerfTestOn() {
        return this.perfTestOn;
    }

    public void setPerfTestOn(boolean val) {
        this.perfTestOn = val;
    }

}

