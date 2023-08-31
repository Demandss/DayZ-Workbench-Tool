package su.demands.elements;

import lombok.Getter;
import lombok.Setter;
import su.demands.common.SettingsManager;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.darkswing.elements.scrollPane.DarkScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@Getter
public class ModListPanel extends DarkScrollPane {

    protected FilterType filterType = FilterType.ALL;

    protected ArrayList<Modification> modifications = new ArrayList<>();

    protected Modification lastSelectedElement = null;

    protected JList<Modification> list;
    protected DefaultListModel<Modification> listModel;

    /*private Popup modToolTip;
    private Timer modToolTipTimer;*/

    public ModListPanel() {
        super(VERTICAL_SCROLLBAR_ALWAYS, HORIZONTAL_SCROLLBAR_NEVER);
        UIManager.put("OptionPane.background", DarkSwingColors.FRAME_BACKGROUND);

        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setCellRenderer(new ModificationRenderer());
        list.setVisibleRowCount(-1);
        list.setBackground(getBackground());

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = list.locationToIndex(e.getPoint());
                if (index > -1) {
                    Rectangle bounds = list.getCellBounds(index, index);
                    ModificationRenderer cellRenderer = (ModificationRenderer) list.getCellRenderer();
                    Component renderComp = cellRenderer.getListCellRendererComponent(list, list.getModel().getElementAt(index), index, false, false);
                    renderComp.setBounds(bounds);

                    Point local = new Point(e.getPoint());

                    if (local.x >= 10 && local.x <= 30)
                    {
                        cellRenderer.sideButton.doClick(0);
                        /*if (modToolTip != null)
                            modToolTip.hide();

                        modToolTip = PopupFactory.getSharedInstance().getPopup(renderComp,
                                new JLabel(cellRenderer.sideButton.getToolTipText()),
                                e.getLocationOnScreen().x + 1, e.getLocationOnScreen().y - 10);
                        modToolTip.show();

                        if (modToolTipTimer != null)
                            modToolTipTimer.stop();

                        modToolTipTimer = new Timer(500, e1 -> modToolTip.hide());
                        modToolTipTimer.setRepeats(false);
                        modToolTipTimer.start();*/
                    }
                }
            }
        });

        setViewportView(list);
    }

    Modification getSelectedMod() {
        return list.getSelectedValue();
    }

    int getSelectedModIndex() {
        return modifications.indexOf(list.getSelectedValue());
    }

    public void setFilterType(FilterType type) {
        filterType = type;
        reRenderElements();
    }

    public void reRenderElements() {
        reRenderElements(filterType);
    }

    public void reRenderElements(FilterType filter) {
        listModel.removeAllElements();

        ArrayList<Modification> sortedMods;

        switch (filter) {
            case CLIENT -> sortedMods = new ArrayList<>(modifications.stream().filter(modification -> modification.getSide() == Modification.ESide.CLIENT).toList());
            case SERVER -> sortedMods = new ArrayList<>(modifications.stream().filter(modification -> modification.getSide() == Modification.ESide.SERVER).toList());
            default -> sortedMods = new ArrayList<>(modifications);
        }

        if (filter != FilterType.ALL)
            sortedMods.sort(Comparator.comparingInt(Modification::getLoadingPriority));

        sortedMods.forEach(this::addMod);

        if (lastSelectedElement != null)
            list.setSelectedValue(lastSelectedElement,true);

        list.updateUI();
    }

    public void addMod(Path path, Modification.ESide side, Modification.EType type, int priority) {
        this.addMod(new Modification(path,side,type,priority));
    }

    public void addMod(Path path, Modification.ESide side, Modification.EType type) {
        this.addMod(new Modification(path,side,type));
    }

    public void addMod(Modification modification) {
        if (!listModel.isEmpty())
            listModel.addElement(new Modification());

        if (!modifications.contains(modification))
            modifications.add(modification);

        listModel.addElement(modification);
    }

    public void removeSelectedMod() {
        removeMod(getSelectedModIndex());
    }

    public void removeMod(int index) {
        if (modifications.isEmpty() || !(index >= 0 && index < modifications.size())) return;

        DarkButton yesButton = new DarkButton("Yes");
        yesButton.addActionListener(e1 -> {
            Modification mod = modifications.get(index);

            switch (mod.getSide()) {
                case SERVER -> SettingsManager.removeServerMod(mod.getName());
                case CLIENT -> SettingsManager.removeClientMod(mod.getName());
            }

            modifications.remove(index);
            lastSelectedElement = null;
            reRenderElements();
            JOptionPane.getRootFrame().dispose();
        });
        DarkButton noButton = new DarkButton("No");
        noButton.addActionListener(e -> JOptionPane.getRootFrame().dispose());

        JOptionPane.showOptionDialog(null,
                new DarkLabel("Are you sure you want to remove the mod?"),
                "¯\\_(ツ)_/¯",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null,new Object[] {yesButton,noButton}, null);
    }

    public enum FilterType {
        ALL,
        CLIENT,
        SERVER
    }
}
