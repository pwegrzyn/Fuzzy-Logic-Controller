package demo;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class EntityRegister {

    private Set<Entity> activeEntities;
    private Set<Entity> toDeleteEntities;
    private Set<Entity> toAddEntities;

    public EntityRegister() {
        activeEntities = new HashSet<>();
        toAddEntities = new HashSet<>();
        toDeleteEntities = new HashSet<>();
    }

    public void register(Entity newEntity) {
        toAddEntities.add(newEntity);
    }

    public void unregister(Entity entity) {
        toDeleteEntities.add(entity);
    }

    public void synchronize() {
        activeEntities.removeAll(toDeleteEntities);
        activeEntities.addAll(toAddEntities);
        toDeleteEntities.clear();
        toAddEntities.clear();
    }

    public void foreach(Consumer<Entity> mapper) {

        for (Entity ent : this.activeEntities) {
            mapper.accept(ent);
        }
    }

    public Set<Entity> getActiveEntities(){
        return activeEntities;
    }

    public Set<Entity> getEnitiesToDelete(){
        return toDeleteEntities;
    }

    public Set<Entity> getEntitiesToAdd(){
        return toAddEntities;
    }

}
