package su.demands;

import lombok.SneakyThrows;
import su.demands.common.ModificationManager;
import su.demands.common.SettingsManager;
import su.demands.common.tools.WindowsToolsBatch;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.frame.main.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

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

            try {
                Runtime.getRuntime().exec("cmd /c tasklist | find /i \"workbenchApp.exe\">nul && Taskkill /F /IM  \"workbenchApp.exe\"");
                Runtime.getRuntime().exec("cmd /c tasklist | find /i \"DayZDiag_x64.exe\">nul && Taskkill /F /IM  \"DayZDiag_x64.exe\"");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
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
                Path target = Path.of(finalPath + name);
                try {
                    Files.copy(Main.class.getResourceAsStream("/assets/DayZWorkbenchTool/"+name),target);
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