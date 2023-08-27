package Exploring.content;

import mindustry.type.SectorPreset;

import static Exploring.content.ExPlanets.exSerpulo;

public class ExSectorPresets{
    public static SectorPreset
    exGroundZero, exFrozenForest, exCraters,

    abandonedMine;

    public static void load() {
        exGroundZero = new SectorPreset("Ex-Ground_Zero", exSerpulo, 15){{
            alwaysUnlocked = true;
            addStartingItems = true;
            captureWave = 10;
            difficulty = 1;
            overrideLaunchDefaults = true;
            noLighting = true;
            startWaveTimeMultiplier = 3f;
        }};

        exFrozenForest = new SectorPreset("Ex-Frozen_Forest", exSerpulo, 86){{
            captureWave = 15;
            difficulty = 2;
        }};

        exCraters = new SectorPreset("Ex-Craters", exSerpulo, 18){{
            captureWave = 20;
            difficulty = 2;
        }};

        abandonedMine = new SectorPreset("Abandoned_Mine", exSerpulo, 23){{
            captureWave = 20;
            difficulty = 2;
        }};
    }
}
