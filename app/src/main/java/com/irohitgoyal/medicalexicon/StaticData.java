package com.irohitgoyal.medicalexicon;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class StaticData
{
    public static int fontSizeBig;
    public static int fontSizeNormal;
    public static int fontSizeSmall;
    public static Boolean isError = Boolean.valueOf(false);
    public static Boolean isExpertMode;
    public static Boolean isFirstRun = Boolean.valueOf(true);
    public static String packageName = "";
    public static int positionMenuSelected;

    static
    {
        fontSizeNormal = 15;
        fontSizeSmall = 12;
        fontSizeBig = 21;
        isExpertMode = Boolean.valueOf(false);
        positionMenuSelected = 1;
    }
}