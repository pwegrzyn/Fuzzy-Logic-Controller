package demo;

import java.awt.image.BufferedImage;

public class Animation {

    private final AnimationType type;
    private BufferedImage[] animationFrames;
    private double runSpeed;
    private double runTimer;
    private int framePointer;

    public Animation(AnimationType type, BufferedImage[] frames, double speed) {
        this.type = type;
        this.animationFrames = frames;
        this.runSpeed = speed;
        this.framePointer = 0;
    }

    public void advanceSelf(double delta) {
        this.runTimer += delta;
        if(this.runTimer > this.runSpeed) {
            this.framePointer = (this.framePointer + 1) % this.animationFrames.length;
            this.runTimer = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return this.animationFrames[this.framePointer];
    }

    public AnimationType getType() {
        return this.type;
    }

    public BufferedImage[] getAllFrames() {
        return this.animationFrames;
    }

}
