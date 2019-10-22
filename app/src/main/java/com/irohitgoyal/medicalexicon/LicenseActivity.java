package com.irohitgoyal.medicalexicon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by ROHIT on 3/5/2016.
 */
public class LicenseActivity extends AppCompatActivity {
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_license);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.licenseText));
    }

    public boolean onCreateOptionsMenu(Menu paramMenu)
    {
        return true;
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
}