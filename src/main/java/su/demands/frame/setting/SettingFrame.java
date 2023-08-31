package su.demands.frame.setting;

import lombok.val;
import su.demands.common.SettingsManager;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.darkswing.elements.tabbedPane.DarkTabbedPane;
import su.demands.frame.BaseFrame;
import su.demands.frame.main.MainFrame;
import su.demands.frame.setting.exact.AdvancedSettingPanel;
import su.demands.frame.setting.exact.GlobalSettingPanel;

import javax.swing.*;
import java.awt.*;

public class SettingFrame extends BaseFrame {

    private MainFrame mainFrame;
    private Container mainContainer;
    private JMenuBar mainMenu;

    public SettingFrame(MainFrame mainFrame, Container mainContainer, JMenuBar mainMenu) {
        super("DayZ Workbench Tool Settings");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setVisible(false);

        this.mainFrame = mainFrame;
        this.mainContainer = mainContainer;
        this.mainMenu = mainMenu;
    }

    @Override
    protected void initElements() {
        setSize(400,400);

        val globalPanel = new GlobalSettingPanel(getSize());
        val advancedPanel = new AdvancedSettingPanel(getSize());

        DarkTabbedPane tabbedPane = new DarkTabbedPane();
        tabbedPane.addTab("Global",globalPanel);
        tabbedPane.addTab("Advanced",advancedPanel);

        getContentPane().add(tabbedPane,BorderLayout.CENTER);

        DarkButton settingCloseButton = new DarkButton();
        settingCloseButton.setBackground(getBackground());
        settingCloseButton.setFont(new Font("Inter", Font.PLAIN,10));
        settingCloseButton.setText("Back");
        settingCloseButton.addActionListener(e -> {
            mainFrame.setContentPane(mainContainer);
            mainFrame.setJMenuBar(mainMenu);
            mainFrame.validate();
        });

        getJMenuBar().add(settingCloseButton);

        DarkButton settingSaveDarkButton = new DarkButton();
        settingSaveDarkButton.setBackground(getBackground());
        settingSaveDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        settingSaveDarkButton.setText("Save");
        settingSaveDarkButton.addActionListener(e -> {
            DarkButton yesButton = new DarkButton("Yes");
            yesButton.addActionListener(e1 -> {
                globalPanel.saveSetting();
                advancedPanel.saveSetting();
                SettingsManager.syncSettingsInFile();
                JOptionPane.getRootFrame().dispose();
            });
            DarkButton noButton = new DarkButton("No");
            noButton.addActionListener(e1 -> JOptionPane.getRootFrame().dispose());

            JOptionPane.showOptionDialog(null,
                    new DarkLabel("Are you sure you want to save your settings?"),
                    "¯\\_(ツ)_/¯",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null,new Object[] {yesButton,noButton}, null);
        });

        getJMenuBar().add(settingSaveDarkButton);

        DarkButton settingResetDarkButton = new DarkButton();
        settingResetDarkButton.setBackground(getBackground());
        settingResetDarkButton.setFont(new Font("Inter", Font.PLAIN,10));
        settingResetDarkButton.setText("Reset");
        settingResetDarkButton.addActionListener(e -> {
            DarkButton yesButton = new DarkButton("Yes");
            yesButton.addActionListener(e1 -> {
                SettingsManager.resetUserConfig();
                JOptionPane.getRootFrame().dispose();
            });
            DarkButton noButton = new DarkButton("No");
            noButton.addActionListener(e1 -> JOptionPane.getRootFrame().dispose());

            JOptionPane.showOptionDialog(null,
                    new DarkLabel("Are you sure you want to reset all settings?"),
                    "¯\\_(ツ)_/¯",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null,new Object[] {yesButton,noButton}, null);
        });

        getJMenuBar().add(settingResetDarkButton);

        getJMenuBar().setBackground(getBackground());
        getJMenuBar().setBorder(BorderFactory.createMatteBorder(0,0,1,0, DarkSwingColors.DARKENED_BORDER));
    }

    @Override
    protected void permanentElements() {}
}
