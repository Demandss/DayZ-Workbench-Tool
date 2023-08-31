package su.demands.elements;

import lombok.Getter;
import lombok.Setter;
import su.demands.common.ModificationManager;
import su.demands.common.SettingsManager;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.darkswing.elements.fileChooser.DarkFileChooser;
import su.demands.darkswing.elements.fileChooser.DarkFileChooserPanel;
import su.demands.darkswing.elements.label.DarkLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

@Getter
public class ModChooserPanel extends DarkFileChooserPanel {

    protected DarkButton plusDarkButton;

    @Setter
    protected boolean configuredFromOutside = false;

    public ModChooserPanel() {
        plusDarkButton = new DarkButton();
        plusDarkButton.setText("+");
        plusDarkButton.setFont(textField.getFont().deriveFont(textField.getFont().getSize() + 2f));
        plusDarkButton.setBorderPainted(true);
        plusDarkButton.setBackground(getBackground());
        plusDarkButton.setBorder(searchFile.getBorder());

        add(plusDarkButton);

        UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
        UIManager.put("Label.foreground", DarkSwingColors.TEXT_FOREGROUND);
        fileChooser = new DarkFileChooser();
    }

    @Override
    protected void openFileChooserAction(ActionEvent event) {
        UIManager.put("OptionPane.background", DarkSwingColors.FRAME_BACKGROUND);

        DarkButton localModDarkButton = new DarkButton("Local");
        localModDarkButton.setToolTipText("Will open the P drive to select a local mod");
        DarkButton workshopModDarkButton = new DarkButton("Workshop");
        workshopModDarkButton.setToolTipText("Will open folder with mods downloaded from SteamWorkshop");

        AtomicReference<String> modRootPath = new AtomicReference<>("-1");

        localModDarkButton.addActionListener(e -> {
            modRootPath.set("P:\\");
            if (!Files.exists(Path.of(modRootPath.get())))
                JOptionPane.showMessageDialog(null,
                        new DarkLabel("Looks like disk P was not found, mount it in DayZ Tools!"),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            else
            {
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                JOptionPane.getRootFrame().dispose();
            }
        });
        workshopModDarkButton.addActionListener(e -> {
            modRootPath.set("P:\\Workshop");
            if (!Files.exists(Path.of(modRootPath.get()))) {
                if (SettingsManager.getGamePath().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            new DarkLabel("Looks like you didn't specify the path to the game in the settings!"),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ModificationManager.makeMklinkWorkshop();
            } else {
                fileChooser.addPropertyChangeListener(e1 -> {
                    if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(e1.getPropertyName())) {
                        textField.setText(fileChooser.getCurrentDirectory().getPath());

                        JComponent comp = (JComponent) e1.getSource();
                        Window win = SwingUtilities.getWindowAncestor(comp);
                        if (win != null)
                            win.dispose();
                    }
                });
                JOptionPane.getRootFrame().dispose();
            }
        });

        if (!configuredFromOutside)
        {
            JOptionPane.showOptionDialog(null, new DarkLabel("Select modification type"),
                    "¯\\_(ツ)_/¯",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null,new Object[] {localModDarkButton,workshopModDarkButton}, null);

            if (modRootPath.get().equals("-1")) return;

            fileChooser.setCurrentDirectory(new File(modRootPath.get()));
        }

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setApproveButtonText("Select");

        int rVal = fileChooser.showOpenDialog(this);
        if (rVal == JFileChooser.APPROVE_OPTION) {
            textField.setText(fileChooser.getSelectedFile().getPath());
        }
    }

    public void clearPath() {
        fileChooser.setSelectedFile(null);
        textField.setText("");
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        textField.setBounds(0,0,getWidth() - 46,20);
        searchFile.setBounds((textField.getX() + textField.getWidth()) + 3,0,20,20);
        plusDarkButton.setBounds((searchFile.getX() + searchFile.getWidth()) + 3,0,20,20);
    }
}
