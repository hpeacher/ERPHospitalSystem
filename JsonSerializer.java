import java.io.File;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonSerializer {

    public static String toJson(Object obj) {
        return serializeObject(obj, 0);
    }

    public static <T> T fromJson(File file, Class<T> clazz) {
        String json = file.toString();
        return deserializeObject(json, clazz);
    }

    private static String serializeObject(Object obj, int indentLevel) {
        if (obj == null)
            return "null";

        StringBuilder json = new StringBuilder();
        Class<?> clazz = obj.getClass();
        String indent = "  ".repeat(indentLevel);

        if (obj instanceof String || clazz.isPrimitive() || obj instanceof Number || obj instanceof Boolean) {
            return "\"" + escape(obj.toString()) + "\"";
        }

        if (obj instanceof List<?>) {
            json.append("[\n");
            List<?> list = (List<?>) obj;
            for (int i = 0; i < list.size(); i++) {
                json.append(indent).append("  ").append(serializeObject(list.get(i), indentLevel + 1));
                if (i < list.size() - 1)
                    json.append(",");
                json.append("\n");
            }
            json.append(indent).append("]");
            return json.toString();
        }

        json.append("{\n");
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Object value = fields[i].get(obj);
                json.append(indent).append("  \"").append(fields[i].getName()).append("\": ")
                        .append(serializeObject(value, indentLevel + 1));
                if (i < fields.length - 1)
                    json.append(",");
                json.append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        json.append(indent).append("}");
        return json.toString();
    }

    public static <T> T deserializeObject(String json, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Map<String, String> fieldMap = extractFields(json);

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String rawValue = fieldMap.get(fieldName);

                if (rawValue == null)
                    continue;

                Class<?> type = field.getType();

                if (type == String.class) {
                    field.set(instance, unescape(rawValue));
                } else if (type == int.class || type == Integer.class) {
                    field.set(instance, Integer.parseInt(rawValue));
                } else if (type == boolean.class || type == Boolean.class) {
                    field.set(instance, Boolean.parseBoolean(rawValue));
                } else if (type == double.class || type == Double.class) {
                    field.set(instance, Double.parseDouble(rawValue));
                } else if (List.class.isAssignableFrom(type)) {
                    // Get generic type of the list
                    Type genericType = field.getGenericType();
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) genericType;
                        Class<?> elementType = (Class<?>) pt.getActualTypeArguments()[0];

                        List<?> list = parseList(rawValue, elementType);
                        field.set(instance, list);
                    }
                } else {
                    // Assume nested object
                    field.set(instance, deserializeObject(rawValue, type));
                }
            }

            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> extractFields(String json) {
        Map<String, String> map = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(\\w+)\":\\s*(\"[^\"]*\"|\\{[^}]*}|\\[[^\\]]*]|\\d+|true|false|null)");
        Matcher matcher = pattern.matcher(json);
        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private static String unescape(String value) {
        if (value == null || value.equals("null"))
            return null;
        return value.replace("\\\"", "\"").replace("\\\\", "\\").replaceAll("^\"|\"$", "");
    }

    private static <T> List<T> parseList(String jsonArray, Class<T> elementType) {
        List<T> list = new ArrayList<>();
        Pattern objectPattern = Pattern.compile("\\{[^}]*}");
        Matcher matcher = objectPattern.matcher(jsonArray);

        while (matcher.find()) {
            String itemJson = matcher.group();
            T item = deserializeObject(itemJson, elementType);
            list.add(item);
        }

        return list;
    }
}