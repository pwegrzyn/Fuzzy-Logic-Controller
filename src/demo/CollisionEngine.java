package demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CollisionEngine {

    private static CollisionDetectionStrategy strategy;

    public static void setStrategy(String str) {
        if(str.equals("circle")) {
            strategy = new CircleCollisionStrategy();
        } else if(str.equals("square")) {
            strategy = new SquareCollisionStrategy();
        }
    }

    public static CollisionDetectionStrategy getStrategy() {
        return strategy;
    }

    public static List<Entity> getCollisions(Entity testedEntity, Area area){
        Set<Entity> activeEntities = area.getEntityRegister().getActiveEntities();
        List<Entity> collidedEntities = new LinkedList<>();
        for (Entity ent : activeEntities) {
            if (ent != testedEntity) {
                if (strategy.doCollide(testedEntity, ent))
                    collidedEntities.add(ent);
            }
        }
        return collidedEntities;
    }

    private static class CircleCollisionStrategy implements CollisionDetectionStrategy {

        @Override
        public boolean doCollide(Entity entA, Entity entB) {

            return Math.sqrt(Math.pow(Math.abs(entB.getXPos() - entA.getXPos()), 2) + Math.pow(Math.abs(entB.getYPos()
                    - entA.getYPos()), 2)) < entB.getCollisionRadius() + entA.getCollisionRadius();

        }
    }

    private static class SquareCollisionStrategy implements CollisionDetectionStrategy {

        @Override
        public boolean doCollide(Entity entA, Entity entB) {

            // TODO
            return false;

        }
    }

}
