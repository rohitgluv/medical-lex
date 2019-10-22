package com.irohitgoyal.medicalexicon;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class ActivityTask extends AsyncTask<Void, Boolean, String>
    {
        Context context;

        public ActivityTask(Context paramContext)
        {
            this.context = paramContext;
        }

    public String createTable()
    {
        String str = "";
       DatabaseHelper dbHelperObj = DatabaseHelper.getInstance(this.context);
        try {
            dbHelperObj.createDataBase();
        } catch (Exception var3) {
            str = var3.getMessage();
        }

        dbHelperObj.close();
        return str; //+ this.testDataBase();
    }


  /*      public String testDataBase()
    {
        return DatabaseHelper.getInstance(this.context).testDataBase();
    }*/

    protected String doInBackground(Void... paramVarArgs)
    {
        return createTable();
    }

    protected void onPostExecute(String paramString)
    {
        if ("".length() == 0)
        {
            ((MainActivity)this.context).dataBaseNoError();
            return;
        }
        ((MainActivity)this.context).dataBaseError("");
    }

        protected void onPreExecute()
        {
            super.onPreExecute();
        }
    }
