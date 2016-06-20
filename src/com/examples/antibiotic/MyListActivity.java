package com.examples.antibiotic;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.examples.model.DataBase;
import com.examples.model.News;

public class MyListActivity extends Activity implements OnItemClickListener {
	/** Called when the activity is first created. */

	ListView lview3;
	ListViewCustomAdapter adapter;
	private static String msg[];
	private static String timestamp[];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rowlayout);

		DataBase db = new DataBase(this);
		ArrayList<News> s = new ArrayList<>();
		s = db.getAllCotacts();

		msg = new String[s.size()];
		timestamp = new String[s.size()];

		for (int i = 0; i < s.size(); i++) {
			msg[i] = s.get(i).getMessage();
			timestamp[i] = s.get(i).getTimestamp();
		}

		lview3 = (ListView) findViewById(R.id.listView3);
		adapter = new ListViewCustomAdapter(this, msg, timestamp);
		lview3.setAdapter(adapter);

		lview3.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
		Intent intent = new Intent(getBaseContext(), showOldMessages.class);
		intent.putExtra("message", msg[position]);
		intent.putExtra("timestamp", timestamp[position]);
		startActivity(intent);

		// Toast.makeText(
		// this,
		// "Title => " + msg[position] + " n Description => "
		// + timestamp[position], Toast.LENGTH_SHORT).show();
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
				MyListActivity.this);

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