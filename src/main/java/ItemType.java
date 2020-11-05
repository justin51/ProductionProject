import java.util.stream.Stream;

public enum ItemType {
    AUDIO("AU"),
    VISUAL("VI"),
    AUDIOMOBILE("AM"),
    VISUAL_MOBILE("VM");

    public String code;
    ItemType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public static Stream<ItemType> stream() {
        return Stream.of(ItemType.values());
    }

    public static ItemType forName(String name) {
        for(ItemType t : ItemType.values()) {
            if(t.name().equals(name))
                return t;
        }
        return null;
    }
}