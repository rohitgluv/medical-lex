package com.irohitgoyal.medicalexicon;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.ShareActionProvider;

/**
 * Created by ROHIT on 3/4/2016.
 */
public class DetailFragment extends Fragment
{
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_URL = "url";
    private Detail m_detail;
    private String m_error;

    private void setText(View paramView, int paramInt1, int paramInt2, String paramString)
    {
        TextView localTextView1 = (TextView)paramView.findViewById(paramInt1);
        TextView localTextView2 = (TextView)paramView.findViewById(paramInt2);
        if ((paramString == null) || (paramString.length() == 0))
        {
            localTextView1.setVisibility(View.GONE);
            localTextView2.setVisibility(View.GONE);
            return;
        }
        localTextView2.setText(Html.fromHtml(paramString, null, new LiTagHandler()));
    }

    public void onCreate(Bundle paramBundle)
    {
        setHasOptionsMenu(true);
        super.onCreate(paramBundle);
        this.m_error = "";
        if (getArguments().containsKey("item_id")) {}
        try
        {
            int i = getArguments().getInt("item_id");
            this.m_detail = new Detail(getActivity(), i);
            return;
        }
        catch (Exception localException)
        {
            Log.e("Database", "DetailFragment.onCreate", localException);
            this.m_error = (localException.getLocalizedMessage() + " [" + ExtendException.stackTraceToString(localException) + "]");
        }
    }

    public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
    {
        paramMenuInflater.inflate(R.menu.menu_share, paramMenu);
        ShareActionProvider localShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(paramMenu.findItem(R.id.action_share));
        if (this.m_detail != null)
        {
            String str1 = this.m_detail.name;
            String str2 = str1 + "\r\n";
            String str3 = str2 + "Definition: " + this.m_detail.definition;
            String str4 = str3 + "\r\n";
            String str5 = str4 + "Symptoms: " + this.m_detail.symptoms;
            String str6 = str5 + "\r\n";
            String str7 = str6 + "Causes: " + this.m_detail.causes;
            String str8 = str7 + "\r\n";
            String str9 = str8 + "Risk factors: " + this.m_detail.riskfactors;
            String str10 = str9 + "\r\n";
            String str11 = str10 + "Complications: " + this.m_detail.complications;
            String str12 = str11 + "\r\n";
            String str13 = str12 + "Preparing for your appointment: " + this.m_detail.preparingappointment;
            String str14 = str13 + "\r\n";
            String str15 = str14 + "Tests and diagnosis: " + this.m_detail.testsdiagnosis;
            String str16 = str15 + "\r\n";
            String str17 = str16 + "Treatments and drugs: " + this.m_detail.treatment;
            String str18 = str17 + "\r\n";
            String str19 = (str18 + "Lifestyle and home remedies: " + this.m_detail.lifestyleremedies).replace("<p>", "").replace("</p>", "\r\n").replace("<ul>", "").replace("</ul>", "\r\n").replace("<li>", " - ").replace("</li>", "\r\n").replace("&#39;", "'").replace("<h3>", "").replace("</h3>", "\r\n").replace("<strong>", "").replace("</strong>", "");
            localShareActionProvider.setShareIntent(new Intent("android.intent.action.SEND").putExtra("android.intent.extra.TEXT", str19).setType("text/plain"));
        }
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        View localView = paramLayoutInflater.inflate(R.layout.fragment_detail, paramViewGroup, false);
        TextView localTextView = (TextView)localView.findViewById(R.id.textViewName);
        if (this.m_detail != null)
        {
            localTextView.setText(Html.fromHtml(this.m_detail.name.replaceAll("-", ""), null, new LiTagHandler()));
            setText(localView, R.id.textViewDefinitionLabel, R.id.textViewDefinition, this.m_detail.definition);
            setText(localView, R.id.textViewSymptomsLabel, R.id.textViewSimphtoms, this.m_detail.symptoms);
            setText(localView, R.id.textViewCausesLabel, R.id.textViewCauses, this.m_detail.causes);
            setText(localView, R.id.textViewRiskFactorsLabel, R.id.textViewRiskFactors, this.m_detail.riskfactors);
            setText(localView, R.id.textViewComplicationsLabel, R.id.textViewComplications, this.m_detail.complications);
            setText(localView, R.id.textViewPreparingAppointmentLabel, R.id.textViewPreparingAppointment, this.m_detail.preparingappointment);
            setText(localView, R.id.textViewTestsDiagnosisLabel, R.id.textViewTestsDiagnosis, this.m_detail.testsdiagnosis);
            setText(localView, R.id.textViewTreatmentLabel, R.id.textViewTreatment, this.m_detail.treatment);
            setText(localView, R.id.textViewLifestyleRemediesLabel, R.id.textViewLifestyleRemedies, this.m_detail.lifestyleremedies);
            return localView;
        }
        ((TextView)localView.findViewById(R.id.textViewName)).setText("\\u041f\\u0440\\u043e\\u0438\\u0437\\u043e\\u0448\\u043b\\u0430 \\u043e\\u0448\\u0438\\u0431\\u043a\\u0430: " + this.m_error);
        return localView;
    }
}

