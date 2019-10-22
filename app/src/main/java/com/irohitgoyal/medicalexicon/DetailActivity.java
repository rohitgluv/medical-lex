package com.irohitgoyal.medicalexicon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class DetailActivity extends AppCompatActivity
{
    public static final String TAG = "DetailActivity";
    private AdView m_adView;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Bundle localBundle1;
        // Bundle localBundle2;
        if (paramBundle == null) {
            Bundle localBundle1 = new Bundle();
            Bundle localBundle2 = getIntent().getExtras();
            if (localBundle2 != null) {
                if (localBundle2.containsKey("item_id")) {
                    localBundle1.putInt("item_id", localBundle2.getInt("item_id", 0));
                } else if (localBundle2.containsKey("url")) {
                    localBundle1.putString("url", localBundle2.getString("url"));
                }
                DetailFragment localDetailFragment = new DetailFragment();
                localDetailFragment.setArguments(localBundle1);
                getSupportFragmentManager().beginTransaction().add(R.id.detail_container, localDetailFragment).commit();
            }
        }
                 this.m_adView = ((AdView)findViewById(R.id.adView));
                AdRequest localAdRequest = new AdRequest.Builder().build();
                this.m_adView.loadAd(localAdRequest);
    }

    public void onDestroy()
    {
        if (this.m_adView != null) {
            this.m_adView.destroy();
        }
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        if (paramMenuItem.getItemId() == 16908332)
        {
            NavUtils.navigateUpTo(this, new Intent(this, TitlesListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(paramMenuItem);
    }

    public void onPause()
    {
        if (this.m_adView != null) {
            this.m_adView.pause();
        }
        super.onPause();
    }

    public void onResume()
    {
        super.onResume();
       if (this.m_adView != null) {
            this.m_adView.resume();
        }
    }
}
