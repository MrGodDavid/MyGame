package bad.code.format.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to label methods in the game that has purpose of printing debug messages.
 *
 * @author Mr. GodDavid
 * @since 4/26/2026
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface DebugMethod {

    @SuppressWarnings("UnnecessarySemicolon")
    enum DebugPurpose {
        /**
         * Indicates that the purpose of debugging method prints debugging messages.
         */
        PRINT_DEBUG_MESSAGE;
    }

    /**
     * Input the debugging purpose of method when using this annotation.
     *
     * @return debugging purpose of this method.
     */
    DebugPurpose purpose();
}
