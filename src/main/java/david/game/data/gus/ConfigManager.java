package david.game.data.gus;

import bad.code.format.annotation.DebugMethod;
import bad.code.format.annotation.SingletonClass;
import bad.code.format.annotation.UninstantiableClass;
import david.game.core.Game;

import java.io.*;
import java.util.*;

/**
 * Parse Json files.
 * Web:
 * <a href = https://vajithc.medium.com/parsing-json-without-libraries-build-your-own-json-reader-in-java-1db8e6165039>
 * here</a>
 * <p>
 * This class configs the config.json file, which stores all the settings and stats of game characters and items in this
 * game.
 * <p>
 * This class parse the JSON files and allow user to enter the name of the character (in getCharacter()) and the name
 * of the item (in getItem()).
 *
 * @author Mr. GodDavid (class file)
 * @since 5/1/2026
 */
@SingletonClass
public final class ConfigManager {

    /**
     * Parser of GUS syntax.
     *
     * @author Mr. GodDavid
     * @since 5/2/2026
     */
    @UninstantiableClass(reason = UninstantiableClass.Reason.UTILITY_CLASS)
    static final class GUSParser {

        private static String syntaxErrorReason;

        private GUSParser() {
            throw new RuntimeException("GUSParser cannot be instantiated");
        }

        /**
         * Parser of the string in GUS Syntax : "[GSS, n] # [GSS, m]" -> returns the number pair: {GSS * n, GSS * m}.
         * <p>
         * GUS Syntax for infinite number pair:
         * <pre><code>
         *     // a, b, c, d, e, ... are numbers.
         *     "[GSS, a] # [GSS, b] # [GSS, c] # [GSS, d] # [GSS, e]# [GSS, f] # ..."
         * </code></pre>
         * <p>
         * Returned number pair:
         * <pre><code>
         *     // a, b, c, d, e, ... are numbers.
         *     double[]{GSS * a, GSS * b, GSS * c, GSS * d, GSS * e, ...};
         * </code></pre>
         *
         * @param gus raw syntax string.
         * @return number pair after parsing gus raw string.
         */
        public static double[] parseNumberPair(String gus) {
            if (gus.equals("NULL"))
                return new double[]{-999, -999};
            if (containSyntaxError(gus))
                throw new GameUnitSyntaxException(syntaxErrorReason);

            String formatted = gus.replace("[", "").replace("]", "");
            String[] dataPairs = formatted.split(ObjectMapper.DATA_DELIMITER);
            double[] result = new double[dataPairs.length];
            for (int i = 0; i < dataPairs.length; i++) {
                String[] data = dataPairs[i].split(ObjectMapper.GUS_DATA_DELIMITER);
                result[i] = (data[0].equals("NULL")) ? 1.0 : Game.UNIT_LENGTH * Double.parseDouble(data[1]);
            }
            return result;
        }

        private static boolean containSyntaxError(String gus) {
            syntaxErrorReason = "";
            if (!gus.contains(ObjectMapper.DATA_DELIMITER)) {
                syntaxErrorReason = "GUS Syntax Error: GUS String should contains delimiter \"#\"!";
                return true;
            }
            String[] tokens = gus.split(ObjectMapper.DATA_DELIMITER);
            for (String token : tokens) {
                token = token.trim();
                if (!token.contains(ObjectMapper.GUS_DATA_DELIMITER)) {
                    syntaxErrorReason = "GUS Syntax Error: At token: " + token + "\n";
                    syntaxErrorReason += "GUS Syntax Error: GUS String should contains data delimiter \",\"!";
                    return true;
                }
                if (!token.startsWith("[")) {
                    syntaxErrorReason = "GUS Syntax Error: At token: " + token + "\n";
                    syntaxErrorReason += "GUS Syntax Error: GUS String should start with \"[\"!";
                    return true;
                }
                if (token.charAt(token.length() - 1) != ']') {
                    syntaxErrorReason = "GUS Syntax Error: At token: " + token + "\n";
                    syntaxErrorReason += "GUS Syntax Error: GUS String should end with \"]\"!";
                    return true;
                }

                String[] innerData = token.substring(1, token.length() - 1).split(ObjectMapper.GUS_DATA_DELIMITER);
                for (int i = 0; i < innerData.length; i++) {
                    innerData[i] = innerData[i].trim();
                }
                // INVALID SYNTAX: SUB GUS DATA PAIR MUST CONTAINS TWO TOKENS.
                if (innerData.length != 2) {
                    syntaxErrorReason = "GUS Syntax Error: At token: " + token + "\n";
                    syntaxErrorReason += "GUS Syntax Error: GUS Data pair should contains only two data number!";
                    return true;
                }
                // INVALID SYNTAX: SUB DATA PAIR CANNOT CONTAIN EMPTY TOKEN.
                for (int i = 0; i < innerData.length; i++) {
                    if (innerData[i].isEmpty()) {
                        syntaxErrorReason = "GUS Syntax Error: At token number " + i + ": " + token + "\n";
                        syntaxErrorReason += "GUS Syntax Error: Token cannot be empty!";
                        return true;
                    }
                }
                // VALID SYNTAX: NULL TELLS PROGRAM TO REPLACE 1.
                if (innerData[0].equals("NULL")) {
                    return false;
                }
                // INVALID SYNTAX: FIRST TOKEN OF DATA PAIR MUST BE "GSS".
                else if (!innerData[0].equals("GSS")) {
                    syntaxErrorReason = "GUS Syntax Error: At token: " + token + "\n";
                    syntaxErrorReason += "GUS Syntax Error: Invalid symbol \"" + innerData[0] + "\"! Do you mean \"GSS\"?";
                    return true;
                }
                // INVALID SYNTAX: SECOND TOKEN OF SUB DATA PAIR MUST BE A NUMBER.
                if (!isNumeric(innerData[1])) {
                    syntaxErrorReason = "GUS Syntax Error: Invalid data number: " + innerData[1] + "!";
                    return true;
                }
            }
            return false;
        }

        private static boolean isNumeric(String str) {
            if (str == null || str.isEmpty()) {
                return false;
            }
            int decimalCount = 0;
            boolean hasDigits = false;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (ch == '.') {
                    decimalCount++;
                    if (decimalCount > 1) {
                        return false;
                    }
                } else if (Character.isDigit(ch)) {
                    hasDigits = true;
                } else {
                    return false;
                }
            }
            return hasDigits && decimalCount <= 1;
        }
    }

    private static final ItemData NULL_ITEM_DATA = new ItemData();
    private static final CharacterData NULL_CHARACTER_DATA = new CharacterData();

    private static ConfigManager instance;

    private final File configFile;
    private Object root;
    private final Map<String, Map<String, Object>> itemIndex;
    private final Map<String, Map<String, Object>> characterIndex;

    private ConfigManager() {
        this.configFile = new File("src/main/resources/assets/config.json");
        this.itemIndex = new HashMap<>();
        this.characterIndex = new HashMap<>();
        load();
    }

    /**
     * Get the single instance of this class. The class first construct itself and return its instance follow on when
     * calling the constructor in this game project.
     *
     * @return the only instance of this class when call this method.
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Load the String JSON file and store it in root for later data extraction. This method also contains debugging log
     * message such as starting loading file and finished laoding/data extraction. Next, this method build look-up table
     * for item and game character data struct from JSON file.
     */
    private void load() {
        System.out.println("[MyGame V1 StatPrinter]:\tLoading config file...");
        root = parseJson(readFiledAsString());

        buildIndex("item_list", "name", itemIndex);
        buildIndex("character_list", "name", characterIndex);

        System.out.println("[MyGame V1 StatPrinter]:\tLoading config file complete!");
    }

    private void buildIndex(String listKey, String keyField, Map<String, Map<String, Object>> index) {
        for (Map<String, Object> obj : getList(listKey)) {
            index.put((String) obj.get(keyField), obj);
        }
    }

    public ItemData getItem(String name) {
        Map<String, Object> raw = itemIndex.get(name);
        if (raw == null) return NULL_ITEM_DATA;
        return ObjectMapper.map(raw, ItemData.class);
    }

    public CharacterData getCharacter(String name) {
        Map<String, Object> raw = characterIndex.get(name);
        if (raw == null) return NULL_CHARACTER_DATA;
        return ObjectMapper.map(raw, CharacterData.class);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> getList(String listKey) {
        if (root instanceof Map<?, ?> map) {
            Object obj = map.get(listKey);
            if (obj instanceof List<?> list) {
                return (List<Map<String, Object>>) list;
            }
        }
        throw new IllegalArgumentException("Invalid list key: " + listKey);
    }

    private String readFiledAsString() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }

    private Object parseJson(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseJsonObject(json);
        } else if (json.startsWith("[")) {
            return parseJsonArray(json);
        } else if (json.startsWith("\"") && json.endsWith("\"")) {
            return json.substring(1, json.length() - 1); // Remove surrounding quotes
        } else if (json.equalsIgnoreCase("true") || json.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(json);
        } else if (json.matches("-?\\d+(\\.\\d+)?")) {
            return json.contains(".") ? Double.parseDouble(json) : Integer.parseInt(json);
        } else {
            throw new IllegalArgumentException("Invalid JSON value: " + json);
        }
    }

    private Map<String, Object> parseJsonObject(String json) {
        Map<String, Object> map = new LinkedHashMap<>();
        json = json.substring(1, json.length() - 1).trim(); // Remove curly braces
        String[] entries = splitJsonEntries(json);
        for (String entry : entries) {
            String[] keyValue = entry.split(":", 2);
            String key = parseJson(keyValue[0]).toString();
            Object value = parseJson(keyValue[1]);
            map.put(key, value);
        }
        return map;
    }

    private List<Object> parseJsonArray(String json) {
        List<Object> list = new ArrayList<>();
        json = json.substring(1, json.length() - 1).trim(); // Remove square brackets
        String[] items = splitJsonEntries(json);
        for (String item : items) {
            list.add(parseJson(item));
        }
        return list;
    }

    private String[] splitJsonEntries(String json) {
        List<String> entries = new ArrayList<>();
        int bracketCount = 0, braceCount = 0;
        boolean inQuotes = false;
        StringBuilder entry = new StringBuilder();

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                } else if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                }
            }

            if (c == ',' && !inQuotes && braceCount == 0 && bracketCount == 0) {
                entries.add(entry.toString().trim());
                entry.setLength(0);
            } else {
                entry.append(c);
            }
        }

        if (!entry.isEmpty()) {
            entries.add(entry.toString().trim());
        }

        return entries.toArray(new String[0]);
    }

    @DebugMethod(purpose = DebugMethod.DebugPurpose.PRINT_OBJECT)
    public static void printJson(Object json, String indent) {
        if (json instanceof Map<?, ?> map) {
            System.out.println(indent + "{");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(indent + " \"" + entry.getKey() + "\": ");
                printJson(entry.getValue(), indent + "  ");
            }
            System.out.println(indent + "}");
        } else if (json instanceof List<?> list) {
            System.out.println(indent + "[");
            for (Object item : list) {
                printJson(item, indent + "  ");
            }
            System.out.println(indent + "]");
        } else {
            System.out.println(indent + json);
        }
    }
}
