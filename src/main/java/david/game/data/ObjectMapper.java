package david.game.data;

import bad.code.format.annotation.UninstantiableClass;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author Mr. GodDavid
 * @since 5/1/2026
 */
@UninstantiableClass
public final class ObjectMapper {

    private ObjectMapper() {
        throw new IllegalAccessError("You can't instantiate this class.");
    }

    public static <T> T map(Map<String, Object> data, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                Object value = data.get(field.getName());
                if (value == null) continue;

                field.set(instance, convert(value, field.getType()));
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object convert(Object value, Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return ((Number) value).intValue();
        }
        if (type == long.class || type == Long.class) {
            return ((Number) value).longValue();
        }
        if (type == double.class || type == Double.class) {
            return ((Number) value).doubleValue();
        }
        if (type == float.class || type == Float.class) {
            return ((Number) value).floatValue();
        }
        if (type == boolean.class || type == Boolean.class) {
            return ((Boolean) value).booleanValue();
        }
        if (type == String.class) {
            return value.toString();
        }
        return value;
    }
}
