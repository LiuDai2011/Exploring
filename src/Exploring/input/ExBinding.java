package Exploring.input;

import arc.KeyBinds;
import arc.input.InputDevice;
import arc.input.KeyCode;

public enum ExBinding implements KeyBinds.KeyBind {
    research(KeyCode.shiftRight)
    ;

    private final KeyBinds.KeybindValue defaultValue;
    private final String category;

    ExBinding(KeyBinds.KeybindValue defaultValue, String category) {
        this.defaultValue = defaultValue;
        this.category = category;
    }

    ExBinding(KeyBinds.KeybindValue defaultValue) {
        this(defaultValue, "exploring");
    }

    public KeyBinds.KeybindValue defaultValue(InputDevice.DeviceType type) {
        return this.defaultValue;
    }

    public String category() {
        return this.category;
    }
}
