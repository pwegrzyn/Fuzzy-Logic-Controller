package demo;

public class RangedMob extends Mob {

    private int attackCooldown = 0;
    private long lastAttackTime = 0;

    public RangedMob(double xInit, double yInit, double collisionRadius) {
        super(xInit, yInit, collisionRadius);
        this.velocity = 110;
        this.aggresive = false;
        this.velocity0 = velocity;
    }

    @Override
    public void makeAttack() {

    }

}
