package Exploring.world.util;

import Exploring.content.ExTeam;
import Exploring.graphics.ExColor;
import Exploring.world.meta.TransTeamc;
import arc.graphics.Color;
import arc.graphics.g2d.PixmapRegion;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;

import java.lang.reflect.Field;

public class Change {
    public static void team(Building build, Team team) {
        build.team(team);
    }

    public static void team(Unit unit, Team team) {
        boolean can = true;
        if (unit instanceof TransTeamc u) {
            can = u.can() && can;
        }
        if (can && unit.team != team) {
            unit.remove();
            unit.team(team);
            if (unit.isPlayer()) unit.getPlayer().team(team);
            unit.add();
        }
    }

    public static void abyss(Unit unit) {
        team(unit, ExTeam.abyss);
    }

    public static Team team(int id, String name, Color color) {
        Team.get(id).name = name;
        Field[] fields = Team.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("color")) {
                try {
                    field.set(Team.get(id), color);
                    break;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return Team.get(id);
    }

    @FunctionalInterface
    public interface ColorBool {
        boolean get(int c);
    }

    @FunctionalInterface
    public interface Int2Color {
        Color get(int x, int y);
    }

    public static PixmapRegion color(PixmapRegion pixmap, ColorBool cond, Int2Color to) {
        pixmap.pixmap.each((x, y) -> {
            if (x >= pixmap.x && x < pixmap.x + pixmap.width && y >= pixmap.y && y < pixmap.y + pixmap.height &&
                    cond.get(pixmap.pixmap.get(x, y))) {
                pixmap.pixmap.set(x, y, to.get(x, y));
            }
        });
        return pixmap;
    }

    public static PixmapRegion color(PixmapRegion pixmap, Color from, Color to) {
        return color(pixmap, c -> c == from.rgba(), (x, y) -> to);
    }

    public static PixmapRegion color(PixmapRegion pixmap, Color to) {
        return color(pixmap, ExColor.changeFrom, to);
    }
}
