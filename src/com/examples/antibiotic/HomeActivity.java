package com.examples.antibiotic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.exapmles.fragments.FragmentAbout;
import com.exapmles.fragments.FragmentFacebookPage;
import com.exapmles.fragments.FragmentHelp;
import com.exapmles.fragments.FragmentHome;
import com.exapmles.fragments.FragmentInviteFriends;
import com.exapmles.fragments.FragmentLogin;
import com.exapmles.fragments.FragmentSendMsg;
import com.exapmles.fragments.FragmentTwitterPage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

//import android.support.v7.app.ActionBarDrawerToggle;

public class HomeActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;

	List<DrawerItem> dataList;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		// check for first time using app
		checkFirstRun();

		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// Add Drawer Item to dataList

		String aDataRow = "";
		String aBuffer = "";
		try {
			File myFile = new File("/sdcard/drugs_news_app/1234512345.txt");
			if (myFile.exists()) {
				FileInputStream fIn = new FileInputStream(myFile);
				BufferedReader myReader = new BufferedReader(
						new InputStreamReader(fIn));
				while ((aDataRow = myReader.readLine()) != null) {
					aBuffer += aDataRow;
				}
				myReader.close();
			}
		} catch (Exception e) {
		}

		if (!aBuffer.equals("")) {
			dataList.add(new DrawerItem(aBuffer, R.drawable.ic_userprofile));
		} else {
			dataList.add(new DrawerItem("Welcome Here ...",
					R.drawable.ic_userprofile));
		}

		dataList.add(new DrawerItem("Social Apps")); // adding a header to the
														// list
		// dataList.add(new DrawerItem("Home", R.drawable.ic_home));
		dataList.add(new DrawerItem("Your Profile", R.drawable.ic_account));
		dataList.add(new DrawerItem("Invite Friends", R.drawable.ic_invite));
		dataList.add(new DrawerItem("Chatting", R.drawable.ic_msg));
		dataList.add(new DrawerItem("Facebook Page", R.drawable.ic_fbb));
		dataList.add(new DrawerItem("Twitter Page", R.drawable.ic_tw));

		dataList.add(new DrawerItem("Other Option")); // adding a header to the
														// list
		dataList.add(new DrawerItem("About", R.drawable.ic_action_about));
		dataList.add(new DrawerItem("Help", R.drawable.ic_help));

		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
				dataList);

		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			if (dataList.get(0).getTitle() != null) {
				SelectItem(1);
			} else {
				SelectItem(0);
			}
		}
	}

	public void SelectItem(int possition) {
		Fragment fragment = null;
		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			fragment = new FragmentHome();
			break;
		case 2:
			fragment = new FragmentLogin();
			break;
		case 3:
			fragment = new FragmentInviteFriends();
			break;
		case 4:
			fragment = new FragmentSendMsg();
			break;
		case 5:
			fragment = new FragmentFacebookPage();
			break;
		case 6:
			fragment = new FragmentTwitterPage();
			break;
		case 8:
			fragment = new FragmentAbout();
			break;
		case 9:
			fragment = new FragmentHelp();
			break;
		default:
			break;
		}

		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem mi = menu.add("Exit");

		mi.setOnMenuItemClickListener(new OnMenuItemClickListener() {
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
				HomeActivity.this);

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

	public void checkFirstRun() {
		boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				.getBoolean("isFirstRun", true);
		if (isFirstRun) {
			// Place your dialog code here to display the dialog

			getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
					.putBoolean("isFirstRun", false).apply();
			showAlert();
		}
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

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}

		}
	}
}
