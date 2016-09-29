package repairhebsub;

/**
 *
 * @author Zvika3
 */
public class FixSubLine {

    /**
     * Remove all tag: <>
     */
    public static String removeTag(String line) {
        String[] stringToRemove = {"</ I>", "</I>", "</ i>", "</i>", "<I>", "<i>", "< I>", "< i>"};
        for (int i = 0; i < stringToRemove.length; i++) {
            line = line.replaceAll(stringToRemove[i], "");
        }
        return line;
    }

    /**
     * Put the string in the end of the line
     */
    public static String putInTheEnd(String line) {
        String[] StringToReplace = {"\" ...", "\"..."};
        String[] StringToPut = {"...\"", "...\""};
        for (int i = 0; i < StringToReplace.length; i++) {
            if (line.endsWith(StringToReplace[i])) {
                line = StringToPut[i]+line.substring(0, line.length() - StringToReplace[i].length());
            }
        }

        String[] StringToputInTheEnd = {"...", "..", "!?", "?!", "!", ".", ",", "?", "\""};
        for (int i = 0; i < StringToputInTheEnd.length; i++) {
            if (line.endsWith(StringToputInTheEnd[i])) {
                line = StringToputInTheEnd[i] + line.substring(0, line.length() - StringToputInTheEnd[i].length());
            }
        }
        return line;
    }

    /**
     * Fix the time line "00: 00: 00,087 -> 00: 00: 01,583" to "00:00:00,087 -->
     * 00:00:01,583"
     */
    public static String fixTime(String line) {
        if (line.contains("->") && line.charAt(0) >= '0' && line.charAt(0) <= '9' && line.charAt(line.length() - 1) >= '0' && line.charAt(line.length() - 1) <= '9') {
            line = line.replaceAll(": ", ":");
            return line.replaceAll(" -> ", " --> ");
        }
        return line;
    }

    /**
     * return NULL if not need to fix. examples: ".aaaa" -> to "aaaa.", ",aaaa"
     */
    public static String fixSubLine(String line) {
        if (line == null || line.length() < 2) {
            return null;
        }

        line = line.trim();
        if (line == null || line.length() < 2) {
            return null;
        }

        line = removeTag(line);

        //remove two space:
        line = line.replaceAll("  ", " ");

        line = line.trim();
        if (line == null || line.length() < 2) {
            return null;
        }

        line = putInTheEnd(line);

        line = fixTime(line);
        


        return line;
    }
}
