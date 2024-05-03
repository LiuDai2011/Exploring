package Exploring;

import Exploring.input.ExBinding;
import arc.Core;
import arc.KeyBinds.KeyBind;
import mindustry.input.Binding;

public class ExSettings {
    public static final float laserOpacity = 1f;

    public static void load() {
        KeyBind[] all = new KeyBind[ExBinding.values().length + Binding.values().length];
        System.arraycopy(Binding.values(), 0, all, 0, Binding.values().length);
        System.arraycopy(ExBinding.values(), 0, all, Binding.values().length, ExBinding.values().length);
        Core.keybinds.setDefaults(all);
    }
}
