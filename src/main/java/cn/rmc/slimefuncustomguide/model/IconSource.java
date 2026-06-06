package cn.rmc.slimefuncustomguide.model;

import java.util.Objects;

public class IconSource {
    private final IconType type;
    private final String id;

    public IconSource(IconType type, String id) {
        this.type = Objects.requireNonNull(type, "type");
        this.id = Objects.requireNonNull(id, "id");
    }

    public IconType getType() { return type; }
    public String getId() { return id; }
}
