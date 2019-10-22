package com.irohitgoyal.medicalexicon;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static int COUNTSTEP = 2;
    private static final String TAG = "FirstPrepare";
    private final int DATABASE_SPACE = 100;
    private final String EXPERT_MODE = "isExpertMode";
    private final String FONT_SIZE_BIG = "fontSizeBig";
    private final String FONT_SIZE_NORMAL = "fontSizeNormal";
    private final String FONT_SIZE_SMALL = "fontSizeSmall";
    private int curentStep = 1;
    private SharedPreferences sPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String errorDB;
        try {
            DatabaseHelper dbHelperObj = DatabaseHelper.getInstance(this);
            //dbHelperObj.openDataBase();
            errorDB =dbHelperObj.testDataBase();
            dbHelperObj.close();
        } catch (Exception e) {
            Log.e("MainActivity", "onCreate", e);
            errorDB = e.getLocalizedMessage();
        }
        getSettings();
        if (errorDB.isEmpty()) {
            runActivity();
        }
        else {
            Long freeSpace;
            label28: {
                long var3;
                try {
                    File var9 = new File(this.getBaseContext().getExternalFilesDir((String)null).toString());
                    var3 = var9.getFreeSpace() / 1048576L;
                } catch (Exception var6) {
                    freeSpace = Long.valueOf(Long.parseLong("100"));
                    break label28;
                }

                freeSpace = Long.valueOf(var3);
            }

            if ((StaticData.isFirstRun.booleanValue()) && (freeSpace.longValue() < 100L))
        {
            StaticData.isError = Boolean.valueOf(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ABC").setMessage("XYZ").setIcon(android.R.drawable.stat_sys_warning).setCancelable(false).setNegativeButton("DEF", new DialogInterface.OnClickListener()
            { public void onClick(DialogInterface dialog, int id) {
                    finish();
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else {
            this.runActivityTask();
        }
    }
    }

    private void getSettings()
    {
        this.sPref = getSharedPreferences("medsister", 0);
        String str1 = this.sPref.getString("fontSizeNormal", "15");
        String str2 = this.sPref.getString("fontSizeSmall", "12");
        String str3 = this.sPref.getString("fontSizeBig", "21");
        String str4 = this.sPref.getString("isExpertMode", "false");
        StaticData.fontSizeNormal = Integer.parseInt(str1);
        StaticData.fontSizeSmall = Integer.parseInt(str2);
        StaticData.fontSizeBig = Integer.parseInt(str3);
        StaticData.isExpertMode = Boolean.valueOf(Boolean.parseBoolean(str4));
    }

    private void runActivityTask() {
        if (this.curentStep > COUNTSTEP) {
            runActivity();
        } else {
            new ActivityTask(this).execute(new Void[0]);
        }
    }

    private void runActivity()
    {
        startActivity(new Intent(this, TitlesListActivity.class));
        finish();
    }

    public void dataBaseNoError()
    {
        runActivity();
    }
    public void dataBaseError(String paramString)
    {
        if (paramString.equals("write failed: ENOSPC (No space left on device)"))
        {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
            localBuilder.setTitle(getString(R.string.not_have_free_space)).setMessage(getString(R.string.message_100mb)).setIcon(android.R.drawable.stat_sys_warning).setCancelable(false).setNegativeButton(getString(R.string.close_app), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    MainActivity.this.finish();
                    paramAnonymousDialogInterface.cancel();
                }
            });
            localBuilder.create().show();
        }
        this.curentStep = (1 + this.curentStep);
        runActivityTask();
    }

}
