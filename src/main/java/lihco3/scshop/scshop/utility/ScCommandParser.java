package lihco3.scshop.scshop.utility;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ScCommandParser {
    public static @Nullable Integer parseCoordinate(Integer start, String arg) {
        // Normal coordinate
        var number = ScCommandParser.parseIntOrNull(arg);
        if(number != null) return number;

        // Relative coordinate
        if(!arg.startsWith("~")) return null;
        if(arg.substring(1).isEmpty()) return start;
        var relativeNumber =  ScCommandParser.parseIntOrNull(arg.substring(1));
        if(relativeNumber == null) return null;
        return start + relativeNumber;
    }

    public static Integer parseIntOrNull(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static @Nullable List<String> parseListOfStrings(String arg) {
        if(arg == null || arg.length() < 2) return null;
        var hasBrackets = arg.startsWith("[") && arg.endsWith("]");
        if(!hasBrackets) return null;

        // Remove the brackets
        var data = arg.substring(1, arg.length() - 1);
        var items = data.split(",");
        List<String> result = new ArrayList<>();

        for (String item : items) {
            result.add(item.trim());
        }

        return result;
    }

    public static @Nullable List<Integer> parseListOfIntegers(String arg) {
        var data = ScCommandParser.parseListOfStrings(arg);
        if (data == null) return null;

        List<Integer> result = new ArrayList<>();
        for (String item : data) {
            var number = ScCommandParser.parseIntOrNull(item);
            if (number == null) return null;
            result.add(number);
        }

        return result;

    }
}
