package com.irohitgoyal.medicalexicon;

import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ROHIT on 3/5/2016.
 */

public class DialogAbout extends DialogFragment implements View.OnClickListener
{
    public void onCancel(DialogInterface paramDialogInterface)
    {
       super.onCancel(paramDialogInterface);
    }

    public void onClick(View paramView)
    {
       switch (paramView.getId()){

        case R.id.textOtherApp:
        {
            Intent intentLink = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=" + getString(R.string.devPlay)));
            startActivity(intentLink);
            
            break;
        }
    }
    
    dismiss();
}

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        getDialog().requestWindowFeature(1);
        
        View localView = paramLayoutInflater.inflate(R.layout.dialog_about, null);
        
        localView.findViewById(R.id.btnYes).setOnClickListener(this);
        //localView.findViewById(R.id.textViewFullHref).setOnClickListener(this);
        
        localView.findViewById(R.id.textOtherApp).setOnClickListener(this);
        
        ((TextView)localView.findViewById(R.id.textVers)).setText("1.1");
        return localView;
    }


    public void onDismiss(DialogInterface paramDialogInterface)
    {
       super.onDismiss(paramDialogInterface);
    }
}


