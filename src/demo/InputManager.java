package demo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager {

    private boolean[] keyStatusArray;
    private KeyListener keyListener;
    private KeybindSet keybinds;
    private boolean isNonChatInputDisabled;

    public InputManager() {
        this.keyStatusArray = new boolean[256];
        this.keyListener = new KeyListener() {

            @Override
            public void keyReleased(KeyEvent e) {

                keyStatusArray[e.getKeyCode()] = false;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // ?
            }

            @Override
            public void keyPressed(KeyEvent e) {
                keyStatusArray[e.getKeyCode()] = true;
            }

        };
        this.keybinds = GlobalConfig.get().getKeyBinds();
        this.isNonChatInputDisabled = false;
    }

    public boolean getPressedByName(String name) {
        return this.keyStatusArray[keybinds.get(name)];
    }

    public boolean getPressedByCode(int code) {
        return this.keyStatusArray[code];
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public boolean isNonChatInputDisabled() {
        return this.isNonChatInputDisabled;
    }

    public void setNonChatInputDisabled(boolean state) {
        this.isNonChatInputDisabled = state;
    }

}
