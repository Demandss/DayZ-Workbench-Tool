package su.demands.elements;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import su.demands.darkswing.DarkSwingColors;
import su.demands.darkswing.elements.button.DarkButton;
import su.demands.darkswing.elements.label.DarkLabel;
import su.demands.darkswing.elements.panel.DarkPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.Objects;

@Getter @Setter
public class Modification {

    protected ESide side;
    protected EType type;
    protected String name;
    protected Path path;
    protected int loadingPriority = -1;

    public Modification(Path path, ESide side, EType type, int loadingPriority) {
        this.side = side;
        this.type = type;
        this.name = path.getFileName().toString().replace("@","");
        this.path = path;
        this.loadingPriority = loadingPriority;
    }

    public Modification(Path path, ESide side, EType type) {
        this(path,side,type,-1);
    }

    public Modification(Path path, ESide side, int loadingPriority) {
        this(path,side,path.getFileName().toString().contains("@") ? EType.WORKSHOP : EType.LOCAL,loadingPriority);
    }

    public Modification() {
        this.name = "SEPARATOR";
    }

    public boolean isSeparator() {
        return Objects.equals(this.name, "SEPARATOR");
    }

    public void increaseLoadingPriority() {
        loadingPriority += 1;
    }

    public void decreaseLoadingPriority() {
        if (loadingPriority == 0) return;

        loadingPriority -= 1;
    }

    @Getter
    public enum ESide {
        CLIENT("mod_client_side.png"),
        SERVER("mod_server_side.png");

        private final Image icon;

        @SneakyThrows
        ESide(String iconName) {
            icon = ImageIO.read(getClass().getResource("/assets/images/" + iconName));
        }
    }

    @Getter
    public enum EType {
        LOCAL(null),
        WORKSHOP("workshop.png");

        private final Image icon;

        @SneakyThrows
        EType(String iconName) {
            if (iconName == null) {
                icon = null;
                return;
            }

            icon = ImageIO.read(getClass().getResource("/assets/images/" + iconName));
        }
    }
}
