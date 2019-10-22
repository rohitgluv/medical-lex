package com.irohitgoyal.medicalexicon;

/**
 * Created by ROHIT on 3/5/2016.
 */
public class ExtendException {
    public static String stackTraceToString(Throwable paramThrowable)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
        int i = arrayOfStackTraceElement.length;
        for (int j = 0; j < i; j++)
        {
            localStringBuilder.append(arrayOfStackTraceElement[j].toString());
            localStringBuilder.append("\n");
        }
        return localStringBuilder.toString();
    }
}
