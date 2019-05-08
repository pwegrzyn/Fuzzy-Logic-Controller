package demo;

import java.util.function.Consumer;

public class Effect {

    private int effectLength = 0;
    private Consumer<Entity> initialConsumer;
    private Consumer<Entity> finalConsumer;
    private long effectEndTime;
    private State state;

    public Effect(int effectLength, Consumer<Entity> initialConsumer, Consumer<Entity> finalConsumer) {
        this.initialConsumer = initialConsumer;
        this.effectEndTime = System.currentTimeMillis() + effectLength;
        this.finalConsumer = finalConsumer;
        this.state = State.INIT;
    }

    public void updateEffect(Entity entity) {
        if (state == State.INIT || state == State.RUNNING) {
            state = State.RUNNING;
            initialConsumer.accept(entity);
        }
        if (System.currentTimeMillis() > effectEndTime && state != State.FINISHED) {

            finalConsumer.accept(entity);
            state = State.FINISHED;
        }
    }

    public boolean isFinished() {
        if (state == State.FINISHED) {
            return true;
        }
        return false;
    }
}

enum State {
    INIT,
    RUNNING,
    FINISHED
}
