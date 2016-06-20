package com.examples.antibiotic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

public class MessageActivity extends Activity {
	// Create, automatically open (if applicable), save, and restore the
	// Active Session in a way that is similar to Android UI lifecycles.
	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout_msg);
		// To maintain FB Login session
		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);

	}

	// When Send Message button is clicked
	public void sendMessagetoFriends(View v) {
		System.out.println("msg here");
		FacebookDialog.MessageDialogBuilder builder = new FacebookDialog.MessageDialogBuilder(
				this);

		// If the Facebook app is installed and we can present the share dialog
		if (builder.canPresent()) {
			// Enable button or other UI to initiate launch of the Message
			// Dialog
			FacebookDialog dialog = builder.build();
			uiHelper.trackPendingDialogCall(dialog.present());
		} else {
			// Disable button or other UI for Message Dialog
			Toast.makeText(
					getApplicationContext(),
					"Facebook Messenger app is not installed in your device, so disabling the button",
					Toast.LENGTH_SHORT).show();
			v.setEnabled(false);
		}
	}

	// After Facebook Messenger dialog is closed
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {

					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Toast.makeText(
								getApplicationContext(),
								"Error Occured\nMost Common Errors:\n1. Device not connected to Internet\n2.Faceboook APP Id is not changed in Strings.xml",
								Toast.LENGTH_LONG).show();
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Toast.makeText(getApplicationContext(), "Done!!",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
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
				MessageActivity.this);

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
