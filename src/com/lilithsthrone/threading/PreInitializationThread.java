package com.lilithsthrone.threading;

import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.types.*;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;
import javafx.application.Platform;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @since 0.4.10.7
 * @version 0.4.10.7
 * @author KeldonSlayer (DrZed)
 */
public class PreInitializationThread  extends Thread {
    /* The next step to improving this is investigating why certain ones take so long, likely due to XML parsing not certain */

    /* Debug in name only, it's for timing the threads to find the slowest source, PRINT_COUNT allows ensuring the thresholds are met safely */
    private static final boolean DEBUG_PRE_INIT = false, PRINT_COUNT = false, MULTITHREADED_PRELOADING = true;
    public static PreInitializationThread HelperThread1, HelperThread2, HelperThread3, HelperThread4; /* 4 threads was the middle ground of Threads Needed, and efficiency. A 5th thread is a little faster, if you time and cluster the fastest ones on another thread */
    public static final AtomicBoolean INIT_BODY_COVERING_TYPE = new AtomicBoolean(false),
            INIT_RACES = new AtomicBoolean(false), INIT_BREASTS = new AtomicBoolean(false),
            INIT_ANTENNA = new AtomicBoolean(false), INIT_ANUS = new AtomicBoolean(false),
            INIT_ARM = new AtomicBoolean(false), INIT_ASS = new AtomicBoolean(false),
            INIT_EAR = new AtomicBoolean(false), INIT_EYE = new AtomicBoolean(false),
            INIT_FACE = new AtomicBoolean(false), INIT_FLUID = new AtomicBoolean(false),
            INIT_FOOT = new AtomicBoolean(false), INIT_HAIR = new AtomicBoolean(false),
            INIT_HORN = new AtomicBoolean(false), INIT_LEG = new AtomicBoolean(false),
            INIT_MOUTH = new AtomicBoolean(false), INIT_NIPPLE = new AtomicBoolean(false),
            INIT_PENIS = new AtomicBoolean(false), INIT_TAIL = new AtomicBoolean(false),
            INIT_TENTACLE = new AtomicBoolean(false), INIT_TESTICLE = new AtomicBoolean(false),
            INIT_TONGUE = new AtomicBoolean(false), INIT_TORSO = new AtomicBoolean(false),
            INIT_VAGINA = new AtomicBoolean(false), INIT_WING = new AtomicBoolean(false),
            INIT_WORLD = new AtomicBoolean(false), INIT_PLACE = new AtomicBoolean(false);
    private static boolean hasRedrawnMainScene = false;// safety check, funny because I don't lock the UI for the official branch
    private static int initializedItems = 0; // we use this as a primary check to ensure it's ready, without polling all the AtomicBooleans every cycle

    public static void preloadData() {// Makes Loading Saves up to 400% faster (or in other terms, take 76~% less time)
        if (MULTITHREADED_PRELOADING) {
            HelperThread1 = new PreInitializationThread("PRE-INIT-1");
            HelperThread1.start();
            HelperThread2 = new PreInitializationThread("PRE-INIT-2");
            HelperThread2.start();
            HelperThread3 = new PreInitializationThread("PRE-INIT-3");
            HelperThread3.start();
            HelperThread4 = new PreInitializationThread("PRE-INIT-4");
            HelperThread4.start();
        }
    }

    @Override
    public void run() {
        if (this == HelperThread1)
            initCore();
        if (this == HelperThread2)
            initHead();
        if (this == HelperThread3)
            initBody();
        if (this == HelperThread4)
            initFinal();

        if (this.finished()) {
            if (UtilText.engine == null) {// This actually saves 1.1 seconds from NPC initialization
                UtilText.initScriptEngine();
            }
            if (!hasRedrawnMainScene) { // Since I block hitting New Game, Resume, Load whilst stuff isn't loaded, we redraw the scene to unblock them
                hasRedrawnMainScene = true;
                @SuppressWarnings("unused") long waited = 0; // persists so that IntelliJ doesn't make me remove the wait loop
                while (Main.instance == null) {
                    waited++;// this serves to ensure it doesn't reset the display of the main window before the window exists.
                }
                Platform.runLater(() -> Main.instance.resetContent()); // ensures it happens on the main thread
//                System.out.println("Waited " + waited + " cycles for instance to initialize."); // Sanity check
            }
            Main.saveProperties();
        }
    }

    private void initCore() {// 410-520ms
        long timeStarted = System.currentTimeMillis(), timeSegment = System.currentTimeMillis();
        do
        {// Do->while is slightly more efficient in this situation than while {}, since it does then compares, instead of compare then do, meaning the work starts first
            int cnt = BodyCoveringType.allBodyCoveringTypes.size();
            if (cnt >= 365) {
                INIT_BODY_COVERING_TYPE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_BODY_COVERING_TYPE initialized : " + cnt);
            }
        } while (!INIT_BODY_COVERING_TYPE.get());// 0-1ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Body Covering in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = Race.getAllRaces().size();
            if (cnt >= 40) {
                INIT_RACES.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_RACES initialized : " + cnt);
            }
        } while (!INIT_RACES.get());// 0-1ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Races in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = FluidType.getAllFluidTypes().size();
            if (cnt >= 112) {
                INIT_FLUID.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_FLUID initialized : " + cnt);
            }
        } while (!INIT_FLUID.get());// 400-510ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Fluids in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = WorldType.getAllWorldTypes().size();
            if (cnt >= 65) {
                INIT_WORLD.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_WORLD initialized : " + cnt);
            }
        } while (!INIT_WORLD.get());// 0-1ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Worlds in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = PlaceType.getAllPlaceTypes().size();
            if (cnt >= 628) {
                INIT_PLACE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_PLACE initialized : " + cnt);
            }
        } while (!INIT_PLACE.get());// 0-1ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Place in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            System.out.println("Finished Core in: " + (System.currentTimeMillis() - timeStarted) + " ms");
        }
    }

    private void initHead() {// 1600-2400ms
        long timeStarted = System.currentTimeMillis(), timeSegment = System.currentTimeMillis();
        do {
            if (!INIT_RACES.get()) continue;
            int cnt = EarType.getAllEarTypes().size();
            if (cnt >= 46) {
                INIT_EAR.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_EAR initialized : " + cnt);
            }
        } while (!INIT_EAR.get());// 390-430ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Ears in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = EyeType.getAllEyeTypes().size();
            if (cnt >= 39) {
                INIT_EYE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_EYE initialized : " + cnt);
            }
        } while (!INIT_EYE.get());// 380-420ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Eyes in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = FaceType.getAllFaceTypes().size();
            if (cnt >= 41) {
                INIT_FACE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_FACE initialized : " + cnt);
            }
        } while (!INIT_FACE.get());// 980-1230ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Faces in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = HairType.getAllHairTypes().size();
            if (cnt >= 40) {
                INIT_HAIR.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_HAIR initialized : " + cnt);
            }
        } while (!INIT_HAIR.get());// 360-390ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Hair in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = HornType.getAllHornTypes().size();
            if (cnt >= 13) {
                INIT_HORN.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_HORN initialized : " + cnt);
            }
        } while (!INIT_HORN.get());// 290-310ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Horns in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = TongueType.getAllTongueTypes().size();
            if (cnt >= 38) {
                INIT_TONGUE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_TONGUE initialized : " + cnt);
            }
        } while (!INIT_TONGUE.get());// 0-1ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Tongues in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = MouthType.getAllMouthTypes().size();
            if (cnt >= 38) {
                INIT_MOUTH.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_MOUTH initialized : " + cnt);
            }
        } while (!INIT_MOUTH.get());// 0-1ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Mouths in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = AntennaType.getAllAntennaTypes().size();
            if (cnt >= 1) {
                INIT_ANTENNA.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_ANTENNA initialized : " + cnt);
            }
        } while (!INIT_ANTENNA.get());// 270-310ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Antennae in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            System.out.println("Finished Head in: " + (System.currentTimeMillis() - timeStarted) + " ms");
        }
    }

    private void initBody() {// 2300-2500ms
        long timeStarted = System.currentTimeMillis(), timeSegment = System.currentTimeMillis();
        do {
            if (!INIT_RACES.get()) continue;
            int cnt = TorsoType.getAllTorsoTypes().size();
            if (cnt >= 40) {
                INIT_TORSO.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_TORSO initialized : " + cnt);
            }
        } while (!INIT_TORSO.get());// 390-430ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Torso in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = TailType.getAllTailTypes().size();
            if (cnt >= 50) {
                INIT_TAIL.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_TAIL initialized : " + cnt);
            }
        } while (!INIT_TAIL.get());// 390-440ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Tail in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = TentacleType.getAllTentacleTypes().size();
            if (cnt >= 4) {
                INIT_TENTACLE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_TENTACLE initialized : " + cnt);
            }
        } while (!INIT_TENTACLE.get());// 360-400ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Tentacle in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = ArmType.getAllArmTypes().size();
            if (cnt >= 41) {
                INIT_ARM.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_ARM initialized : " + cnt);
            }
        } while (!INIT_ARM.get());// 380-420ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Arm in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }


        do {
            int cnt = LegType.getAllLegTypes().size();
            if (cnt >= 46) {
                INIT_LEG.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_LEG initialized : " + cnt);
            }
        } while (!INIT_LEG.get());// 390-430ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Leg in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = FootType.getAllFootTypes().size();
            if (cnt >= 9) {
                INIT_FOOT.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_FOOT initialized : " + cnt);
            }
        } while (!INIT_FOOT.get());// 0-1ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Foot in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = WingType.getAllWingTypes().size();
            if (cnt >= 11) {
                INIT_WING.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_WING initialized : " + cnt);
            }
        } while (!INIT_WING.get());// 350-380ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Wing in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            System.out.println("Finished Body in: " + (System.currentTimeMillis() - timeStarted) + " ms");
        }
    }

    private void initFinal() {// 2900-3200ms
        long timeStarted = System.currentTimeMillis(), timeSegment = System.currentTimeMillis();
        do {
            int cnt = VaginaType.getAllVaginaTypes().size();
            if (cnt >= 39) {
                INIT_VAGINA.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_VAGINA initialized : " + cnt);
            }
        } while (!INIT_VAGINA.get());// 800-920ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Vagina in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = NippleType.getAllNippleTypes().size();
            if (cnt >= 37) {
                INIT_NIPPLE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_NIPPLE initialized : " + cnt);
            }
        } while (!INIT_NIPPLE.get());// 370-395ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Nipple in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = BreastType.getAllBreastTypes().size();
            if (cnt >= 38) {
                INIT_BREASTS.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_BREASTS initialized : " + cnt);
            }
        } while (!INIT_BREASTS.get());// 390-415ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Breasts in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = AnusType.getAllAnusTypes().size();
            if (cnt >= 37) {
                INIT_ANUS.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_ANUS initialized : " + cnt);
            }
        } while (!INIT_ANUS.get());// 390-410ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Anus in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = AssType.getAllAssTypes().size();
            if (cnt >= 36) {
                INIT_ASS.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_ASS initialized : " + cnt);
            }
        } while (!INIT_ASS.get());// 360-390ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Ass in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = TesticleType.getAllTesticleTypes().size();
            if (cnt >= 39) {
                INIT_TESTICLE.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_TESTICLE initialized : " + cnt);
            }
        } while (!INIT_TESTICLE.get());// 290-320ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Testicle in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        do {
            int cnt = PenisType.getAllPenisTypes().size();
            if (cnt >= 39) {
                INIT_PENIS.set(true);
                if (DEBUG_PRE_INIT && PRINT_COUNT) System.out.println("INIT_PENIS initialized : " + cnt);
            }
        } while (!INIT_PENIS.get());// 280-300ms
        initializedItems++;
        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Penis in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            timeSegment = System.currentTimeMillis();
        }

        Perk.generateSubspeciesPerks();// 0-2ms

        if (DEBUG_PRE_INIT) {
            System.out.println("Finished Perks in: " + (System.currentTimeMillis() - timeSegment) + " ms");
            System.out.println("Finished Final in: " + (System.currentTimeMillis() - timeStarted) + " ms");
        }
    }

    public PreInitializationThread(String name) {
        super(name);
    }

    public boolean finished() {
        if (initializedItems < 27) return false;
        return INIT_BREASTS.get() && INIT_PLACE.get() && INIT_WORLD.get() && INIT_ANTENNA.get() && INIT_ANUS.get() && INIT_ARM.get() && INIT_ASS.get() && INIT_EAR.get() && INIT_EYE.get() && INIT_FACE.get() && INIT_FLUID.get() && INIT_FOOT.get() && INIT_HAIR.get() && INIT_HORN.get() && INIT_LEG.get() && INIT_MOUTH.get() && INIT_NIPPLE.get() && INIT_PENIS.get() && INIT_TAIL.get() && INIT_TENTACLE.get() && INIT_TESTICLE.get() && INIT_TONGUE.get() && INIT_TORSO.get() && INIT_VAGINA.get() && INIT_WING.get();
    }
}