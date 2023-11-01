package su.demands.elements;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import su.demands.common.ModificationManager;
import su.demands.common.tools.ReferenceTools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

@Getter @Setter
public class Modification {

    protected boolean isEnabled;
    protected ESide side;
    protected EType type;
    protected String name;
    protected Path path;
    protected int loadingPriority = -1;

    public Modification(String text, ESide side, EType type, int loadingPriority) {
        this.side = side;
        this.type = type;

        String[] splittedText = text.split("&");
        this.isEnabled = Boolean.parseBoolean(splittedText[1]);
        this.path = Path.of(splittedText[0]);
        this.name = path.getFileName().toString().replace("@","");
        this.loadingPriority = loadingPriority;
    }

    public Modification(String text, ESide side, EType type) {
        this(text,side,type,-1);
    }

    public Modification(String text, ESide side, int loadingPriority) {
        this(text,side,Path.of(text.split("&")[0]).getFileName().toString().contains("@") ? EType.WORKSHOP : EType.LOCAL,loadingPriority);
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

    void switchSideState() {
        ModificationManager.MODIFICATIONS.remove(this);
        switch (getSide()) {
            case CLIENT -> setSide(ESide.SERVER);
            case SERVER -> setSide(ESide.CLIENT);
        }
        ModificationManager.MODIFICATIONS.add(this);
    }

    void switchEnableState() {
        setEnabled(!isEnabled());
    }

    @SneakyThrows
    Image getEnableStateIcon() {
        return ImageIO.read(getClass().getResource("/assets/images/" + (isEnabled() ? "mod_enabled.png" : "mod_disebled.png") ));
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
