package su.demands.common;

import lombok.SneakyThrows;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.label.DarkLabel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ModificationManager {

    @SneakyThrows
    public static void makeMklinkMods() {
        UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
        UIManager.put("OptionPane.background", DarkSwingColors.FRAME_BACKGROUND);
        if (SettingsManager.getGamePath().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    new DarkLabel("It looks like you have not set a game path"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Path gameScriptsPath = Path.of(SettingsManager.getWorkDrivePath() + "\\scripts");

        if (!Files.exists(gameScriptsPath)) {
            JOptionPane.showMessageDialog(null,
                    new DarkLabel("Looks like you haven't unpacked the game scripts in " + SettingsManager.getWorkDrivePath()),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Path> modifications = SettingsManager.getAllMods().stream()
                .filter(mod -> !mod.contains("@"))
                .map(Path::of)
                .toList();

        if (!modifications.isEmpty()) {
            modifications.forEach(mod -> {
                Path modLinkPath = Path.of(SettingsManager.getGamePath() + "\\" + mod.getFileName());
                if (!Files.exists(modLinkPath)) {
                    try {
                        Files.createSymbolicLink(modLinkPath,mod);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        Path gameScriptsLinkPath = Path.of(SettingsManager.getGamePath() + "\\scripts");

        if (!Files.exists(gameScriptsLinkPath))
            Files.createSymbolicLink(gameScriptsLinkPath,gameScriptsPath);
    }

    @SneakyThrows
    public static void makeMklinkWorkshop() {
        if (SettingsManager.getWorkDrivePath().isEmpty() || SettingsManager.getGamePath().isEmpty()) {
            UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
            UIManager.put("OptionPane.background", DarkSwingColors.FRAME_BACKGROUND);
            JOptionPane.showMessageDialog(null,
                    new DarkLabel("It looks like you have not set a working folder or path to the game"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Path workDrive = Path.of(SettingsManager.getWorkDrivePath() + "\\Workshop");

        if (!Files.exists(workDrive))
            Files.createSymbolicLink(workDrive,Path.of(SettingsManager.getGamePath() + "\\!Workshop"));
    }

    @SneakyThrows
    public static void removeMklinkMods() {
        UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
        UIManager.put("OptionPane.background", DarkSwingColors.FRAME_BACKGROUND);
        if (SettingsManager.getGamePath().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    new DarkLabel("It looks like you have not set a game path"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Path> modifications = SettingsManager.getAllMods().stream()
                .filter(mod -> !mod.contains("@"))
                .map(Path::of)
                .toList();

        if (!modifications.isEmpty()) {
            modifications.forEach(mod -> {
                Path modLinkPath = Path.of(SettingsManager.getGamePath() + "\\" + mod.getFileName());
                if (Files.exists(modLinkPath) && Files.isSymbolicLink(modLinkPath)) {
                    try {
                        Files.delete(modLinkPath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

        Path gameScriptsLinkPath = Path.of(SettingsManager.getGamePath() + "\\scripts");

        if (Files.exists(gameScriptsLinkPath) && Files.isSymbolicLink(gameScriptsLinkPath)) {
            Files.delete(gameScriptsLinkPath);
        }
    }

    public static void checkingExistenceMods() {
        ArrayList<String> modifications = new ArrayList<>(SettingsManager.getAllMods().stream()
                .filter(mod -> !mod.contains("@"))
                .toList());

        if (!modifications.isEmpty()) {
            for (String mod: modifications) {
                Path path = Path.of(mod);
                if (Files.exists(path)) {
                    modifications.remove(mod);
                }
            }
        }

        if (modifications.isEmpty()) return;;

        StringJoiner mods = new StringJoiner("<br>");
        modifications.forEach(mods::add);

        UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
        JOptionPane.showMessageDialog(null,
                new DarkLabel("<html>Looks like these mods don't exist anymore:<br>" + mods),
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}
