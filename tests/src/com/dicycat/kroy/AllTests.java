package com.dicycat.kroy;

import com.dicycat.kroy.bullets.*;
import com.dicycat.kroy.debug.DebugCircleTest;
import com.dicycat.kroy.debug.DebugLineTest;
import com.dicycat.kroy.debug.DebugRectTest;
import com.dicycat.kroy.debug.DebugRendererTest;
import com.dicycat.kroy.entities.*;
import com.dicycat.kroy.gamemap.TileTypeTest;
import com.dicycat.kroy.misc.*;
import com.dicycat.kroy.scenes.*;
import com.dicycat.kroy.screens.GameOverScreenTest;
import com.dicycat.kroy.screens.GameScreenTest;
import com.dicycat.kroy.screens.MenuScreenTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        BulletDispenserTest.class,
        BulletTest.class,
        PatternTest.class,
        DebugCircleTest.class,
        DebugLineTest.class,
        DebugRectTest.class,
        DebugRendererTest.class,
        FireStationTest.class,
        FireTruckTest.class,
        FortressTest.class,
        GooseTest.class,
        PipeTest.class,
        PowerTypeTest.class,
        PowerUpsTest.class,
        UFOTest.class,
        TileTypeTest.class,
        ButtonListenersTest.class,
        OverwriteDialogTest.class,
        SaveManagerTest.class,
        StatBarTest.class,
        UpdaterTest.class,
        WaterStreamTest.class,
        DifficultySceneTest.class,
        FireTruckSelectionSceneTest.class,
        HUDTest.class,
        LoadGameSceneTest.class,
        OptionsWindowTest.class,
        PauseWindowTest.class,
        SaveGameSceneTest.class,
        GameOverScreenTest.class,
        GameScreenTest.class,
        MenuScreenTest.class,
        StatsOverlayTest.class
})

public class AllTests {
}