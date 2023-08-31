package su.demands.frame.setting.exact;

import lombok.Getter;
import su.demands.common.SettingsManager;
import su.demands.darkswing.elements.fileChooser.DarkFileChooserPanel;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.frame.setting.SettingBasePanel;

import javax.swing.*;
import java.awt.*;

@Getter
public class GlobalSettingPanel extends SettingBasePanel {

    private DarkFileChooserPanel gamePathFileChooser;
    private DarkFileChooserPanel workDriveFileChooser;
    private DarkFileChooserPanel workbenchPathFileChooser;
    private DarkFileChooserPanel workshopPathFileChooser;

    public GlobalSettingPanel(Dimension size) {
        super(size);
    }

    @Override
    protected void initElements() {
        DarkLabel gamePathLabel = new DarkLabel("Game path");

        gamePathLabel.setFont(new Font("Inter", Font.PLAIN,12));

        addElement(gamePathLabel);

        gamePathFileChooser = new DarkFileChooserPanel();
        gamePathFileChooser.getTextField().getTextPrompt().setText("path..");
        gamePathFileChooser.getFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        gamePathFileChooser.getTextField().setText(SettingsManager.getGamePath().replace("\\\\","\\"));

        constraints.gridx = 1;
        addElement(gamePathFileChooser);

        DarkLabel workDriveLabel = new DarkLabel("Work drive path");

        workDriveLabel.setFont(new Font("Inter", Font.PLAIN,12));

        constraints.gridx = 0;
        constraints.gridy = 1;
        addElement(workDriveLabel);

        workDriveFileChooser = new DarkFileChooserPanel();
        workDriveFileChooser.getTextField().getTextPrompt().setText("P:\\");
        workDriveFileChooser.getFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        workDriveFileChooser.setEnabled(false);

        /*if (!SettingsManager.getWorkDrivePath().replace("\\\\","\\").equals("P:\\"))
            workDriveFileChooser.getTextField().setText(SettingsManager.getWorkDrivePath().replace("\\\\","\\"));*/

        constraints.gridx = 1;
        addElement(workDriveFileChooser);

        DarkLabel workbenchPathLabel = new DarkLabel("Workbench path");

        workbenchPathLabel.setFont(new Font("Inter", Font.PLAIN,12));

        constraints.gridx = 0;
        constraints.gridy = 2;
        addElement(workbenchPathLabel);

        workbenchPathFileChooser = new DarkFileChooserPanel();
        workbenchPathFileChooser.getTextField().getTextPrompt().setText("path..");
        workbenchPathFileChooser.getFileChooser().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        workbenchPathFileChooser.getTextField().setText(SettingsManager.getWorkbenchPath().replace("\\\\","\\"));

        constraints.gridx = 1;
        addElement(workbenchPathFileChooser);
    }

    @Override
    public void saveSetting() {
        SettingsManager.setGamePath(gamePathFileChooser.getPath().toString());

        /*if (!workDriveFileChooser.getPath().toString().isEmpty())
            SettingsManager.setWorkDrivePath(workDriveFileChooser.getPath().toString());*/

        SettingsManager.setWorkbenchPath(workbenchPathFileChooser.getPath().toString());
    }
}
