package bad.code.format.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this annotation when indicating that a class is a Singleton class (can only be instantiated once).
 *
 * @author Mr. GodDavid
 * @since 4/19/2026
 */
@Retention(RetentionPolicy.SOURCE)
public @interface SingletonClass {
}
