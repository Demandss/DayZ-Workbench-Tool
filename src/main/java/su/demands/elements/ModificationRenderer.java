package su.demands.elements;

import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.darkswing.elements.panel.DarkPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ModificationRenderer implements ListCellRenderer<Modification> {

    protected Modification modification;
    protected DarkButton sideButton;
    protected DarkLabel nameLabel;

    @Override
    public Component getListCellRendererComponent(JList<? extends Modification> list, Modification modification, int index, boolean isSelected, boolean cellHasFocus) {
        if (modification.isSeparator()) {
            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(DarkSwingColors.ELEMENTS_BACKGROUND);
            separator.setForeground(DarkSwingColors.ELEMENTS_BACKGROUND);
            return separator;
        }

        this.modification = modification;

        DarkPanel panel = new DarkPanel();
        panel.setOpaque(true);

        sideButton = new DarkButton(new ImageIcon(modification.getSide().getIcon()));
        sideButton.setOpaque(false);
        sideButton.setContentAreaFilled(false);
        sideButton.setBorderPainted(false);
        sideButton.setPreferredSize(new Dimension(18,18));

        UpdateButtonToolTip(modification);

        sideButton.addActionListener(e -> {
            switch (modification.getSide()) {
                case CLIENT -> modification.setSide(Modification.ESide.SERVER);
                case SERVER -> modification.setSide(Modification.ESide.CLIENT);
            }
            sideButton.setIcon(new ImageIcon(modification.getSide().getIcon()));
            UpdateButtonToolTip(modification);
            list.updateUI();
        });

        panel.add(sideButton);

        Image image = modification.getType().getIcon();

        Border nameLabelBorder = BorderFactory.createMatteBorder(1,1,1,1, DarkSwingColors.SUB_SELECT);

        if (image != null)  {
            nameLabel = new DarkLabel(modification.getName(),new ImageIcon(image),DarkLabel.LEADING);
            nameLabel.setOpaque(false);
        } else {
            nameLabel = new DarkLabel(modification.getName());
            nameLabelBorder = new CompoundBorder(nameLabelBorder, new EmptyBorder(0, 5, 0, 0));
        }

        nameLabel.setPreferredSize(new Dimension(310,20));
        nameLabel.setBorder(nameLabelBorder);

        panel.add(nameLabel);

        if (isSelected)
            panel.setBackground(DarkSwingColors.SELECT);
        else
            panel.setBackground(DarkSwingColors.FRAME_BACKGROUND);

        return panel;
    }

    void UpdateButtonToolTip(Modification modification) {
        switch (modification.getSide()) {
            case CLIENT -> sideButton.setToolTipText("client side");
            case SERVER -> sideButton.setToolTipText("server side");
        }
    }
}
