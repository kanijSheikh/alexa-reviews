// the following class is added to provide more meaningful information in the production environment by adding more debug points.

package com.signify.exception;

public class ExceptionUtility {

    private ExceptionUtility(){}

    public static String getExceptionDetails(Throwable throwable)
    {
        StringBuilder result = new StringBuilder();
        result.append(throwable.toString());
        result.append('\n');
        StackTraceElement[] elements = throwable.getStackTrace();
        for (StackTraceElement element :elements)
        {
            result.append(element);
            result.append('\n');
        }
        return result.toString();

    }
}
