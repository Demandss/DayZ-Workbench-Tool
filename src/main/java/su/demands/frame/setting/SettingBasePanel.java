package su.demands.frame.setting;

import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.label.DarkLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

public abstract class SettingBasePanel extends JPanel {

    protected GridBagConstraints constraints = new GridBagConstraints();
    protected JPanel panel = new JPanel(new GridBagLayout());

    public SettingBasePanel(Dimension size) {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets.top = 5;
        constraints.insets.left = 5;
        constraints.gridx = 0;
        constraints.gridy = 0;

        setLayout(new BorderLayout());
        setSize(size);

        initElements();
        permanentElements();

        setDoubleBuffered(false);
        setBackground(DarkSwingColors.FRAME_BACKGROUND);

        panel.setBackground(getBackground());
        panel.setPreferredSize(new Dimension(getWidth(), Math.min(panel.getComponentCount() * 15, (int) (getHeight() - (getHeight() * 0.275)))));
        add(panel,BorderLayout.PAGE_START);
    }

    protected void addElement(Component component) {
        panel.add(component, constraints);
    }

    protected abstract void initElements();

    protected void permanentElements() {
        DarkLabel demands = new DarkLabel("Powered by ©Demands.");
        demands.setForeground(Color.decode("#404348"));
        demands.setFont(new Font("Inter", Font.PLAIN,14));
        demands.setToolTipText("github.com/Demandss/DayZ-WorkbenchTool");
        demands.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(URI.create("https://github.com/Demandss/DayZ-WorkbenchTool"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        demands.setSize(60,14);
        demands.setHorizontalAlignment(SwingConstants.LEFT);
        demands.setVerticalAlignment(SwingConstants.BOTTOM);

        add(demands);
    }

    public abstract void saveSetting();
}
