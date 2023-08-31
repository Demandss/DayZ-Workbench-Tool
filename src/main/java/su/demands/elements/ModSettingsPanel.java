package su.demands.elements;

import lombok.Getter;
import su.demands.common.SettingsManager;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

            System.out.println(modPath);

            if (!Files.exists(modPath)) return;

            SettingsManager.addClientMod(modPath.toString());

            loadMods();

            clearPath();
        });
    }

    public void syncSettings() {
        ArrayList<Modification> modifications = new ArrayList<>(modListPanel.modifications.stream()
                .filter(mod -> mod.getSide() == Modification.ESide.CLIENT)
                .toList());

        modifications.sort(Comparator.comparingInt(Modification::getLoadingPriority));

        SettingsManager.setClientMods(modifications.stream()
                .map(Modification::getPath)
                .map(Path::toString)
                .toList());

        modifications = new ArrayList<>(modListPanel.modifications.stream()
                .filter(mod -> mod.getSide() == Modification.ESide.SERVER)
                .toList());

        modifications.sort(Comparator.comparingInt(Modification::getLoadingPriority));

        SettingsManager.setServerMods(modifications.stream()
                .map(Modification::getPath)
                .map(Path::toString)
                .toList());

        SettingsManager.syncSettingsInFile();
    }

    public void loadMods() {
        modListPanel.modifications.clear();

        List<String> mods = SettingsManager.getClientMods();

        for (int i = 0; i < mods.size(); i++) {
            modListPanel.addMod(new Modification(Path.of(mods.get(i)), Modification.ESide.CLIENT,i));
        }

        mods = SettingsManager.getServerMods();

        for (int i = 0; i < mods.size(); i++) {
            modListPanel.addMod(new Modification(Path.of(mods.get(i)), Modification.ESide.SERVER,i));
        }

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
