package demo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Entity {

    protected BufferedImage texture;
    protected Map<AnimationType, Animation> animations;
    protected Animation currentAnimation;
    protected double xPos;
    protected double yPos;
    protected Boolean collidable;
    protected double collisionRadius;
    protected double angularRotation;
    protected Area area;
    public List<Effect> activeEffects;
    public List<Action> spreadingActions;
    private Map<Entity, Integer> collisionCache;

    public Entity(double xInit, double yInit, double collisionRadius) {
        this.xPos = xInit;
        this.yPos = yInit;
        this.collidable = true;
        this.collisionRadius = collisionRadius;
        this.angularRotation = 0.0;
        this.activeEffects = new LinkedList<>();
        this.spreadingActions = new LinkedList<>();
        this.collisionCache = new HashMap<>();
        this.animations = new HashMap<>();
    }

    public void advanceSelf(double delta) {
        checkCollisions();
        updateEffects();
        if(this.currentAnimation != null) {
            this.currentAnimation.advanceSelf(delta);
        }
    }

    public final void renderSelf(Graphics graphics, Camera currentCamera) {
        int width = GlobalConfig.get().getTargetWidth();
        int height = GlobalConfig.get().getTargetHeight();
        int xApparent = (int) xPos - currentCamera.getXPos() + width / 2;
        int yApparent = (int) yPos - currentCamera.getYPos() + height / 2;
        if(this.texture != null) {
            graphics.drawImage(this.texture, xApparent - texture.getWidth() / 2, yApparent - texture.getHeight() / 2,
                    texture.getWidth(), texture.getHeight(), null);
        } else if(this.currentAnimation != null) {
            BufferedImage frame = this.currentAnimation.getCurrentFrame();
            graphics.drawImage(frame, xApparent - frame.getWidth() / 2, yApparent - frame.getHeight() / 2,
                    frame.getWidth(), frame.getHeight(), null);
        }
    }

    public final void pickAnimation(AnimationType type) {
        this.currentAnimation = this.animations.get(type);
    }

    public final void assignAnimation(Animation anim) {
        this.animations.put(anim.getType(), anim);
    }

    public final void checkCollisions() {
        for (Entity cachedEntity : collisionCache.keySet()){
            collisionCache.put(cachedEntity, 0);
        }
        List<Entity> collidedEntities = CollisionEngine.getCollisions(this, this.area);
        for (Entity ent : collidedEntities) {
//            if(ent instanceof MeleeMob) {
//                this.performCollisionAction(ent);
//            }
//            else if (!collisionCache.containsKey(ent)) {
//                this.performCollisionAction(ent);
//            }
            collisionCache.put(ent,  1);
        }
        collisionCache.keySet().removeIf(ent -> collisionCache.get(ent) == 0);
    }

    public final void updateEffects() {
        for (Effect effect : activeEffects) {
            effect.updateEffect(this);
        }
        activeEffects.removeIf(eff -> eff.isFinished());
    }

    public void performCollisionAction(Entity entity) {
        for (Action effect : entity.spreadingActions) {
            effect.activate(this);
        }
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public double getCollisionRadius() {
        return collisionRadius;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Map<Entity, Integer> getCollisionCache() {
        return collisionCache;
    }

}
