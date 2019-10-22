package com.irohitgoyal.medicalexicon;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import android.util.Log;

import org.xml.sax.XMLReader;

import java.util.Stack;

/**
 * Created by ROHIT on 3/5/2016.
 */
public class LiTagHandler implements Html.TagHandler
        {
private static final BulletSpan bullet = new BulletSpan(10);
private static final int indent = 10;
private static final int listItemIndent = 20;
        Stack<String> lists = new Stack();
        Stack<Integer> olNextIndex = new Stack();

private static void end(Editable paramEditable, Class<?> paramClass, Object... paramVarArgs)
        {
        int i = paramEditable.length();
        Object localObject = getLast(paramEditable, paramClass);
        int j = paramEditable.getSpanStart(localObject);
        paramEditable.removeSpan(localObject);
        if (j != i)
        {
        int k = paramVarArgs.length;
        for (int m = 0; m < k; m++) {
        paramEditable.setSpan(paramVarArgs[m], j, i, 33);
        }
        }
        }

private static Object getLast(Spanned paramSpanned, Class<?> paramClass)
        {
        Object[] arrayOfObject = paramSpanned.getSpans(0, paramSpanned.length(), paramClass);
        if (arrayOfObject.length == 0) {
        return null;
        }
        return arrayOfObject[(-1 + arrayOfObject.length)];
        }

private static void start(Editable paramEditable, Object paramObject)
        {
        int i = paramEditable.length();
        paramEditable.setSpan(paramObject, i, i, 17);
        }

public void handleTag(boolean var1, String var2, Editable var3, XMLReader var4) {
    if(var2.equalsIgnoreCase("ul")) {
        if(var1) {
            this.lists.push(var2);
        } else {
            this.lists.pop();
        }
    } else if(var2.equalsIgnoreCase("ol")) {
        if(var1) {
            this.lists.push(var2);
            ((Integer)this.olNextIndex.push(Integer.valueOf(1))).toString();
        } else {
            this.lists.pop();
            ((Integer)this.olNextIndex.pop()).toString();
        }
    } else if(var2.equalsIgnoreCase("li")) {
        if(var1) {
            if(var3.length() > 0 && var3.charAt(var3.length() - 1) != '\n') {
                var3.append("\n");
            }

            var2 = (String)this.lists.peek();
            if(var2.equalsIgnoreCase("ol")) {
                start(var3, new LiTagHandler.Ol());
                var3.append(((Integer)this.olNextIndex.peek()).toString()).append(". ");
                this.olNextIndex.push(Integer.valueOf(((Integer)this.olNextIndex.pop()).intValue() + 1));
            } else if(var2.equalsIgnoreCase("ul")) {
                start(var3, new LiTagHandler.Ul());
            }
        } else {
            int var5;
            int var6;
            if(((String)this.lists.peek()).equalsIgnoreCase("ul")) {
                if(var3.charAt(var3.length() - 1) != 10) {
                    var3.append("\n");
                }

                var5 = 10;
                if(this.lists.size() > 1) {
                    var6 = 10 - bullet.getLeadingMargin(true);
                    var5 = var6;
                    if(this.lists.size() > 2) {
                        var5 = var6 - (this.lists.size() - 2) * 20;
                    }
                }

                BulletSpan var7 = new BulletSpan(var5);
                end(var3, LiTagHandler.Ul.class, new Object[]{new LeadingMarginSpan.Standard((this.lists.size() - 1) * 20), var7});
            } else if(((String)this.lists.peek()).equalsIgnoreCase("ol")) {
                if(var3.charAt(var3.length() - 1) != 10) {
                    var3.append("\n");
                }

                var6 = (this.lists.size() - 1) * 20;
                var5 = var6;
                if(this.lists.size() > 2) {
                    var5 = var6 - (this.lists.size() - 2) * 20;
                }

                end(var3, LiTagHandler.Ol.class, new Object[]{new LeadingMarginSpan.Standard(var5)});
            }
        }
    } else if(var1) {
        Log.d("TagHandler", "Found an unsupported tag " + var2);
    }

}
        class Ol {

        }

        class Ul {

        }
}
