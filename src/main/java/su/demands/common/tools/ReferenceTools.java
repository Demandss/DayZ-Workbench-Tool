package su.demands.common.tools;

import su.demands.Main;
import su.demands.elements.Modification;

import java.util.ArrayList;
import java.util.Collections;

public class ReferenceTools {

    public static String DATA_PATH = Main.DATA_PATH + "/tools";
    public static String RESOURCES = "/assets/tools";
    public static String FIX_SCRIPTS_FILENAME = "/fixScripts.bat";
    public static String START_DEBUG_ALL_FILENAME = "/StartDebugAll.bat";
    public static String START_DEBUG_CLIENT_FILENAME = "/StartDebugClient.bat";
    public static String START_DEBUG_SERVER_FILENAME = "/StartDebugServer.bat";
    public static String START_WORKBENCH_FILENAME = "/StartWorkbench.bat";
    public static String USER_SETTINGS_FILENAME = "/UserSettings.bat";
    public static String DATA_PATH_FIX_SCRIPTS = DATA_PATH + FIX_SCRIPTS_FILENAME;
    public static String DATA_PATH_START_DEBUG_ALL = DATA_PATH + START_DEBUG_ALL_FILENAME;
    public static String DATA_PATH_START_DEBUG_CLIENT = DATA_PATH + START_DEBUG_CLIENT_FILENAME;
    public static String DATA_PATH_START_DEBUG_SERVER = DATA_PATH + START_DEBUG_SERVER_FILENAME;
    public static String DATA_PATH_START_WORKBENCH = DATA_PATH + START_WORKBENCH_FILENAME;
    public static String DATA_PATH_USER_SETTINGS = DATA_PATH + USER_SETTINGS_FILENAME;
    public static String RESOURCES_FIX_SCRIPTS = RESOURCES + FIX_SCRIPTS_FILENAME;
    public static String RESOURCES_START_DEBUG_ALL = RESOURCES + START_DEBUG_ALL_FILENAME;
    public static String RESOURCES_START_DEBUG_CLIENT = RESOURCES + START_DEBUG_CLIENT_FILENAME;
    public static String RESOURCES_START_DEBUG_SERVER = RESOURCES + START_DEBUG_SERVER_FILENAME;
    public static String RESOURCES_START_WORKBENCH = RESOURCES + START_WORKBENCH_FILENAME;
    public static String RESOURCES_USER_SETTINGS = RESOURCES + USER_SETTINGS_FILENAME;

    public static ArrayList<Modification> MODIFICATIONS = new ArrayList<>(Collections.emptyList());
}
