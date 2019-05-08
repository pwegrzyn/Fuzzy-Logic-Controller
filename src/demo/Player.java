package demo;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;


public class Player extends Entity {

    private InputManager inputManager;
    private int attackCooldown = 0;
    private long lastAttackTime = 0;
    private int manaRegenCooldown = 100;
    private long lastManaRegenTime = 0;
    private int score;
    private boolean godmode;
    private BufferedImage graveTexture;
    private double ProjectileSwitchCooldown = 0.5;
    private double projectileSwitchTimer = 0;
    private double consumableUseCooldown = 0.5;
    private double consumableUseTimer = 0;
    private double speedChangeTimer = 0;
    private double speedChangeDuration = 0;
    private double speedMultiplier = 1.0;
    private int speed = 30;
    private int verticalSpeed = 0;
    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private Vector moveVector = new Vector(0, 0);
    private boolean performingAttack;
    private int hppot_amount = 0;
    private int manapot_amount = 0;
    private double idleTimer = 0;
    private double idleCooldown = 0.1;
    private TurnDirection direction;
    private static TurnDirection textureDirection = TurnDirection.RIGHT;

    public Player(double xInit, double yInit) {

        super(xInit, yInit, 28);
        this.godmode = false;
        this.direction = textureDirection;

        Animation animation = new Animation(AnimationType.WALK, new BufferedImage[] {
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f0.png"),
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f1.png"),
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f2.png"),
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f3.png")
        }, 0.1);
        assignAnimation(animation);
        animation = new Animation(AnimationType.IDLE, new BufferedImage[] {
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f0.png"),
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f0.png"),
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f0.png"),
                ImageLoader.active.fetchImage("resources/wizzard_m_idle_anim_f0.png")
        }, 0.1);
        assignAnimation(animation);

    }

    @Override
    public void advanceSelf(double delta) {

        super.advanceSelf(delta);

        reactToUserInput(delta);

        speedChangeHandler(delta);

        // check if not walked out of the area
        if(this.xPos >= area.getXSize()) {
            System.exit(0);
        }
        if(this.yPos >= area.getYSize()) {
            this.yPos = area.getYSize();
        }
        if(this.xPos < 0) {
            this.xPos = 0;
        }
        if(this.yPos < 0) {
            this.yPos = 0;
        }

    }

    private void reactToUserInput(double delta) {

        boolean isInputDisabled = inputManager.isNonChatInputDisabled();

        // handle displacement
        boolean changedPosition = false;
        if (true && !isInputDisabled) {
            this.yPos += verticalSpeed * this.speedMultiplier * delta;
            changedPosition = true;
        }

        // Never go to the right
        if (false && !isInputDisabled) {
            this.xPos -= this.speed * this.speedMultiplier * delta;
            changedPosition = true;
            this.direction = TurnDirection.LEFT;
        }
        // Constantly moving to the right for the purpose of the demo
        if (!isInputDisabled) {
            this.xPos += this.speed * this.speedMultiplier * delta;
            changedPosition = true;
            this.direction = TurnDirection.RIGHT;
        }
        if(changedPosition) {
            pickAnimation(AnimationType.WALK);
        } else {
            this.idleTimer += delta;
            if(this.idleTimer > this.idleCooldown) {
                idleTimer = 0;
                pickAnimation(AnimationType.IDLE);
            }
        }

        // Check if need to flip avatar direction
        if(this.direction != textureDirection) {
            for(BufferedImage img : this.animations.get(AnimationType.IDLE).getAllFrames()) {
                ImageLoader.active.flipImageHorizontally(img);
            }
            for (BufferedImage img : this.animations.get(AnimationType.WALK).getAllFrames()) {
                ImageLoader.active.flipImageHorizontally(img);
            }
            textureDirection = this.direction;
        }

        // check character facing look
        if (inputManager.getPressedByName("lookUp") && !isInputDisabled) {
            if (!up) {
                this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
                up = true;
                performingAttack = true;
            }
        }
        else {
            if (up) {
                up = false;
                this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
            }
        }

        if (inputManager.getPressedByName("lookDown") && !isInputDisabled) {
            if (!down) {
                down = true;
                this.angularRotation = moveVector.addAndUpdate(0,  1, this.angularRotation);
                performingAttack = true;
            }
        }
        else {
            if (down) {
                down = false;
                this.angularRotation = moveVector.addAndUpdate(0,  -1, this.angularRotation);
            }
        }

        if (inputManager.getPressedByName("lookLeft") && !isInputDisabled) {
            if (!left) {
                left = true;
                this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
                performingAttack = true;
            }
        }
        else {
            if (left) {
                left = false;
                this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
            }
        }

        if (inputManager.getPressedByName("lookRight") && !isInputDisabled) {
            if (!right) {
                right = true;
                this.angularRotation = moveVector.addAndUpdate(1,  0, this.angularRotation);
                performingAttack = true;
            }
        }
        else {
            if (right) {
                right = false;
                this.angularRotation = moveVector.addAndUpdate(-1,  0, this.angularRotation);
            }
        }

    }


    @Override
    public void performCollisionAction(Entity entity) {

//        if(entity instanceof Projectile) {
//            if(((Projectile)entity).getOwner() == this) {
//                return;
//            }
//        }
        super.performCollisionAction(entity);
        // Kek

        double currentDistance = Math.sqrt(Math.pow(Math.abs(entity.getXPos() - this.getXPos()), 2)
                + Math.pow(Math.abs(entity.getYPos() - this.getYPos()), 2));
        this.yPos = entity.getYPos() + (this.getYPos() > entity.getYPos() ? 1 : -1) * Math.abs(this.getYPos()
                - entity.getYPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);
        this.xPos = entity.getXPos() + (this.getXPos() > entity.getXPos() ? 1 : -1) * Math.abs(this.getXPos()
                - entity.getXPos())/currentDistance * (this.collisionRadius + entity.collisionRadius);

    }

    public void setInputManager(InputManager input) {
        this.inputManager = input;
    }

    private void speedChangeHandler(double delta) {
        speedChangeTimer += delta;
        if(this.speedChangeTimer > this.speedChangeDuration) {
            this.speedChangeTimer = 0;
            this.speedMultiplier = 1.0;
            this.speedChangeDuration = 0.0;
            return;
        }
    }

    public void changeSpeedTemporarily(double time, double multiplier) {
        this.speedChangeDuration = time;
        this.speedMultiplier = multiplier;
    }

    public double getSpeedMultiplayer() {
        return this.speedMultiplier;
    }

    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }
}

enum TurnDirection {
    LEFT, RIGHT
}

