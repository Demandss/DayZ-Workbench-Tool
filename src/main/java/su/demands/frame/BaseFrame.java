package su.demands.frame;

import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.darkswing.elements.menuBar.DarkMenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

public abstract class BaseFrame extends JFrame {

    protected final DarkMenuBar menuBar = new DarkMenuBar();

    public BaseFrame(String title) {
        setBackground(DarkSwingColors.FRAME_BACKGROUND);
        setTitle(title);
        setSize(800,600);
        setResizable(false);
        setLocationRelativeTo(null);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(getBackground());
        initElements();
        permanentElements();
    }

    protected abstract void initElements();

    protected void permanentElements() {
        DarkLabel demands = new DarkLabel("Powered by ©Demands.");
        demands.setForeground(Color.decode("#404348"));
        demands.setFont(new Font("Inter", Font.PLAIN,14));
        demands.setToolTipText("github.com/Demandss/DayZ-Workbench-Tool");
        demands.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(URI.create("https://github.com/Demandss/DayZ-Workbench-Tool"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        demands.setSize(60,14);
        demands.setHorizontalAlignment(SwingConstants.LEFT);
        demands.setVerticalAlignment(SwingConstants.BOTTOM);

        getContentPane().add(demands);

        getJMenuBar().setBackground(getBackground());
        getJMenuBar().setBorder(BorderFactory.createMatteBorder(0,0,1,0, DarkSwingColors.DARKENED_BORDER));
    }
}
