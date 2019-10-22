package com.irohitgoyal.medicalexicon;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class ContentCursorAdapter extends CursorAdapter
{
    private final int iconFavoriteOff = R.drawable.ic_tab_bookmarks_selected;
    private final int iconFavoriteOn = R.drawable.ic_tab_bookmarks_unselected;
    private LayoutInflater mInflater;

    public ContentCursorAdapter(Context paramContext)
    {
        super(paramContext, null, 0);
        this.mInflater = ((LayoutInflater)paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
        TextView localTextView = (TextView)paramView.findViewById(R.id.textTitle);
        ImageView localImageView = (ImageView)paramView.findViewById(R.id.imageViewTitleList);
        Title localTitle = new Title(paramCursor);
        String str = ((TitlesListActivity)paramContext).getTiltesListFragemt().getSearchFilter();
        localTextView.setText(Html.fromHtml(TextUtils.selectFiltredSubstring(localTitle.names, str)));
        boolean var6;
        if(localTitle.isFavorite > 0) {
            var6 = true;
        } else {
            var6 = false;
        }

        int var5;
        if(Boolean.valueOf(var6).booleanValue()) {
            var5 = iconFavoriteOn;
        } else {
            var5 = iconFavoriteOff;
        }

        localImageView.setImageResource(var5);
        localImageView.setTag(Integer.valueOf(localTitle.id));
        }

    public View newView(final Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
        View localView = this.mInflater.inflate(R.layout.titles_list_item, paramViewGroup, false);
        ((ImageView)localView.findViewById(R.id.imageViewTitleList)).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramAnonymousView)
            {
                String str = paramAnonymousView.getTag().toString();
                if ((str != null) && (str.length() > 0))
                {
                    int i = Integer.parseInt(str);
                    if (i > 0)
                    {
                        boolean bool = Favourite.toggleFavourite(paramContext, i);
                        ImageView  localImageView = (ImageView)paramAnonymousView.findViewById(R.id.imageViewTitleList);
                        if (!bool) {
                            int varOff=iconFavoriteOff;
                            localImageView.setImageResource(varOff);
                            Toast.makeText(paramContext, paramContext.getString(R.string.deletefavorite), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int varOn=iconFavoriteOn;
                        localImageView.setImageResource(varOn);
                        Toast.makeText(paramContext, paramContext.getString(R.string.addfavorite), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return localView;
    }
}
