package su.demands;

import lombok.SneakyThrows;
import su.demands.common.ModificationManager;
import su.demands.common.SettingsManager;
import su.demands.common.tools.ReferenceTools;
import su.demands.common.tools.WindowsToolsBatch;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.elements.Modification;
import su.demands.frame.main.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Main {

    public static SettingsManager settingsManager;
    public static WindowsToolsBatch windowsToolsBatch;

    public static String DATA_PATH = System.getenv("APPDATA") + "/.dayzworkbenchtool";

    public static void main(String[] args) {
        settingsManager = new SettingsManager();

        UIManager.put("OptionPane.background", DarkSwingColors.FRAME_BACKGROUND);
        if (!Files.exists(Path.of(SettingsManager.getWorkDrivePath()))) {
            UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
            JOptionPane.showMessageDialog(null,
                    new DarkLabel("Looks like disk "+SettingsManager.getWorkDrivePath()+" was not found, mount it in DayZ Tools!"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        windowsToolsBatch = new WindowsToolsBatch();

        loadDayZWorkbenchToolMod();

        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ModificationManager.removeMklinkMods();

            Path toolsLinkPath = Path.of(SettingsManager.getWorkDrivePath() + "\\dayzworkbenchtool");

            if (Files.exists(toolsLinkPath)) {
                try {
                    Files.delete(toolsLinkPath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            SettingsManager.getTaskKillList().forEach(taskName -> {
                try {
                    Runtime.getRuntime().exec("cmd /c tasklist | find /i \"%task%\">nul && Taskkill /F /IM  \"%task%\"".replaceAll("%task%",taskName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }));

        globalLoadMods();
    }

    public static void globalLoadMods() {
        List<String> mods = SettingsManager.getClientMods();

        for (int i = 0; i < mods.size(); i++) {
            ReferenceTools.MODIFICATIONS.add(new Modification(mods.get(i), Modification.ESide.CLIENT,i));
        }

        mods = SettingsManager.getServerMods();

        for (int i = 0; i < mods.size(); i++) {
            ReferenceTools.MODIFICATIONS.add(new Modification(mods.get(i), Modification.ESide.SERVER,i));
        }
    }

    @SneakyThrows
    static void loadDayZWorkbenchToolMod() {
        //temp
        String[] pluginFiles = {"StartDebugAll.c","StartDebugClient.c","StartDebugServer.c"};

        Path path = Path.of(SettingsManager.getWorkDrivePath() + "\\scripts\\editor\\plugins\\DayZWorkbenchTool\\");

        if (!Files.exists(path)) {
            Files.createDirectories(path);
            Path finalPath = path;
            Arrays.stream(pluginFiles).toList().forEach(name -> {
                Path target = Path.of(finalPath + "\\" + name);
                try {
                    Files.copy(Objects.requireNonNull(Main.class.getResourceAsStream("/assets/DayZWorkbenchTool/" + name)),target);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        path = Path.of(DATA_PATH + "\\tools\\");

        if (Files.exists(path)) {
            Path toolsLinkPath = Path.of(SettingsManager.getWorkDrivePath() + "\\dayzworkbenchtool");

            if (!Files.exists(toolsLinkPath))
                Files.createSymbolicLink(toolsLinkPath,path);
        }
    }
}