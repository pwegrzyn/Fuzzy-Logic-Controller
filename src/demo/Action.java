package demo;

import java.util.function.Consumer;

public class Action {
    private Consumer<Entity> consumer;
    private Class<? extends Entity> affectedClass;

    public Action(Class<? extends Entity> affectedClass,  Consumer<Entity> consumer) {
        this.consumer = consumer;
        this.affectedClass = affectedClass;
    }

    public void activate(Entity entity) {
        if (affectedClass.isAssignableFrom(entity.getClass())) {
            consumer.accept(entity);
        }
    }
}
