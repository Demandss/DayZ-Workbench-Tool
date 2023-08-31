package su.demands.frame.setting;

import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.elements.ModListPanel;
import su.demands.elements.ModSettingsPanel;
import su.demands.elements.Modification;
import su.demands.frame.BaseFrame;
import su.demands.frame.main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class ModsSettingFrame extends BaseFrame {

    protected MainFrame mainFrame;
    protected Container mainContainer;
    protected JMenuBar mainMenu;

    protected ModSettingsPanel modSettingsPanel;

    public ModsSettingFrame(MainFrame mainFrame, Container mainContainer, JMenuBar mainMenu) {
        super("DayZ Workbench Tool Mods Settings");
        setVisible(false);

        this.mainFrame = mainFrame;
        this.mainContainer = mainContainer;
        this.mainMenu = mainMenu;
    }

    @Override
    protected void initElements() {
        setSize(400,400);

        modSettingsPanel = new ModSettingsPanel();
        modSettingsPanel.getTextField().getTextPrompt().setText("mod folder..");

        add(modSettingsPanel,BorderLayout.PAGE_START);

        DarkButton backMenuButton = new DarkButton();
        backMenuButton.setBackground(getBackground());
        backMenuButton.setFont(new Font("Inter", Font.PLAIN,10));
        backMenuButton.setText("Back");
        backMenuButton.addActionListener(e -> {
            modSettingsPanel.syncSettings();

            mainFrame.setContentPane(mainContainer);
            mainFrame.setJMenuBar(mainMenu);
            mainFrame.validate();
        });

        getJMenuBar().add(backMenuButton);

        JMenu filterMenu = new JMenu();
        filterMenu.setBackground(getBackground());
        filterMenu.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        filterMenu.setBorder(null);
        filterMenu.setFont(new Font("Inter", Font.PLAIN,10));
        filterMenu.setText("Filter");

        JMenuItem allFilterMenuItem = new JMenuItem();
        allFilterMenuItem.setBackground(getBackground());
        allFilterMenuItem.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        allFilterMenuItem.setFont(new Font("Inter", Font.PLAIN,10));
        allFilterMenuItem.setText("All");
        allFilterMenuItem.addActionListener(e -> modSettingsPanel.getModListPanel().setFilterType(ModListPanel.FilterType.ALL));

        filterMenu.add(allFilterMenuItem);

        JMenuItem serverFilterMenuItem = new JMenuItem();
        serverFilterMenuItem.setBackground(getBackground());
        serverFilterMenuItem.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        serverFilterMenuItem.setFont(new Font("Inter", Font.PLAIN,10));
        serverFilterMenuItem.setText("Server");
        serverFilterMenuItem.addActionListener(e -> modSettingsPanel.getModListPanel().setFilterType(ModListPanel.FilterType.SERVER));

        filterMenu.add(serverFilterMenuItem);

        JMenuItem clientFilterMenuItem = new JMenuItem();
        clientFilterMenuItem.setBackground(getBackground());
        clientFilterMenuItem.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        clientFilterMenuItem.setFont(new Font("Inter", Font.PLAIN,10));
        clientFilterMenuItem.setText("Client");
        clientFilterMenuItem.addActionListener(e -> modSettingsPanel.getModListPanel().setFilterType(ModListPanel.FilterType.CLIENT));

        filterMenu.add(clientFilterMenuItem);

        getJMenuBar().add(filterMenu);

        getJMenuBar().setBackground(getBackground());
        getJMenuBar().setBorder(BorderFactory.createMatteBorder(0,0,1,0, DarkSwingColors.DARKENED_BORDER));
    }

    public void loadMods() {
        modSettingsPanel.loadMods();
    }
}
