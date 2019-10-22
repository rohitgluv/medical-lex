package com.irohitgoyal.medicalexicon;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class TitlesListActivity
            extends AppCompatActivity
            implements TilesListFragment.Callbacks, SearchView.OnCloseListener, View.OnFocusChangeListener, SearchView.OnQueryTextListener
    {
        public static final String STATE_VISIBLE_SEARCH_ITEM = "state_visible_search_item";
        private DialogFragment dlgAbout;
        int iconFavoriteOff = R.drawable.ic_tab_bookmarks_selected;
        int iconFavoriteOn = R.drawable.ic_tab_bookmarks_unselected;
        private AdView m_adView;
        private MenuItem m_favoriteMenuItem;
        private InterstitialAd m_interstitialAds = null;
        private MenuItem m_searchMenuItem;
        private String m_searchTextPrev;
        private SearchView m_searchView;
        private boolean m_twoPane;
        private boolean m_visibleSearchMenuItem;

        public TilesListFragment getTiltesListFragemt()
        {
            return (TilesListFragment)getSupportFragmentManager().findFragmentById(R.id.titles_list);
        }

        public void onBackPressed()
        {
            if (!this.m_visibleSearchMenuItem)
            {
                this.m_searchTextPrev = null;
                MenuItemCompat.collapseActionView(this.m_searchMenuItem);
                m_visibleSearchMenuItem = (!getTiltesListFragemt().toggleFavorites());
                supportInvalidateOptionsMenu();
                    return;
            }
            if ((getSupportFragmentManager().getBackStackEntryCount() == 0) && (!this.m_searchView.hasWindowFocus()) && (this.m_interstitialAds.isLoaded())) {
                this.m_interstitialAds.show();
            }
            super.onBackPressed();
        }

        public boolean onClose()
        {
            getTiltesListFragemt().setSearchFilter(null);
            return true;
        }

        protected void onCreate(Bundle paramBundle)
        {
            super.onCreate(paramBundle);
            setContentView(R.layout.activity_titleslist);
            this.dlgAbout = new DialogAbout();
            if (findViewById(R.id.detail_container) != null)
            {
                this.m_twoPane = true;
                ((TilesListFragment)getSupportFragmentManager().findFragmentById(R.id.titles_list)).setActivateOnItemClick(true);
            }
            this.m_adView = ((AdView)findViewById(R.id.adView));
            AdRequest localAdRequest1 = new AdRequest.Builder().build();
            this.m_adView.loadAd(localAdRequest1);
            this.m_interstitialAds = new InterstitialAd(this);
            this.m_interstitialAds.setAdUnitId(getString(R.string.adbMobFullScreenUnitId));
            AdRequest localAdRequest2 = new AdRequest.Builder().build();
            this.m_interstitialAds.loadAd(localAdRequest2);
            boolean boolVar;
            if(paramBundle != null && !paramBundle.getBoolean("state_visible_search_item")) {
                boolVar = false;
            } else {
                boolVar = true;
            }

            this.m_visibleSearchMenuItem = boolVar;
        }
        public boolean onCreateOptionsMenu(Menu menu)
        {
            /*this.getMenuInflater().inflate(R.menu.main, paramMenu);
            SearchManager localSearchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
            this.m_searchMenuItem = paramMenu.findItem(R.id.action_search);
            this.m_favoriteMenuItem = paramMenu.findItem(R.id.action_favorites);
            this.m_searchView = ((SearchView)MenuItemCompat.getActionView(this.m_searchMenuItem));
            this.m_searchView.setSearchableInfo(localSearchManager.getSearchableInfo(this.getComponentName()));
            this.m_searchView.setOnCloseListener(this);
            this.m_searchView.setOnQueryTextFocusChangeListener(this);
            if(this.m_searchTextPrev != null && !this.m_searchTextPrev.isEmpty()) {
                MenuItemCompat.expandActionView(this.m_searchMenuItem);
                this.m_searchView.setQuery(this.m_searchTextPrev, false);
            } else {
                this.m_searchTextPrev = this.getTiltesListFragemt().getSearchFilter();
                if(this.m_searchTextPrev != null && !this.m_searchTextPrev.isEmpty()) {
                    MenuItemCompat.expandActionView(this.m_searchMenuItem);
                    this.m_searchView.setQuery(this.m_searchTextPrev, false);
                }
            }
            this.m_searchView.setOnQueryTextListener(this);
            return true;*/

            getMenuInflater().inflate(R.menu.main, menu);
            SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
            m_searchMenuItem = menu.findItem(R.id.action_search);
            m_favoriteMenuItem = menu.findItem(R.id.action_favorites);
            m_searchView = (SearchView)MenuItemCompat.getActionView(m_searchMenuItem);
            m_searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            m_searchView.setOnCloseListener(this);
            m_searchView.setOnQueryTextFocusChangeListener(this);
            if((m_searchTextPrev == null) || (m_searchTextPrev.isEmpty())) {
                m_searchTextPrev = getTiltesListFragemt().getSearchFilter();
                if((m_searchTextPrev != null) && (!m_searchTextPrev.isEmpty())) {
                    MenuItemCompat.expandActionView(m_searchMenuItem);
                    m_searchView.setQuery(m_searchTextPrev, false);
                }
            } else {
                MenuItemCompat.expandActionView(m_searchMenuItem);
                m_searchView.setQuery(m_searchTextPrev, false);
            }
            m_searchView.setOnQueryTextListener(this);
            return true;
        }

        public void onDestroy()
        {
            if (this.m_adView != null) {
                this.m_adView.destroy();
            }
            super.onDestroy();
        }

        public void onFocusChange(View paramView, boolean paramBoolean)
        {
            if (!paramBoolean)
            {
                MenuItemCompat.collapseActionView(this.m_searchMenuItem);
                this.m_searchView.setQuery(null, false);
            }
        }

        public void onItemSelected(int paramInt)
        {
            if (this.m_twoPane)
            {
                Bundle localBundle = new Bundle();
                localBundle.putInt("item_id", paramInt);
                DetailFragment localDetailFragment = new DetailFragment();
                localDetailFragment.setArguments(localBundle);
                this.m_searchView.setOnQueryTextFocusChangeListener(null);
                getSupportFragmentManager().popBackStack("DetailActivity", 1);
                getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, localDetailFragment).addToBackStack("DetailActivity").commit();
            }
            else {
                Intent localIntent = new Intent(this, DetailActivity.class);
                localIntent.putExtra("item_id", paramInt);
                startActivity(localIntent);
            }
        }

        protected void onNewIntent(Intent paramIntent)
        {
            if ("android.intent.action.SEARCH".equals(paramIntent.getAction()))
            {
                String str = paramIntent.getStringExtra("query");
                MenuItemCompat.expandActionView(this.m_searchMenuItem);
                this.m_searchView.setQuery(str, true);
            }
        }

        public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
            boolean var3 = true;
            boolean var2;
            switch(paramMenuItem.getItemId()) {
                case 16908332:
                case R.id.action_favorites:
                    this.m_searchTextPrev = null;
                    MenuItemCompat.collapseActionView(this.m_searchMenuItem);
                    if(!this.getTiltesListFragemt().toggleFavorites()) {
                        var2 = true;
                    } else {
                        var2 = false;
                    }

                    this.m_visibleSearchMenuItem = var2;
                    this.supportInvalidateOptionsMenu();
                    var2 = var3;
                    break;
                case  R.id.action_item_about:
                    this.dlgAbout.show(this.getSupportFragmentManager(), "dlgAbout");
                    var2 = var3;
                    break;
                default:
                    var2 = super.onOptionsItemSelected(paramMenuItem);
            }
            return var2;
        }

        public void onPause()
        {
           /* if (this.m_adView != null) {
                this.m_adView.pause();
            }*/
            super.onPause();
        }

        public boolean onPrepareOptionsMenu(Menu paramMenu)
        {
            this.m_searchMenuItem.setVisible(this.m_visibleSearchMenuItem);
            MenuItem localMenuItem = this.m_favoriteMenuItem;
            int varInt;
            if(this.m_visibleSearchMenuItem) {
                varInt = this.iconFavoriteOff;
            } else {
                varInt = this.iconFavoriteOn;
            }
            localMenuItem.setIcon(varInt);
            return super.onPrepareOptionsMenu(paramMenu);
        }

        public boolean onQueryTextChange(String paramString)
        {
            if (!paramString.equals(this.m_searchTextPrev))
            {
                this.m_searchTextPrev = paramString;
                getTiltesListFragemt().setSearchFilter(paramString);
            }
            return false;
        }

        public boolean onQueryTextSubmit(String paramString)
        {
            if (!paramString.equals(this.m_searchTextPrev))
            {
                this.m_searchTextPrev = paramString;
                getTiltesListFragemt().setSearchFilter(paramString);
            }
            return true;
        }

        public void onResume()
        {
            super.onResume();
           if (this.m_adView != null) {
                this.m_adView.resume();
            }
        }

        public void onSaveInstanceState(Bundle paramBundle)
        {
            super.onSaveInstanceState(paramBundle);
            paramBundle.putBoolean("state_visible_search_item", this.m_visibleSearchMenuItem);
        }

        public void setItemUrl(String paramString)
        {
            Bundle localBundle = new Bundle();
            localBundle.putString("url", paramString);
            DetailFragment localDetailFragment = new DetailFragment();
            localDetailFragment.setArguments(localBundle);
            this.m_searchView.setOnQueryTextFocusChangeListener(null);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, localDetailFragment).addToBackStack("DetailActivity").commit();
        }
    }
