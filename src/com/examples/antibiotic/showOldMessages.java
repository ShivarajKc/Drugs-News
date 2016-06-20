package com.examples.antibiotic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;

public class showOldMessages extends Activity {
	TextView msgText, timestampText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.old_msg_activity);

		String msg = getIntent().getStringExtra("message");
		String timestamp = getIntent().getStringExtra("timestamp");

		msgText = (TextView) findViewById(R.id.oldmessage);
		timestampText = (TextView) findViewById(R.id.timestamp);
		msgText.setText(msg);
		timestampText.setText(timestamp);
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
				showOldMessages.this);

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