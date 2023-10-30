package su.demands.elements;

import lombok.Getter;
import su.demands.Main;
import su.demands.common.SettingsManager;
import su.demands.common.tools.ReferenceTools;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.darkswing.elements.label.DarkLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;

@Getter
public class ModSettingsPanel extends ModChooserPanel {

    protected DarkButton removeModDarkButton;
    protected DarkButton modPriorityUpDarkButton;
    protected DarkButton modPriorityDownDarkButton;

    protected ModListPanel modListPanel;

    public ModSettingsPanel() {
        setPreferredSize(new Dimension(getPreferredSize().width,315));

        removeModDarkButton = new DarkButton();
        removeModDarkButton.setText("-");
        removeModDarkButton.setFont(textField.getFont().deriveFont(textField.getFont().getSize() + 2f));
        removeModDarkButton.setBorderPainted(true);
        removeModDarkButton.setBackground(getBackground());
        removeModDarkButton.setBorder(searchFile.getBorder());
        removeModDarkButton.addActionListener(e -> modListPanel.removeSelectedMod());

        add(removeModDarkButton);

        modListPanel = new ModListPanel();
        modListPanel.setDoubleBuffered(false);
        modListPanel.setBackground(getBackground());
        modListPanel.setBorder(searchFile.getBorder());

        modListPanel.getList().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = modListPanel.getList().locationToIndex(e.getPoint());
                if (index > -1) {
                    ModificationRenderer cellRenderer = (ModificationRenderer) modListPanel.getList().getCellRenderer();

                    Path modFolder = cellRenderer.modification.getPath();

                    if (!Files.exists(modFolder))
                    {
                        textField.setText("");
                        UIManager.put("Panel.background", DarkSwingColors.FRAME_BACKGROUND);
                        JOptionPane.showMessageDialog(null,
                                new DarkLabel("Looks like file %s no longer exists!".formatted(modFolder.toString())),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    configuredFromOutside = true;
                    textField.setText(modFolder.toString());
                    textField.setCaretPosition(0);

                    File file = new File(modFolder.toString());

                    fileChooser.setSelectedFile(file);
                    fileChooser.setCurrentDirectory(file);
                }
            }
        });

        add(modListPanel);

        plusDarkButton.addActionListener(e -> {
            Path modPath = getPath();

            if (!Files.exists(modPath)) return;

            SettingsManager.addClientMod(modPath.toString());

            loadMods();

            clearPath();
        });
    }

    public void syncSettings() {
        Map<Modification.ESide, List<Modification>> mods = modListPanel.modifications.stream()
                .collect(Collectors.groupingBy(Modification::getSide));

        if (mods.get(Modification.ESide.CLIENT) != null)
            SettingsManager.setClientMods(mods.get(Modification.ESide.CLIENT).stream()
                    .map(mod -> mod.getPath().toString() + "&" + mod.isEnabled())
                    .toList());
        else
            SettingsManager.setClientMods(new ArrayList<>(Collections.emptyList()));

        if (mods.get(Modification.ESide.SERVER) != null)
            SettingsManager.setServerMods(mods.get(Modification.ESide.SERVER).stream()
                    .map(mod -> mod.getPath().toString() + "&" + mod.isEnabled())
                    .toList());
        else
            SettingsManager.setServerMods(new ArrayList<>(Collections.emptyList()));

        SettingsManager.syncSettingsInFile();
    }

    public void loadMods() {
        modListPanel.modifications.clear();

        Main.globalLoadMods();

        ReferenceTools.MODIFICATIONS.forEach(modification -> modListPanel.addMod(modification));

        modListPanel.reRenderElements();
    }

    @Override
    protected void openFileChooserAction(ActionEvent event) {
        if (textField.getText().isEmpty())
            configuredFromOutside = false;

        super.openFileChooserAction(event);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        textField.setBounds(5,5,getWidth() - 79,20);
        searchFile.setBounds((textField.getX() + textField.getWidth()) + 3,textField.getY(),20,20);
        plusDarkButton.setBounds((searchFile.getX() + searchFile.getWidth()) + 3,textField.getY(),20,20);
        removeModDarkButton.setBounds((plusDarkButton.getX() + plusDarkButton.getWidth()) + 3,textField.getY(),20,20);

        modListPanel.setBounds(5,textField.getHeight() + 8,getWidth() - 10,(getHeight() - textField.getHeight()) - 8);
    }
}
