package bad.code.format.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Use this annotation when indicating a class is not instantiable. Contains a private constructor that
 * throws a {@link RuntimeException} if using that constructor.
 *
 * @author Mr. GodDavid
 * @since 4/19/2026
 */
@Retention(RetentionPolicy.SOURCE)
public @interface UninstantiableClass {

    enum Reason {
        UTILITY_CLASS;
    }

    Reason[] reason();
}
