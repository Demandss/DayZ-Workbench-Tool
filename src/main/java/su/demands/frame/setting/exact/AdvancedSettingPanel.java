package su.demands.frame.setting.exact;

import lombok.Getter;
import su.demands.common.SettingsManager;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.darkswing.elements.textfield.DarkTextField;
import su.demands.frame.setting.SettingBasePanel;

import java.awt.*;
import java.util.List;
import java.util.StringJoiner;


@Getter
public class AdvancedSettingPanel extends SettingBasePanel {

    private DarkTextField clientArgumentsTextField;
    private DarkTextField clientSandboxArgumentsTextField;
    private DarkTextField serverArgumentsTextField;
    private DarkTextField taskKillListTextField;

    public AdvancedSettingPanel(Dimension size) {
        super(size);
    }

    @Override
    protected void initElements() {
        DarkLabel clientArgumentsLabel = new DarkLabel("Client arguments");

        clientArgumentsLabel.setFont(new Font("Inter", Font.PLAIN,12));

        addElement(clientArgumentsLabel);

        clientArgumentsTextField = new DarkTextField();
        clientArgumentsTextField.setText(SettingsManager.getClientArguments());
        clientArgumentsTextField.setCaretPosition(0);
        clientArgumentsTextField.setPreferredSize(new Dimension(85,20));
        clientArgumentsTextField.getTextPrompt().setText("exe args");

        constraints.gridx = 1;
        addElement(clientArgumentsTextField);

        DarkLabel clientSandboxArgumentsLabel = new DarkLabel("Client sandbox arguments");

        clientSandboxArgumentsLabel.setFont(new Font("Inter", Font.PLAIN,12));

        constraints.gridx = 0;
        constraints.gridy = 1;
        addElement(clientSandboxArgumentsLabel);

        clientSandboxArgumentsTextField = new DarkTextField();
        clientSandboxArgumentsTextField.setText(SettingsManager.getClientSandboxArguments());
        clientSandboxArgumentsTextField.setCaretPosition(0);
        clientSandboxArgumentsTextField.setPreferredSize(new Dimension(85,20));
        clientSandboxArgumentsTextField.getTextPrompt().setText("exe args");

        constraints.gridx = 1;
        addElement(clientSandboxArgumentsTextField);

        DarkLabel serverArgumentsLabel = new DarkLabel("Server arguments");

        serverArgumentsLabel.setFont(new Font("Inter", Font.PLAIN,12));

        constraints.gridx = 0;
        constraints.gridy = 2;
        addElement(serverArgumentsLabel);

        serverArgumentsTextField = new DarkTextField();
        serverArgumentsTextField.setText(SettingsManager.getServerArguments());
        serverArgumentsTextField.setCaretPosition(0);
        serverArgumentsTextField.setPreferredSize(new Dimension(85,20));
        serverArgumentsTextField.getTextPrompt().setText("exe args");

        constraints.gridx = 1;
        addElement(serverArgumentsTextField);

        DarkLabel taskKillListLabel = new DarkLabel("Auto task kill list");

        taskKillListLabel.setFont(new Font("Inter", Font.PLAIN,12));

        constraints.gridx = 0;
        constraints.gridy = 3;
        addElement(taskKillListLabel);

        taskKillListTextField = new DarkTextField();

        StringJoiner taskKillList = new StringJoiner(", ");
        SettingsManager.getTaskKillList().forEach(taskKillList::add);

        taskKillListTextField.setText(taskKillList.toString());
        taskKillListTextField.setCaretPosition(0);
        taskKillListTextField.setPreferredSize(new Dimension(85,20));
        taskKillListTextField.getTextPrompt().setText("workbenchApp.exe, DayZDiag_x64.exe");

        constraints.gridx = 1;
        addElement(taskKillListTextField);
    }

    @Override
    public void saveSetting() {
        SettingsManager.setClientArguments(clientArgumentsTextField.getText());
        SettingsManager.setClientSandboxArguments(clientSandboxArgumentsTextField.getText());
        SettingsManager.setServerArguments(serverArgumentsTextField.getText());
        SettingsManager.setTaskKillList(List.of(taskKillListTextField.getText().replace(" ","").split(",")));
    }
}
