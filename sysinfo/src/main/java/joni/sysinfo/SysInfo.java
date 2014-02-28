package joni.sysinfo;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Pattern;

/**
 * @author Jonatan Ivanov
 */
public class SysInfo {
    private static final String ALL = "all";
    private static final String KEYS = "keys";

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Map properties = getSystemProperties();

        if (args.length == 0) {
            System.out.println(getHelp());
        }
        else if (ALL.equalsIgnoreCase(args[0])) {
            printProps(properties);
        }
        else if (KEYS.equalsIgnoreCase(args[0])) {
            printKeys(properties);
        }
        else {
            printProps(properties, args);
        }
    }

    @SuppressWarnings("rawtypes")
    private static void printProps(final Map properties) {
        for (Object key : properties.keySet()) {
            System.out.println(key + ": " + properties.get(key));
        }
    }

    @SuppressWarnings("rawtypes")
    private static void printKeys(final Map properties) {
        for (Object key : properties.keySet()) {
            System.out.println(key);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void printProps(final Map properties, final String... keys) {
        Map props = new ConcurrentSkipListMap(String.CASE_INSENSITIVE_ORDER);
        props.putAll(properties);

        for (int i = 0; i < keys.length; i++) {
            for (Object propKey : props.keySet()) {
                if (keys[i] != null && propKey != null && containsIgnoreCase(String.valueOf(propKey), keys[i])) {
                    System.out.println(propKey + ": " + props.get(propKey));
                    props.remove(propKey);
                }
            }
        }
    }

    private static boolean containsIgnoreCase(String thiz, String that) {
        return Pattern.compile(Pattern.quote(that), Pattern.CASE_INSENSITIVE).matcher(thiz).find();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Map getSystemProperties() {
        Map propMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);

        propMap.putAll(System.getProperties());
        propMap.putAll(System.getenv());

        propMap.put("runtime.availableProcessors", Runtime.getRuntime().availableProcessors());
        propMap.put("runtime.freeMemory", Runtime.getRuntime().freeMemory());
        propMap.put("runtime.maxMemory", Runtime.getRuntime().maxMemory());
        propMap.put("runtime.totalMemory", Runtime.getRuntime().totalMemory());

        propMap.put("system.currentTimeMillis", System.currentTimeMillis());
        propMap.put("system.nanoTime", System.nanoTime());

        return propMap;
    }

    private static String getHelp() {
        StringBuilder sb = new StringBuilder();

        sb.append("parameters:\n  ");
        sb.append(ALL + ": all properties\n  ");
        sb.append(KEYS + ": only property keys\n  ");
        sb.append("any others (even more than one)" + ": those properties which contain the given string(s) (ignorecase)\n");

        return sb.toString();
    }
}
