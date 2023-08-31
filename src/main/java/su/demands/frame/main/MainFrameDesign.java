package su.demands.frame.main;

import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.elements.ModChooserPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainFrameDesign {

    private final MainFrame frame;

    public MainFrameDesign(MainFrame frame) {
        this.frame = frame;
    }

    void init() {
        frame.getJMenuBar().setBackground(frame.getBackground());
        frame.getJMenuBar().setBorder(BorderFactory.createMatteBorder(0,0,1,0, DarkSwingColors.DARKENED_BORDER));

        DarkButton settingsDarkButton = new DarkButton();
        settingsDarkButton.setBackground(frame.getBackground());
        settingsDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        settingsDarkButton.setText("Settings");

        settingsDarkButton.addActionListener(frame::settingsDarkButtonAction);

        frame.getJMenuBar().add(settingsDarkButton);

        JMenu toolsMenu = new JMenu();
        toolsMenu.setBackground(frame.getBackground());
        toolsMenu.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        toolsMenu.setBorder(null);
        toolsMenu.setFont(new Font("Inter", Font.PLAIN,10));
        toolsMenu.setText("Tools");

        JMenuItem fixScriptsMenuItem = new JMenuItem();
        fixScriptsMenuItem.setBackground(frame.getBackground());
        fixScriptsMenuItem.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        fixScriptsMenuItem.setFont(new Font("Inter", Font.PLAIN,10));
        fixScriptsMenuItem.setText("fixScripts");
        fixScriptsMenuItem.setToolTipText("Fix paths in unpacked DayZ scripts on drive P");
        fixScriptsMenuItem.addActionListener(frame::toolsFixScriptsMenuItemAction);

        toolsMenu.add(fixScriptsMenuItem);

        JMenuItem removeMklinkMenuItem = new JMenuItem();
        removeMklinkMenuItem.setBackground(frame.getBackground());
        removeMklinkMenuItem.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        removeMklinkMenuItem.setFont(new Font("Inter", Font.PLAIN,10));
        removeMklinkMenuItem.setText("remove mods mklink");
        removeMklinkMenuItem.setToolTipText("Removes all shortened links to mods on drive P");
        removeMklinkMenuItem.addActionListener(frame::removeMklinkMenuItemAction);

        toolsMenu.add(removeMklinkMenuItem);

        frame.getJMenuBar().add(toolsMenu);

        toolsPanel();
        modsPanel();
    }

    void toolsPanel() {
        JPanel panel = new JPanel();
        panel.setDoubleBuffered(false);
        panel.setBackground(DarkSwingColors.FRAME_BACKGROUND);

        panel.setLayout(new GridBagLayout());

        GridBagConstraints bagConstraints = new GridBagConstraints();
        bagConstraints.fill = GridBagConstraints.BOTH;
        bagConstraints.ipadx = 0;
        bagConstraints.ipady = 0;
        bagConstraints.weightx = 1;

        DarkButton workbenchDarkButton = new DarkButton();
        workbenchDarkButton.setBackground(DarkSwingColors.ELEMENTS_BACKGROUND);
        workbenchDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        workbenchDarkButton.setText("Workbench");
        workbenchDarkButton.setMinimumSize(new Dimension(180,24));

        workbenchDarkButton.addActionListener(frame::workbenchDarkButtonAction);

        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        bagConstraints.insets = new Insets(0,1,6,2);
        panel.add(workbenchDarkButton,bagConstraints);

        DarkButton debugClientDarkButton = new DarkButton();
        debugClientDarkButton.setBackground(DarkSwingColors.ELEMENTS_BACKGROUND);
        debugClientDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        debugClientDarkButton.setText("Debug Client");
        debugClientDarkButton.setMinimumSize(new Dimension(180,24));

        debugClientDarkButton.addActionListener(frame::debugClientDarkButtonAction);

        bagConstraints.gridy = 1;
        panel.add(debugClientDarkButton,bagConstraints);

        DarkButton debugClientSandboxDarkButton = new DarkButton();
        debugClientSandboxDarkButton.setBackground(DarkSwingColors.ELEMENTS_BACKGROUND);
        debugClientSandboxDarkButton.setForeground(DarkSwingColors.TEXT_GHOSTLY_FOREGROUND);
        debugClientSandboxDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        debugClientSandboxDarkButton.setText("Debug Client Sandbox");
        debugClientSandboxDarkButton.setMinimumSize(new Dimension(180,24));
        debugClientSandboxDarkButton.setToolTipText("maybe later");

        debugClientSandboxDarkButton.addActionListener(frame::debugClientSandboxDarkButtonAction);

        bagConstraints.gridy = 2;
        panel.add(debugClientSandboxDarkButton,bagConstraints);

        DarkButton debugAllDarkButton = new DarkButton();
        debugAllDarkButton.setBackground(DarkSwingColors.ELEMENTS_BACKGROUND);
        debugAllDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        debugAllDarkButton.setText("Debug All");
        debugAllDarkButton.setMinimumSize(new Dimension(180,24));

        debugAllDarkButton.addActionListener(frame::debugAllDarkButtonAction);

        bagConstraints.gridx = 1;
        bagConstraints.gridy = 0;
        bagConstraints.insets = new Insets(0,4,6,1);
        panel.add(debugAllDarkButton,bagConstraints);

        DarkButton debugServerDarkButton = new DarkButton();
        debugServerDarkButton.setBackground(DarkSwingColors.ELEMENTS_BACKGROUND);
        debugServerDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        debugServerDarkButton.setText("Debug Server");
        debugServerDarkButton.setMinimumSize(new Dimension(180,24));

        debugServerDarkButton.addActionListener(frame::debugServerDarkButtonAction);

        bagConstraints.gridy = 1;
        panel.add(debugServerDarkButton,bagConstraints);

        TitledBorder toolsPanelBorder = BorderFactory.createTitledBorder("Tools");
        toolsPanelBorder.setBorder(BorderFactory.createMatteBorder(1,1,1,1, DarkSwingColors.ELEMENTS_BACKGROUND));
        toolsPanelBorder.setTitleColor(DarkSwingColors.TEXT_FOREGROUND);
        toolsPanelBorder.setTitleFont(new Font("Inter", Font.PLAIN,12));

        panel.setBorder(toolsPanelBorder);
        panel.setBounds(2,5,380,108);

        frame.getContentPane().add(panel);
    }

    ModChooserPanel modChooserClient;
    ModChooserPanel modChooserServer;

    void modsPanel() {
        JPanel panel = new JPanel();
        panel.setDoubleBuffered(false);
        panel.setLayout(new GridBagLayout());

        GridBagConstraints bagConstraints = new GridBagConstraints();
        bagConstraints.fill = GridBagConstraints.BOTH;
        bagConstraints.ipadx = 0;
        bagConstraints.ipady = 0;
        bagConstraints.weightx = 1;

        panel.setBackground(DarkSwingColors.FRAME_BACKGROUND);

        JLabel labelModTypeClient = new JLabel();

        labelModTypeClient.setText("Client");
        labelModTypeClient.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        labelModTypeClient.setFont(new Font("Inter", Font.PLAIN,12));
        labelModTypeClient.setHorizontalAlignment(JLabel.CENTER);

        bagConstraints.gridx = 0;
        bagConstraints.gridy = 0;
        bagConstraints.insets = new Insets(0,1,6,2);
        panel.add(labelModTypeClient,bagConstraints);

        modChooserClient = new ModChooserPanel();
        modChooserClient.getTextField().getTextPrompt().setText("mod folder..");
        modChooserClient.getPlusDarkButton().addActionListener(frame::modChooserPlusDarkButtonClientAction);

        bagConstraints.gridy = 1;
        bagConstraints.ipady = 20;
        bagConstraints.insets = new Insets(0,1,6,2);
        panel.add(modChooserClient,bagConstraints);
        bagConstraints.ipady = 0;

        JLabel labelModTypeServer = new JLabel();

        labelModTypeServer.setText("Server");
        labelModTypeServer.setForeground(DarkSwingColors.TEXT_FOREGROUND);
        labelModTypeServer.setFont(new Font("Inter", Font.PLAIN,12));
        labelModTypeServer.setHorizontalAlignment(JLabel.CENTER);

        bagConstraints.gridx = 1;
        bagConstraints.gridy = 0;
        bagConstraints.insets = new Insets(0,4,6,1);
        panel.add(labelModTypeServer,bagConstraints);

        modChooserServer = new ModChooserPanel();
        modChooserServer.getTextField().getTextPrompt().setText("mod folder..");
        modChooserServer.getPlusDarkButton().addActionListener(frame::modChooserPlusDarkButtonServerAction);

        bagConstraints.gridy = 1;
        bagConstraints.ipady = 20;
        bagConstraints.insets = new Insets(0,4,6,1);
        panel.add(modChooserServer,bagConstraints);
        bagConstraints.ipady = 0;

        DarkButton modsSettingsDarkButton = new DarkButton();
        modsSettingsDarkButton.setBackground(DarkSwingColors.ELEMENTS_BACKGROUND);
        modsSettingsDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        modsSettingsDarkButton.setText("Mods settings");

        modsSettingsDarkButton.addActionListener(frame::modsSettingsDarkButtonAction);

        bagConstraints.gridx = 0;
        bagConstraints.gridy = 2;
        bagConstraints.gridwidth = 2;
        bagConstraints.insets = new Insets(0,1,0,0);
        panel.add(modsSettingsDarkButton,bagConstraints);

        TitledBorder modsPanelBorder = BorderFactory.createTitledBorder("Mods");
        modsPanelBorder.setBorder(BorderFactory.createMatteBorder(1,1,1,1, DarkSwingColors.ELEMENTS_BACKGROUND));
        modsPanelBorder.setTitleColor(DarkSwingColors.TEXT_FOREGROUND);
        modsPanelBorder.setTitleFont(new Font("Inter", Font.PLAIN,12));

        panel.setBorder(modsPanelBorder);
        panel.setBounds(2,130,380,100);

        frame.getContentPane().add(panel);
    }
}
