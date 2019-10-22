package com.irohitgoyal.medicalexicon;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


/**
 * Created by ROHIT on 3/5/2016.
 */
public class Detail {
	public String causes;
	public String complications;
	public String definition;
	public int id;
	public int is_favorites;
	public String lifestyleremedies;
	public String name;
	public String preparingappointment;
	public String riskfactors;
	public String symptoms;
	public String testsdiagnosis;
	public String treatment;
	public String url;

	public Detail(Context paramContext, int paramInt) {
		Uri localUri = ContentUris.withAppendedId(ContentManager.Detail.CONTENT_DETAIL_URI, (long) paramInt);
		Cursor localCursor = paramContext.getContentResolver().query(localUri, null, null, null, null);
		if (localCursor.moveToFirst()) {
			this.id = localCursor.getInt(localCursor.getColumnIndex("_id"));
			this.name = localCursor.getString(localCursor.getColumnIndex("name"));
			this.url = localCursor.getString(localCursor.getColumnIndex("url"));
			this.definition = localCursor.getString(localCursor.getColumnIndex("definition"));
			this.symptoms = localCursor.getString(localCursor.getColumnIndex("symptoms"));
			this.causes = localCursor.getString(localCursor.getColumnIndex("causes"));
			this.riskfactors = localCursor.getString(localCursor.getColumnIndex("riskfactors"));
			this.complications = localCursor.getString(localCursor.getColumnIndex("complications"));
			this.preparingappointment = localCursor.getString(localCursor.getColumnIndex("preparingappointment"));
			this.testsdiagnosis = localCursor.getString(localCursor.getColumnIndex("testsdiagnosis"));
			this.treatment = localCursor.getString(localCursor.getColumnIndex("treatment"));
			this.lifestyleremedies = localCursor.getString(localCursor.getColumnIndex("lifestyleremedies"));
			this.is_favorites = localCursor.getInt(localCursor.getColumnIndex("is_favorites"));
		}
	}
}
