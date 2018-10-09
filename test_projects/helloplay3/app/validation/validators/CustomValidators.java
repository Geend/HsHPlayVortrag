package validation.validators;

import play.data.validation.Constraints;
import play.libs.F;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static play.libs.F.Tuple;

public class CustomValidators {


    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = AllUpperCaseValidator.class)
    @play.data.Form.Display(name="constraint.alluppercase", attributes={})
    public @interface AllUpperCase {
        String message() default AllUpperCaseValidator.message;
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
    }


    public static class AllUpperCaseValidator
            extends play.data.validation.Constraints.Validator<String>
            implements ConstraintValidator<AllUpperCase, String> {

        /* Default error message */
        final static public String message = "error.alluppercase";


        public AllUpperCaseValidator() {
        }

        /**
         * Validator init
         * Can be used to initialize the validation based on parameters
         * passed to the annotation.
         */
        public void initialize(AllUpperCase constraintAnnotation) {
        }

        /**
         * The validation itself
         */
        public boolean isValid(String object) {
            if (object == null)
                return false;
            String s = object.toString();
            for (char c : s.toCharArray()) {
                if (Character.isLetter(c) && Character.isLowerCase(c))
                    return false;
            }

            return true;
        }

        @Override
        public F.Tuple<String, Object[]> getErrorMessageKey() {
            return Tuple(message, new Object[]{});
        }


    }

    public static Constraints.Validator<String> alluppercase() {
        return new AllUpperCaseValidator();
    }

}