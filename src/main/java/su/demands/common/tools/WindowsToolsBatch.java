package su.demands.common.tools;

import lombok.SneakyThrows;
import su.demands.common.SettingsManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class WindowsToolsBatch extends ReferenceToolsPath {

    @SneakyThrows
    public WindowsToolsBatch() {
        Path toolsPath = Path.of(DATA_PATH);

        File tools = new File(toolsPath.toUri());
        if (!tools.exists())
            tools.mkdirs();

        if (!Files.exists(Path.of(toolsPath + FIX_SCRIPTS_FILENAME)))
            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_FIX_SCRIPTS)),Path.of(toolsPath + FIX_SCRIPTS_FILENAME));

        if (!Files.exists(Path.of(toolsPath + START_DEBUG_ALL_FILENAME)))
            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_START_DEBUG_ALL)),Path.of(toolsPath + START_DEBUG_ALL_FILENAME));

        if (!Files.exists(Path.of(toolsPath + START_DEBUG_CLIENT_FILENAME)))
            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_START_DEBUG_CLIENT)),Path.of(toolsPath + START_DEBUG_CLIENT_FILENAME));

        if (!Files.exists(Path.of(toolsPath + START_DEBUG_SERVER_FILENAME)))
            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_START_DEBUG_SERVER)),Path.of(toolsPath + START_DEBUG_SERVER_FILENAME));

        if (!Files.exists(Path.of(toolsPath + START_WORKBENCH_FILENAME)))
            Files.copy(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_START_WORKBENCH)),Path.of(toolsPath + START_WORKBENCH_FILENAME));
    }

    @SneakyThrows
    public static void buildUserSettings() {
        String text = new BufferedReader(new InputStreamReader(Objects.requireNonNull(WindowsToolsBatch.class.getResourceAsStream(RESOURCES_USER_SETTINGS))))
                .lines().parallel().collect(Collectors.joining("\n"));

        text = text.replaceAll("&GAME_PATH&", SettingsManager.getGamePath());
        text = text.replaceAll("&WORKDRIVE_PATH&", SettingsManager.getWorkDrivePath());
        text = text.replaceAll("&WORKBENCH_PATH&", SettingsManager.getWorkbenchPath());

        StringJoiner modsClient = new StringJoiner(";");

        ArrayList<String> modClientList = new ArrayList<>(SettingsManager.getClientMods());
        modClientList.forEach(modsClient::add);

        text = text.replaceAll("&MODS_CLIENT&", modsClient.toString().replace("\\","\\\\"));

        StringJoiner modsServer = new StringJoiner(";");

        ArrayList<String> modServerList = new ArrayList<>(SettingsManager.getServerMods());
        modServerList.forEach(modsServer::add);

        text = text.replaceAll("&MODS_SERVER&", modsServer.toString().replace("\\","\\\\"));

        ArrayList<String> allMods = new ArrayList<>(modClientList);
        allMods.addAll(modServerList);
        allMods.sort(Comparator.comparing(mod -> mod.contains("@")));
        allMods = new ArrayList<>(allMods.stream().map(mod -> mod.replace("\\","\\\\")).toList());

        StringJoiner loadMods = new StringJoiner(";");
        allMods.forEach(loadMods::add);

        text = text.replaceAll("&LOAD_MODS&", loadMods.toString());

        text = text.replaceAll("&SERVER_ARGS&", SettingsManager.getServerArguments());
        text = text.replaceAll("&CLIENT_ARGS&", SettingsManager.getClientArguments());
        text = text.replaceAll("&CLIENT_SANDBOX_ARGS&", SettingsManager.getClientSandboxArguments());

        if (Files.exists(Path.of(DATA_PATH_USER_SETTINGS)))
            Files.delete(Path.of(DATA_PATH_USER_SETTINGS));

        try {
            FileWriter file = new FileWriter(Path.of(DATA_PATH_USER_SETTINGS).toFile());
            file.write(text);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
