package su.demands.frame.main;

import su.demands.common.ModificationManager;
import su.demands.common.SettingsManager;
import su.demands.common.tools.ReferenceTools;
import su.demands.common.tools.WindowsToolsBatch;
import su.demands.frame.BaseFrame;
import su.demands.frame.setting.ModsSettingFrame;
import su.demands.frame.setting.SettingFrame;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainFrame extends BaseFrame {

    private MainFrameDesign design;
    protected SettingFrame settingFrame;
    protected ModsSettingFrame modsSettingFrame;

    public MainFrame() {
        super("DayZ Workbench Tool");
        setSize(400,400);
    }

    @Override
    protected void initElements() {
        design = new MainFrameDesign(this);
        design.init();
    }

    void settingsDarkButtonAction(ActionEvent event) {
        if (settingFrame == null)
            settingFrame = new SettingFrame(this,this.getContentPane(),this.getJMenuBar());

        setContentPane(settingFrame.getContentPane());
        setJMenuBar(settingFrame.getJMenuBar());
        validate();
    }

    void toolsFixScriptsMenuItemAction(ActionEvent event) {
        WindowsToolsBatch.buildUserSettings();
        try {
            Runtime.getRuntime().exec("cmd /c " + ReferenceTools.DATA_PATH_FIX_SCRIPTS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void toolsStopWorkbenchMenuItemAction(ActionEvent event) {
        String[] tasks = {"workbenchApp.exe","DayZDiag_x64.exe"};
        for (String task : tasks) {
            try {
                Runtime.getRuntime().exec("cmd /c tasklist | find /i \"%task%\">nul && Taskkill /F /IM  \"%task%\"".replaceAll("%task%",task));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void removeMklinkMenuItemAction(ActionEvent event) {
        ModificationManager.removeMklinkMods();
    }

    void workbenchDarkButtonAction(ActionEvent event) {
        ModificationManager.checkingExistenceMods();

        WindowsToolsBatch.buildUserSettings();
        ModificationManager.removeMklinkMods();
        ModificationManager.makeMklinkMods();
        try {
            Runtime.getRuntime().exec("cmd /c " + ReferenceTools.DATA_PATH_START_WORKBENCH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void debugClientDarkButtonAction(ActionEvent event) {
        ModificationManager.checkingExistenceMods();

        WindowsToolsBatch.buildUserSettings();
        ModificationManager.removeMklinkMods();
        ModificationManager.makeMklinkMods();
        try {
            Runtime.getRuntime().exec("cmd /c " + ReferenceTools.DATA_PATH_START_DEBUG_CLIENT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void debugClientSandboxDarkButtonAction(ActionEvent event) {
        //TODO - Maybe later
    }

    void debugAllDarkButtonAction(ActionEvent event) {
        ModificationManager.checkingExistenceMods();

        WindowsToolsBatch.buildUserSettings();
        ModificationManager.removeMklinkMods();
        ModificationManager.makeMklinkMods();
        try {
            Runtime.getRuntime().exec("cmd /c " + ReferenceTools.DATA_PATH_START_DEBUG_ALL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void debugServerDarkButtonAction(ActionEvent event) {
        ModificationManager.checkingExistenceMods();

        WindowsToolsBatch.buildUserSettings();
        ModificationManager.removeMklinkMods();
        ModificationManager.makeMklinkMods();
        try {
            Runtime.getRuntime().exec("cmd /c " + ReferenceTools.DATA_PATH_START_DEBUG_SERVER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void modChooserPlusDarkButtonClientAction(ActionEvent event) {
        Path modPath = design.modChooserClient.getPath();

        if (!Files.exists(modPath)) return;

        SettingsManager.addClientMod(modPath.toString());

        design.modChooserClient.clearPath();
    }

    void modChooserPlusDarkButtonServerAction(ActionEvent event) {
        Path modPath = design.modChooserServer.getPath();

        if (!Files.exists(modPath)) return;

        SettingsManager.addServerMod(modPath.toString());

        design.modChooserServer.clearPath();
    }

    void modsSettingsDarkButtonAction(ActionEvent event) {
        if (modsSettingFrame == null)
            modsSettingFrame = new ModsSettingFrame(this,this.getContentPane(),this.getJMenuBar());

        modsSettingFrame.loadMods();
        setContentPane(modsSettingFrame.getContentPane());
        setJMenuBar(modsSettingFrame.getJMenuBar());
        validate();
    }
}
