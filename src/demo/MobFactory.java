package demo;

import java.awt.image.BufferedImage;

public class MobFactory {

    public Mob createRangedBoss(double xInit, double yInit) {
        RangedMob newMob = new RangedMob(xInit, yInit, 36);
        newMob.setAggresive(false);
        Animation animation = new Animation(AnimationType.IDLE, new BufferedImage[] {
                ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f0.png"),
                ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f1.png"),
                ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f2.png"),
                ImageLoader.active.fetchImage("resources/big_demon_idle_anim_f3.png")
        }, 0.1);
        newMob.assignAnimation(animation);
        newMob.pickAnimation(AnimationType.IDLE);
        return newMob;
    }


}
