package com.irohitgoyal.medicalexicon;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class TextUtils
{
    public static String selectFiltredSubstring(String paramString1, String paramString2)
    {
        return selectFiltredSubstring_(paramString1, paramString2).replace("<\u2022>", "<font color=\"red\">").replace("</\u2022>", "</font>");
    }


    private static String selectFiltredSubstring_(String str, String filtr) {
        if(filtr != null && !filtr.isEmpty()) {
            String var3 = str.toLowerCase();
            String var4 = "";
            String var5 = filtr.toLowerCase().trim();
            String[] var6 = var5.split(" ");
            int var7;
            int var8;
            if(var6.length > 1) {
                var8 = var6.length;
                var7 = 0;
                filtr = str;

                while(true) {
                    str = filtr;
                    if(var7 >= var8) {
                        break;
                    }

                    filtr = selectFiltredSubstring_(filtr, var6[var7].trim());
                    ++var7;
                }
            } else {
                var8 = var5.length();
                var7 = var3.indexOf(var5);
                str = str;
                String var2 = var4;

                for(filtr = var3; var7 != -1; var7 = filtr.indexOf(var5)) {
                    var2 = var2 + str.substring(0, var7) + "<\u2022>";
                    var2 = var2 + str.substring(var7, var7 + var8);
                    var2 = var2 + "</\u2022>";
                    str = str.substring(var7 + var8);
                    filtr = filtr.substring(var7 + var8);
                }

                str = var2 + str;
            }
        }

        return str;
    }

    public static String strJoin(String[] paramArrayOfString, String paramString)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        int i = 0;
        int j = paramArrayOfString.length;
        while (i < j)
        {
            if (i > 0) {
                localStringBuilder.append(paramString);
            }
            localStringBuilder.append(paramArrayOfString[i]);
            i++;
        }
        return localStringBuilder.toString();
    }
}


