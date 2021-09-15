package stream.regex;

public class RegexFactory {
    private static final String dateGroupName = "on";
    private static final String dateRegex = "\\d{2}/\\d{2}/\\d{2,4}";
    private static final String numberGroupName = "price";
    private static final String numberRegex = "\\d+";

    public static String createSwipeRegex() {
        return String.format(".*(?<%s>%s).*?(?<%s>%s).*",
                dateGroupName, dateRegex, numberGroupName, numberRegex);
    }

    public static String createStockRegex() {
        return String.format(".*(?<%s>%s).*?(?<%s>%s).*?(?<%s>%s).*",
                dateGroupName, dateRegex, "settle", dateRegex, numberGroupName, numberRegex);
    }
}
