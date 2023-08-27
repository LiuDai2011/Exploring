package Exploring.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.game.Objectives;

import static Exploring.content.ExBlocks.*;
import static Exploring.content.ExItems.*;
import static Exploring.content.ExLiquids.helium;
import static Exploring.content.ExLiquids.liquidHelium;
import static Exploring.content.ExSectorPresets.*;
import static mindustry.content.TechTree.*;

public class ExSerpuloTechTree {
    public static void load() {
        ExPlanets.exSerpulo.techTree = nodeRoot("ex-serpulo", ExBlocks.exCoreShard, () -> {
            node(exConveyor, () -> {
                node(exJunction, () -> {
                    node(exRouter, () -> {
//                        node(launchPad, Seq.with(new Objectives.SectorComplete(extractionOutpost)), () -> {
//                            //no longer necessary to beat the campaign
//                            //node(interplanetaryAccelerator, Seq.with(new SectorComplete(planetaryTerminal)), () -> {
//
//                            //});
//                        });

                        node(exDistributor);
                        node(exSorter, () -> {
                            node(exInvertedSorter);
                            node(exOverflowGate, () -> {
                                node(exUnderflowGate);
                            });
                        });
                        node(exContainer/*, Seq.with(new Objectives.SectorComplete(exBiomassFacility))*/, () -> {
//                            node(exUnloader);
                            node(exVault/*, Seq.with(new Objectives.SectorComplete(exStainedMountains))*/, () -> {

                            });
                        });

                        node(exItemBridge, () -> {
                            node(exTitaniumConveyor, Seq.with(new Objectives.SectorComplete(exCraters)), () -> {
                                node(exPhaseConveyor, () -> {
                                    node(exMassDriver, () -> {

                                    });
                                });

//                                node(exPayloadConveyor, () -> {
//                                    node(exPayloadRouter, () -> {
//
//                                    });
//                                });

                                node(exArmoredConveyor, () -> {
                                    node(exPlastaniumConveyor, () -> {

                                    });
                                });
                            });
                        });
                    });
                });
            });

//            node(coreFoundation, () -> {
//                node(coreNucleus, () -> {
//
//                });
//            });

            node(exMechanicalDrill, () -> {
//                node(mechanicalPump, () -> {
//                    node(conduit, () -> {
//                        node(liquidJunction, () -> {
//                            node(liquidRouter, () -> {
//                                node(liquidContainer, () -> {
//                                    node(liquidTank);
//                                });
//
//                                node(bridgeConduit);
//
//                                node(pulseConduit, Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
//                                    node(phaseConduit, () -> {
//
//                                    });
//
//                                    node(platedConduit, () -> {
//
//                                    });
//
//                                    node(rotaryPump, () -> {
//                                        node(impulsePump, () -> {
//
//                                        });
//                                    });
//                                });
//                            });
//                        });
//                    });
//                });
//
//                node(graphitePress, () -> {
//                    node(pneumaticDrill, Seq.with(new Objectives.SectorComplete(frozenForest)), () -> {
//                        node(cultivator, Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {
//
//                        });
//
//                        node(laserDrill, () -> {
//                            node(blastDrill, Seq.with(new Objectives.SectorComplete(nuclearComplex)), () -> {
//
//                            });
//
//                            node(waterExtractor, Seq.with(new Objectives.SectorComplete(saltFlats)), () -> {
//                                node(oilExtractor, () -> {
//
//                                });
//                            });
//                        });
//                    });
//
//                    node(pyratiteMixer, () -> {
//                        node(blastMixer, () -> {
//
//                        });
//                    });
//
//                    node(siliconSmelter, () -> {
//
//                        node(sporePress, () -> {
//                            node(coalCentrifuge, () -> {
//                                node(multiPress, () -> {
//                                    node(siliconCrucible, () -> {
//
//                                    });
//                                });
//                            });
//
//                            node(plastaniumCompressor, Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
//                                node(phaseWeaver, Seq.with(new Objectives.SectorComplete(tarFields)), () -> {
//
//                                });
//                            });
//                        });
                node(siliconPurifier, () -> {
                    node(tubeMakingMachine, () -> {
                        node(siliconRecombiner, () -> {

                        });
                    });

                    node(titaniumFurnace, () -> {

                    });

                    node(airCollector, () -> {
                        node(liquidHeliumCooler, () -> {

                        });
                    });

                    node(excitedThoriumReactor, () -> {
                        node(super_excitedThoriumReactor, () -> {

                        });
                    });
                });
//
//                        node(kiln, Seq.with(new Objectives.SectorComplete(exCraters)), () -> {
//                            node(pulverizer, () -> {
//                                node(incinerator, () -> {
//                                    node(melter, () -> {
//                                        node(surgeSmelter, () -> {
//
//                                        });
//
//                                        node(separator, () -> {
//                                            node(disassembler, () -> {
//
//                                            });
//                                        });
//
//                                        node(cryofluidMixer, () -> {
//
//                                        });
//                                    });
//                                });
//                            });
//                        });
//
//                        //logic disabled until further notice
//                        node(microProcessor, () -> {
//                            node(switchBlock, () -> {
//                                node(message, () -> {
//                                    node(logicDisplay, () -> {
//                                        node(largeLogicDisplay, () -> {
//
//                                        });
//                                    });
//
//                                    node(memoryCell, () -> {
//                                        node(memoryBank, () -> {
//
//                                        });
//                                    });
//                                });
//
//                                node(logicProcessor, () -> {
//                                    node(hyperProcessor, () -> {
//
//                                    });
//                                });
//                            });
//                        });
//
//                        node(illuminator, () -> {
//
//                        });
//                    });
//                });
//
//
                node(exCombustionGenerator, Seq.with(new Objectives.Research(Items.coal)), () -> {
                    node(exPowerNode, () -> {
                        node(exPowerNodeLarge, () -> {
                            node(exDiode, () -> {
                                node(exSurgeTower, () -> {

                                });
                            });
                        });

                        node(exBattery, () -> {
                            node(exBatteryLarge, () -> {

                            });
                        });

//                        node(mender, () -> {
//                            node(mendProjector, () -> {
//                                node(forceProjector, Seq.with(new Objectives.SectorComplete(impact0078)), () -> {
//                                    node(overdriveProjector, Seq.with(new Objectives.SectorComplete(impact0078)), () -> {
//                                        node(overdriveDome, Seq.with(new Objectives.SectorComplete(impact0078)), () -> {
//
//                                        });
//                                    });
//                                });
//
//                                node(repairPoint, () -> {
//                                    node(repairTurret, () -> {
//
//                                    });
//                                });
//                            });
//                        });

                        node(exSteamGenerator, Seq.with(new Objectives.SectorComplete(exCraters)), () -> {
                            node(exThermalGenerator, () -> {
                                node(exDifferentialGenerator, () -> {
                                    node(exThoriumReactor, Seq.with(new Objectives.Research(Liquids.cryofluid)), () -> {
                                        node(exImpactReactor, () -> {

                                        });

                                        node(exRtgGenerator, () -> {

                                        });
                                    });
                                });
                            });
                        });

                        node(exSolarPanel, () -> {
                            node(exLargeSolarPanel, () -> {

                            });
                        });
                    });
                });
            });

            node(exDuo, () -> {
                node(exCopperWall, () -> {
                    node(exCopperWallLarge, () -> {
                        node(exTitaniumWall, () -> {
                            node(exTitaniumWallLarge);

                            node(exDoor, () -> {
                                node(exDoorLarge);
                            });
                            node(exPlastaniumWall, () -> {
                                node(exPlastaniumWallLarge, () -> {

                                });
                            });
                            node(exThoriumWall, () -> {
                                node(exThoriumWallLarge);
                                node(exSurgeWall, () -> {
                                    node(exSurgeWallLarge);
                                    node(exPhaseWall, () -> {
                                        node(exPhaseWallLarge);
                                    });
                                });
                            });
                        });

                        node(leadWall, () -> {
                            node(leadWallLarge, () -> {

                            });
                        });
                    });

                    node(exScatter, () -> {
                        node(exHail, Seq.with(new Objectives.SectorComplete(exCraters)), () -> {
                            node(exSalvo, () -> {
                                node(exSwarmer, () -> {
                                    node(exCyclone, () -> {
//                                    node(spectre, Seq.with(new Objectives.SectorComplete(nuclearComplex)), () -> {
//
//                                    });
                                        node(exSpectre, () -> {

                                        });
                                    });
                                });

                                node(exRipple, () -> {
                                    node(exFuse, () -> {

                                    });
                                });
                            });
                        });
                    });

                    node(exScorch, () -> {
                        node(exArc, () -> {
                            node(exWave, () -> {
                                node(exParallax, () -> {
                                    node(exSegment, () -> {

                                    });
                                });

                                node(exTsunami, () -> {

                                });
                            });

                            node(exLancer, () -> {
                                node(exMeltdown, () -> {
                                    node(exForeshadow, () -> {

                                    });
                                });
//
//                            node(exShockMine, () -> {
//
//                            });
                            });
                        });
                    });

                    node(ExBlocks.test_pt, () -> {

                    });
                });
            });

//            node(groundFactory, () -> {
//
//                node(dagger, () -> {
//                    node(mace, () -> {
//                        node(fortress, () -> {
//                            node(scepter, () -> {
//                                node(reign, () -> {
//
//                                });
//                            });
//                        });
//                    });
//
//                    node(nova, () -> {
//                        node(pulsar, () -> {
//                            node(quasar, () -> {
//                                node(vela, () -> {
//                                    node(corvus, () -> {
//
//                                    });
//                                });
//                            });
//                        });
//                    });
//
//                    node(crawler, () -> {
//                        node(atrax, () -> {
//                            node(spiroct, () -> {
//                                node(arkyid, () -> {
//                                    node(toxopid, () -> {
//
//                                    });
//                                });
//                            });
//                        });
//                    });
//                });
//
//                node(airFactory, () -> {
//                    node(flare, () -> {
//                        node(horizon, () -> {
//                            node(zenith, () -> {
//                                node(antumbra, () -> {
//                                    node(eclipse, () -> {
//
//                                    });
//                                });
//                            });
//                        });
//
//                        node(mono, () -> {
//                            node(poly, () -> {
//                                node(mega, () -> {
//                                    node(quad, () -> {
//                                        node(oct, () -> {
//
//                                        });
//                                    });
//                                });
//                            });
//                        });
//                    });
//
//                    node(navalFactory, Seq.with(new Objectives.SectorComplete(ruinousShores)), () -> {
//                        node(risso, () -> {
//                            node(minke, () -> {
//                                node(bryde, () -> {
//                                    node(sei, () -> {
//                                        node(omura, () -> {
//
//                                        });
//                                    });
//                                });
//                            });
//
//                            node(retusa, Seq.with(new Objectives.SectorComplete(windsweptIslands)), () -> {
//                                node(oxynoe, Seq.with(new Objectives.SectorComplete(coastline)), () -> {
//                                    node(cyerce, () -> {
//                                        node(aegires, () -> {
//                                            node(navanax, Seq.with(new Objectives.SectorComplete(navalFortress)), () -> {
//
//                                            });
//                                        });
//                                    });
//                                });
//                            });
//                        });
//                    });
//                });
//
//                node(additiveReconstructor, Seq.with(new Objectives.SectorComplete(biomassFacility)), () -> {
//                    node(multiplicativeReconstructor, () -> {
//                        node(exponentialReconstructor, Seq.with(new Objectives.SectorComplete(overgrowth)), () -> {
//                            node(tetrativeReconstructor, () -> {
//
//                            });
//                        });
//                    });
//                });
//            });

            node(exGroundZero, () -> {
                node(exFrozenForest, Seq.with(
                        new Objectives.SectorComplete(exGroundZero),
                        new Objectives.Research(exJunction),
                        new Objectives.Research(exRouter)
                ), () -> {
                    node(exCraters, Seq.with(
                            new Objectives.SectorComplete(exFrozenForest)
//                            ,
//                            new Objectives.Research(mender),
//                            new Objectives.Research(combustionGenerator)
                    ), () -> {
//                        node(ruinousShores, Seq.with(
//                                new Objectives.SectorComplete(craters),
//                                new Objectives.Research(graphitePress),
//                                new Objectives.Research(kiln),
//                                new Objectives.Research(mechanicalPump)
//                        ), () -> {
//                            node(windsweptIslands, Seq.with(
//                                    new Objectives.SectorComplete(ruinousShores),
//                                    new Objectives.Research(pneumaticDrill),
//                                    new Objectives.Research(hail),
//                                    new Objectives.Research(siliconSmelter),
//                                    new Objectives.Research(steamGenerator)
//                            ), () -> {
//                                node(tarFields, Seq.with(
//                                        new Objectives.SectorComplete(windsweptIslands),
//                                        new Objectives.Research(coalCentrifuge),
//                                        new Objectives.Research(conduit),
//                                        new Objectives.Research(wave)
//                                ), () -> {
//                                    node(impact0078, Seq.with(
//                                            new Objectives.SectorComplete(tarFields),
//                                            new Objectives.Research(Items.thorium),
//                                            new Objectives.Research(lancer),
//                                            new Objectives.Research(salvo),
//                                            new Objectives.Research(coreFoundation)
//                                    ), () -> {
//                                        node(desolateRift, Seq.with(
//                                                new Objectives.SectorComplete(impact0078),
//                                                new Objectives.Research(thermalGenerator),
//                                                new Objectives.Research(thoriumReactor),
//                                                new Objectives.Research(coreNucleus)
//                                        ), () -> {
//                                            node(planetaryTerminal, Seq.with(
//                                                    new Objectives.SectorComplete(desolateRift),
//                                                    new Objectives.SectorComplete(nuclearComplex),
//                                                    new Objectives.SectorComplete(overgrowth),
//                                                    new Objectives.SectorComplete(extractionOutpost),
//                                                    new Objectives.SectorComplete(saltFlats),
//                                                    new Objectives.Research(risso),
//                                                    new Objectives.Research(minke),
//                                                    new Objectives.Research(bryde),
//                                                    new Objectives.Research(spectre),
//                                                    new Objectives.Research(launchPad),
//                                                    new Objectives.Research(massDriver),
//                                                    new Objectives.Research(impactReactor),
//                                                    new Objectives.Research(additiveReconstructor),
//                                                    new Objectives.Research(exponentialReconstructor)
//                                            ), () -> {
//
//                                            });
//                                        });
//                                    });
//                                });
//
//                                node(extractionOutpost, Seq.with(
//                                        new Objectives.SectorComplete(stainedMountains),
//                                        new Objectives.SectorComplete(windsweptIslands),
//                                        new Objectives.Research(groundFactory),
//                                        new Objectives.Research(nova),
//                                        new Objectives.Research(airFactory),
//                                        new Objectives.Research(mono)
//                                ), () -> {
//
//                                });
//
//                                node(saltFlats, Seq.with(
//                                        new Objectives.SectorComplete(windsweptIslands),
//                                        new Objectives.Research(groundFactory),
//                                        new Objectives.Research(additiveReconstructor),
//                                        new Objectives.Research(airFactory),
//                                        new Objectives.Research(door)
//                                ), () -> {
//                                    node(coastline, Seq.with(
//                                            new Objectives.SectorComplete(windsweptIslands),
//                                            new Objectives.SectorComplete(saltFlats),
//                                            new Objectives.Research(navalFactory),
//                                            new Objectives.Research(payloadConveyor)
//                                    ), () -> {
//                                        node(navalFortress, Seq.with(
//                                                new Objectives.SectorComplete(coastline),
//                                                new Objectives.SectorComplete(extractionOutpost),
//                                                new Objectives.Research(oxynoe),
//                                                new Objectives.Research(minke),
//                                                new Objectives.Research(cyclone),
//                                                new Objectives.Research(ripple)
//                                        ), () -> {
//
//                                        });
//                                    });
//                                });
//                            });
//                        });
//
//                        node(overgrowth, Seq.with(
//                                new Objectives.SectorComplete(craters),
//                                new Objectives.SectorComplete(fungalPass),
//                                new Objectives.Research(cultivator),
//                                new Objectives.Research(sporePress),
//                                new Objectives.Research(additiveReconstructor),
//                                new Objectives.Research(UnitTypes.mace),
//                                new Objectives.Research(UnitTypes.flare)
//                        ), () -> {
//
//                        });
//                    });
//
//                    node(biomassFacility, Seq.with(
//                            new Objectives.SectorComplete(frozenForest),
//                            new Objectives.Research(powerNode),
//                            new Objectives.Research(steamGenerator),
//                            new Objectives.Research(scatter),
//                            new Objectives.Research(graphitePress)
//                    ), () -> {
//                        node(stainedMountains, Seq.with(
//                                new Objectives.SectorComplete(biomassFacility),
//                                new Objectives.Research(pneumaticDrill),
//                                new Objectives.Research(siliconSmelter)
//                        ), () -> {
//                            node(fungalPass, Seq.with(
//                                    new Objectives.SectorComplete(stainedMountains),
//                                    new Objectives.Research(groundFactory),
//                                    new Objectives.Research(door)
//                            ), () -> {
//                                node(nuclearComplex, Seq.with(
//                                        new Objectives.SectorComplete(fungalPass),
//                                        new Objectives.Research(thermalGenerator),
//                                        new Objectives.Research(laserDrill),
//                                        new Objectives.Research(Items.plastanium),
//                                        new Objectives.Research(swarmer)
//                                ), () -> {
//
//                                });
//                            });
//                        });
                    });

                    node(abandonedMine, () -> {

                    });
                });
            });

            nodeProduce(iron, () -> {
                nodeProduce(titaniumAlloy, () -> {

                });

                nodeProduce(siliconBlock, () -> {
                    nodeProduce(vacuumTube, () -> {

                    });

                    nodeProduce(pureSilicon, () -> {
                        nodeProduce(chip, () -> {
                            nodeProduce(quantumChip, () -> {

                            });
                        });
                    });
                });

                nodeProduce(helium, () -> {
                    nodeProduce(liquidHelium, () -> {

                    });
                });
            });
        });
    }
}
