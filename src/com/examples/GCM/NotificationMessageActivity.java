package com.examples.GCM;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.examples.antibiotic.HomeActivity;
import com.examples.antibiotic.R;
import com.examples.antibiotic.R.drawable;
import com.examples.antibiotic.R.id;
import com.examples.antibiotic.R.layout;
import com.examples.antibiotic.R.menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class NotificationMessageActivity extends Activity {
	TextView msgET, usertitleET;

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notifications_messages);

		// Intent Message sent from Broadcast Receiver
		String str = getIntent().getStringExtra("msg");

		// Get Email ID from Shared preferences
		SharedPreferences prefs = getSharedPreferences("UserDetails",
				Context.MODE_PRIVATE);
		// String eMailId = prefs.getString("eMailId", "");
		// Set Title
		// usertitleET = (TextView) findViewById(R.id.usertitle);

		if (!checkPlayServices()) {
			Toast.makeText(getApplicationContext(),
					"Your Mobile Phone will unable to get messages.",
					Toast.LENGTH_LONG).show();
		}

		// usertitleET.setText("Hello " + eMailId + " !");
		// When Message sent from Broadcase Receiver is not empty
		if (str != null) {
			msgET = (TextView) findViewById(R.id.message);
			msgET.setText(str);
		}
	}

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this,
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Your Mobile Phone will unable to get messages.",
						Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		} else {
			// Toast.makeText(
			// getApplicationContext(),
			// "This device supports Play services, App will work normally",
			// Toast.LENGTH_LONG).show();
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkPlayServices();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem home = menu.add("Home");
		MenuItem exit = menu.add("Exit");

		home.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent i = new Intent(getApplicationContext(),
						HomeActivity.class);
				startActivity(i);
				return true;
			}
		});
		exit.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				doExit();
				return true;
			}
		});
		return true;
	}

	private void doExit() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				NotificationMessageActivity.this);

		alertDialog.setPositiveButton("Yes", new OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(DialogInterface dialog, int which) {
				moveTaskToBack(true);
				System.runFinalizersOnExit(true);
				finish();
				System.exit(0);
			}
		});

		alertDialog.setNegativeButton("No", null);

		alertDialog.setMessage("Do you want to exit?");
		alertDialog.show();
	}

	@SuppressWarnings("deprecation")
	public void showAlert() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Welcome");
		alertDialog
				.setMessage("We Wish This Application be a good reference for you...");
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// here you can add functions
			}
		});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
}
