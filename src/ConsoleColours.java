public class ConsoleColours {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    public enum ChannelValues {
        RESET,
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        PURPLE,
        CYAN,
        WHITE
    }

    public static String ANSIfromChannel(int channel) {
        ChannelValues channelval = ChannelValues.values()[channel];
        return switch (channelval) {
            case RESET -> RESET;
            case BLACK -> BLACK;
            case RED -> RED;
            case GREEN -> GREEN;
            case YELLOW -> YELLOW;
            case BLUE -> BLUE;
            case PURPLE -> PURPLE;
            case CYAN -> CYAN;
            case WHITE -> WHITE;
            default -> WHITE;
        };
    }

    public static ChannelValues RGBtoANSI(int red, int green, int blue) {
        if (red > 180 && green < 60 && blue < 60) {
            return ChannelValues.RED;
        }
        if (green > 180 && red < 60 && blue < 60) {
            return ChannelValues.GREEN;
        }
        if (blue > 180 && green < 60 && red < 60) {
            return ChannelValues.BLUE;
        }
        if (blue < 60 && green < 60 && red < 60) {
            return ChannelValues.BLACK;
        }

        if (red > 180 && green > 180 && blue < 60) {
            return ChannelValues.YELLOW;
        }
        if (red > 180 && blue > 180 && green < 60) {
            return ChannelValues.PURPLE;
        }
        if (blue > 180 && green > 180 && red < 60) {
            return ChannelValues.CYAN;
        }

        return ChannelValues.WHITE;
    }
}