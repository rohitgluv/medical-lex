package com.irohitgoyal.medicalexicon;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by ROHIT on 3/5/2016.
 */
public class CustomLinkMovementMethod extends LinkMovementMethod
{
    private static CustomLinkMovementMethod s_linkMovementMethod = new CustomLinkMovementMethod();

    public static MovementMethod getInstance()
    {
        return s_linkMovementMethod;
    }

    public boolean onTouchEvent(TextView paramTextView, Spannable paramSpannable, MotionEvent paramMotionEvent)
    {
        if (paramMotionEvent.getAction() == 1)
        {
            int i = (int)paramMotionEvent.getX();
            int j = (int)paramMotionEvent.getY();
            int k = i - paramTextView.getTotalPaddingLeft();
            int m = j - paramTextView.getTotalPaddingTop();
            int n = k + paramTextView.getScrollX();
            int i1 = m + paramTextView.getScrollY();
            Layout localLayout = paramTextView.getLayout();
            int i2 = localLayout.getOffsetForHorizontal(localLayout.getLineForVertical(i1), n);
            URLSpan[] arrayOfURLSpan = (URLSpan[])paramSpannable.getSpans(i2, i2, URLSpan.class);
            if (arrayOfURLSpan.length != 0)
            {
                String str = arrayOfURLSpan[0].getURL().replace("\\\"", "");
                Context localContext = paramTextView.getContext();
                if ((localContext instanceof DetailActivity))
                {
                    DetailActivity localDetailActivity = (DetailActivity)paramTextView.getContext();
                    Intent localIntent = new Intent(localDetailActivity, DetailActivity.class);
                    localIntent.putExtra("url", str);
                    localDetailActivity.startActivity(localIntent);
                    return true;
                }
                if ((localContext instanceof TitlesListActivity))
                {
                    ((TitlesListActivity)paramTextView.getContext()).setItemUrl(str);
                    return true;
                }
            }
        }
        return super.onTouchEvent(paramTextView, paramSpannable, paramMotionEvent);
    }
}
