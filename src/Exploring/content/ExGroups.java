package Exploring.content;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import mindustry.game.Team;
import mindustry.gen.Building;

public class ExGroups {
    public static final ObjectMap<Team, Seq<Building>> coreLinkers = new ObjectMap<>();

    public static void checkCoreLinkerKey(Team team) {
        if (coreLinkers.containsKey(team)) return;
        coreLinkers.put(team, new Seq<>());
    }
}
