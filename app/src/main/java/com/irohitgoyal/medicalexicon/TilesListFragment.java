package com.irohitgoyal.medicalexicon;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class TilesListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static final String STATE_IS_FAVORITE = "is_favorite";
    private static final String STATE_SEARCH_FILTER = "search_filter";
    private static Callbacks sDummyCallbacks = new Callbacks()
    {
        public void onItemSelected(int paramAnonymousInt) {}
    };
    private Callbacks mCallbacks = sDummyCallbacks;
    public ListView mList;
    View mListContainer;
    boolean mListShown;
    View mProgressContainer;
    private int m_activatedPosition = -1;
    protected ContentCursorAdapter m_caTitleOrganizations;
    private Boolean m_is_favorites = Boolean.valueOf(false);
    private String m_searchFilter = "";

    public String getSearchFilter()
    {
        return this.m_searchFilter;
    }

    public void onAttach(Activity paramActivity)
    {
        super.onAttach(paramActivity);
        if (!(paramActivity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }
        this.mCallbacks = ((Callbacks)paramActivity);
    }

    public void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        this.m_caTitleOrganizations = new ContentCursorAdapter(getActivity());
        setListAdapter(this.m_caTitleOrganizations);
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
    {
        setListShown(false);
        this.m_activatedPosition = 1;
        Uri localUri;
        if ((this.m_searchFilter != null) && (!this.m_searchFilter.isEmpty())) {
            localUri = ContentManager.Titles.CONTENT_SEARCH_URI.buildUpon().appendQueryParameter("filter", this.m_searchFilter).build();
        }
        else if (m_is_favorites.booleanValue()) {
            localUri = ContentManager.Titles.CONTENT_FAVORITES_URI.buildUpon().appendQueryParameter("favorites", m_is_favorites.toString()).build();
        } else {
            localUri = ContentManager.Titles.CONTENT_TITLES_URI;
        }
        return new CursorLoader(getActivity(), localUri, null, null, null, null);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        View localView = paramLayoutInflater.inflate(R.layout.list_frag_content, paramViewGroup, false);
        localView.findViewById(R.id.internalEmpty).setId(R.id.list_item);
        this.mList = ((ListView)localView.findViewById(R.id.list));
        this.mListContainer = localView.findViewById(R.id.listContainer);
        this.mProgressContainer = localView.findViewById(R.id.progressContainer);
        this.mListShown = true;
        return localView;
    }

    public void onDetach()
    {
        super.onDetach();
        this.mCallbacks = sDummyCallbacks;
    }

    public void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong)
    {
        super.onListItemClick(paramListView, paramView, paramInt, paramLong);
        Cursor localCursor = (Cursor)getListAdapter().getItem(paramInt);
        if (localCursor != null)
        {
            this.m_activatedPosition = paramInt;
            int i = localCursor.getInt(localCursor.getColumnIndex("_id"));
            this.mCallbacks.onItemSelected(i);
        }
    }

    public void onLoadFinished(Loader<Cursor> paramLoader, Cursor paramCursor)
    {
        setListShown(true);
        this.m_caTitleOrganizations.changeCursor(paramCursor);
    }

    public void onLoaderReset(Loader<Cursor> paramLoader)
    {
        this.m_caTitleOrganizations.changeCursor(null);
    }

    public void onSaveInstanceState(Bundle paramBundle)
    {
        super.onSaveInstanceState(paramBundle);
        paramBundle.putInt("activated_position", this.m_activatedPosition);
        paramBundle.putString("search_filter", this.m_searchFilter);
        paramBundle.putBoolean("is_favorite", this.m_is_favorites.booleanValue());
    }

    public void onViewCreated(View paramView, Bundle paramBundle)
    {
        super.onViewCreated(paramView, paramBundle);
        if (paramBundle != null)
        {
            this.m_activatedPosition = paramBundle.getInt("activated_position");
            this.m_searchFilter = paramBundle.getString("search_filter");
            this.m_is_favorites = Boolean.valueOf(paramBundle.getBoolean("is_favorite"));
        }
        setListShown(false);
    }

    public void setActivateOnItemClick(boolean paramBoolean)
    {
        ListView localListView = getListView();
        localListView.setChoiceMode(paramBoolean ? 1 : 0);

//        if (paramBoolean) {}
//        for (int i = 1;; i = 0)
//        {
//            localListView.setChoiceMode(i);
//            return;
//        }
    }

    public void setListShown(boolean paramBoolean)
    {
        setListShown(paramBoolean, true);
    }

    public void setListShown(boolean var1, boolean var2) {
        if(this.mProgressContainer != null && this.mListContainer != null && this.mListShown != var1) {
            this.mListShown = var1;
            if(var1) {
                if(var2) {
                    this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.abc_slide_in_bottom));
                    this.mListContainer.startAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.abc_slide_in_top));
                }

                this.mProgressContainer.setVisibility(View.GONE);
                this.mListContainer.setVisibility(View.VISIBLE);
            } else {
                if(var2) {
                    this.mProgressContainer.startAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.abc_slide_in_top));
                    this.mListContainer.startAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.abc_slide_in_bottom));
                }

                this.mProgressContainer.setVisibility(View.VISIBLE);
                this.mListContainer.setVisibility(View.INVISIBLE);
            }
        }

    }


    public void setListShownNoAnimation(boolean paramBoolean)
    {
        setListShown(paramBoolean, false);
    }

    public void setSearchFilter(String paramString)
    {
        this.m_searchFilter = paramString;
        getActivity().getSupportLoaderManager().restartLoader(0, null, this);
    }

    public boolean toggleFavorites()
    {
        m_is_favorites = Boolean.valueOf((!m_is_favorites.booleanValue()));
        m_searchFilter = null;
        ( (TitlesListActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(m_is_favorites.booleanValue());
        getActivity().getSupportLoaderManager().restartLoader(0, null, this);
        getActivity().setTitle(getString(m_is_favorites.booleanValue() ? R.string.Favorites: R.string.app_name));
        return m_is_favorites.booleanValue();
    }

    public interface Callbacks
    {
        void onItemSelected(int paramInt);
    }

    }


