package Exploring.content;

import Exploring.world.util.Change;
import arc.graphics.Color;
import mindustry.game.Team;

public class ExTeam {
    public static Team abyss, Void;

    static {
        abyss = Team.get(66);
        Void = Team.get(255);
    }

    public static void load() {
        Change.team(abyss.id, "abyss", Color.valueOf("7130ad"));
        Change.team(Void.id, "void", Color.clear);
    }
}
