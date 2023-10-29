package su.demands.common;

import com.google.gson.*;
import lombok.SneakyThrows;
import lombok.val;
import su.demands.Main;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.label.DarkLabel;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsManager {

    private static JsonObject jsonObject;

    public static String DATA_PATH = Main.DATA_PATH + "/setting";

    private static final String DEFAULT_FILE_SETTINGS_NAME = "/default_settings.json";
    private static final String USER_FILE_SETTINGS_NAME = "/user_settings.json";

    private static final String RESOURCES_PATH = "/assets/setting";

    @SneakyThrows
    public SettingsManager() {
        Path settingsPath = Path.of(DATA_PATH);

        File setting = new File(settingsPath.toUri());
        if (!setting.exists())
            setting.mkdirs();

        settingsPath = Path.of(settingsPath + USER_FILE_SETTINGS_NAME);

        if (!Files.exists(settingsPath))
            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_PATH + DEFAULT_FILE_SETTINGS_NAME)),settingsPath);

        reload();
    }

    @SneakyThrows
    public static void resetUserConfig() {
        Path settingsPath = Path.of(DATA_PATH + USER_FILE_SETTINGS_NAME);
        if (Files.exists(settingsPath))
        {
            Files.delete(settingsPath);

            Files.copy(Objects.requireNonNull(SettingsManager.class.getResourceAsStream(RESOURCES_PATH + DEFAULT_FILE_SETTINGS_NAME)),settingsPath);
        }
    }

    @SneakyThrows
    public void reload() {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(DATA_PATH + USER_FILE_SETTINGS_NAME));
        jsonObject = JsonParser.parseReader(bufferedReader).getAsJsonObject();

        checkSettingsVersionAndSync();
    }

    private void checkSettingsVersionAndSync() {
        String currentFileVersion = jsonObject.get("version").getAsString();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_PATH + DEFAULT_FILE_SETTINGS_NAME))));
        JsonObject object = JsonParser.parseReader(bufferedReader).getAsJsonObject();
        String needVersion = object.get("version").getAsString();

        if (!currentFileVersion.equals(needVersion))
        {
            JsonObject cash = new JsonObject();
            object.keySet().forEach(key -> {
                if (key.equals("version"))
                    cash.add(key,object.get(key));
                else
                {
                    if (jsonObject.has(key))
                        cash.add(key,jsonObject.get(key));
                    else
                        cash.add(key,object.get(key));
                }
            });
            jsonObject = cash;
        }
        syncSettingsInFile();
    }

    public static void syncSettingsInFile() {
        try {
            FileWriter fileSettings = new FileWriter(Path.of(DATA_PATH + USER_FILE_SETTINGS_NAME).toFile());
            fileSettings.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject));
            fileSettings.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getGamePath() {
        return jsonObject.get("game_path").getAsString();
    }

    public static void setGamePath(String path) {
        jsonObject.addProperty("game_path",path.replace("\\","\\\\"));
    }

    public static String getWorkDrivePath() {
        return jsonObject.get("work_drive_path").getAsString();
    }

    public static void setWorkDrivePath(String path) {
        jsonObject.addProperty("work_drive_path",path.replace("\\","\\\\"));
    }

    public static String getWorkbenchPath() {
        return jsonObject.get("workbench_path").getAsString();
    }

    public static void setWorkbenchPath(String value) {
        jsonObject.addProperty("workbench_path",value.replace("\\","\\\\"));
    }

    public static String getClientArguments() {
        return jsonObject.get("client_arguments").getAsString();
    }

    public static void setClientArguments(String value) {
        jsonObject.addProperty("client_arguments",value);
    }

    public static String getClientSandboxArguments() {
        return jsonObject.get("client_sandbox_arguments").getAsString();
    }

    public static void setClientSandboxArguments(String value) {
        jsonObject.addProperty("client_sandbox_arguments",value);
    }

    public static String getServerArguments() {
        return jsonObject.get("server_arguments").getAsString();
    }

    public static void setServerArguments(String value) {
        jsonObject.addProperty("server_arguments",value);
    }

    public static List<String> getClientMods() {
        return jsonObject.get("client_mods").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
    }

    public static void setClientMods(List<String> value) {
        jsonObject.add("client_mods",new GsonBuilder().create().toJsonTree(value));
    }

    public static void addClientMod(String mod) {
        if (mod.isEmpty()) return;

        ArrayList<String> mods = new ArrayList<>(getClientMods());
        if (!mods.contains(mod))
            mods.add(mod);
        setClientMods(mods);
        syncSettingsInFile();
    }

    public static void removeClientMod(String mod) {
        if (mod.isEmpty()) return;

        ArrayList<String> mods = new ArrayList<>(getClientMods());
        mods.remove(mod);
        setClientMods(mods);
        syncSettingsInFile();
    }

    public static List<String> getServerMods() {
        return jsonObject.get("server_mods").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
    }

    public static void setServerMods(List<String> value) {
        jsonObject.add("server_mods",new GsonBuilder().create().toJsonTree(value));
    }

    public static void addServerMod(String mod) {
        if (mod.isEmpty()) return;

        ArrayList<String> mods = new ArrayList<>(getServerMods());
        if (!mods.contains(mod))
            mods.add(mod);
        setServerMods(mods);
        syncSettingsInFile();
    }

    public static void removeServerMod(String mod) {
        if (mod.isEmpty()) return;

        ArrayList<String> mods = new ArrayList<>(getServerMods());
        mods.remove(mod);
        setServerMods(mods);
        syncSettingsInFile();
    }

    public static boolean containsEmptyParams() {
        StringBuilder msg = new StringBuilder("<html>Looks like you forgot to set:");

        if (getGamePath().isEmpty())
            msg.append("<br>").append(" Game path not set!");
        if (getWorkbenchPath().isEmpty())
            msg.append("<br>").append(" Workbench path not set!");
        if (getWorkDrivePath().isEmpty())
            msg.append("<br>").append(" Work drive path is empty 0_o!");
        if (getServerArguments().isEmpty())
            msg.append("<br>").append(" Server arguments is empty 0_o!");
        if (getClientArguments().isEmpty())
            msg.append("<br>").append(" Client arguments is empty 0_o!");
        if (getClientSandboxArguments().isEmpty())
            msg.append("<br>").append(" Client sandbox arguments is empty 0_o!");

        if(msg.toString().contains("<br>"))
        {
            UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
            UIManager.put("OptionPane.background", DarkSwingColors.FRAME_BACKGROUND);
            JOptionPane.showMessageDialog(null,
                    new DarkLabel(msg.toString()),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            return true;
        }
        return false;
    }

    public static List<String> getTaskKillList() {
        return jsonObject.get("task_kill_list").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
    }

    public static void setTaskKillList(List<String> value) {
        jsonObject.add("task_kill_list",new GsonBuilder().create().toJsonTree(value));
    }

    public static void addTaskKill(String mod) {
        if (mod.isEmpty()) return;

        ArrayList<String> mods = new ArrayList<>(getTaskKillList());
        if (!mods.contains(mod))
            mods.add(mod);
        setTaskKillList(mods);
        syncSettingsInFile();
    }

    public static void removeTaskKill(String mod) {
        if (mod.isEmpty()) return;

        ArrayList<String> mods = new ArrayList<>(getTaskKillList());
        mods.remove(mod);
        setTaskKillList(mods);
        syncSettingsInFile();
    }
}
