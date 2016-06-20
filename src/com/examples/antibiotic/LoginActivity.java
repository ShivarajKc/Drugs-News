package com.examples.antibiotic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

@SuppressLint("SdCardPath")
public class LoginActivity extends Activity {
	private UiLifecycleHelper uihelper;
	Button butLogin, backButton;

	TextView welcomeText, user_name;
	ImageView user_image;

	void showMsg(String string) {
		Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT)
				.show();
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,
				Exception exception) {

			onSessionStateChange(session, state, exception);
		}
	};

	void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			Log.i("facebook", "Logged in...");
			System.out.println("Logged in..");

			Request.newMeRequest(session, new Request.GraphUserCallback() {

				@SuppressLint("SdCardPath")
				@Override
				public void onCompleted(GraphUser user, Response response) {

					if (user != null) {
						// showMsg(user.getName());

						TextView user_name = (TextView) findViewById(R.id.user_name);
						user_name.setText(user.getName());
						user_name.setVisibility(View.VISIBLE);

						try {
							String root = Environment
									.getExternalStorageDirectory().toString();
							File myDir = new File(root + "/drugs_news_app");
							if (!myDir.exists()) {
								myDir.mkdirs();
							}

							File myFile = new File(
									"/sdcard/drugs_news_app/1234512345.txt");
							myFile.createNewFile();
							FileOutputStream fOut = new FileOutputStream(myFile);
							OutputStreamWriter myOutWriter = new OutputStreamWriter(
									fOut);
							myOutWriter.write(user.getName());
							myOutWriter.close();
							fOut.close();
							// System.out.println("DONE");
							File myFile2 = new File(
									"/sdcard/drugs_news_app/1234554321.txt");
							myFile2.createNewFile();
							FileOutputStream fOut2 = new FileOutputStream(
									myFile2);
							OutputStreamWriter myOutWriter2 = new OutputStreamWriter(
									fOut2);
							myOutWriter2.write(user.getId());
							myOutWriter2.close();
							fOut2.close();
							// System.out.println("DONE");

						} catch (Exception e) {
						}
						TextView welcomeText = (TextView) findViewById(R.id.welcomeText);
						welcomeText.setVisibility(View.VISIBLE);

						// showMsg(user.getProperty("email") + "");
						// showMsg(user.getProperty("gender") + "");
						// showMsg(user.getId() + "");
						butLogin = (Button) findViewById(R.id.authButton);
						butLogin.setVisibility(View.GONE);

						backButton = (Button) findViewById(R.id.backtohome);
						backButton.setVisibility(View.VISIBLE);
						backButton
								.setOnTouchListener(new View.OnTouchListener() {

									@Override
									public boolean onTouch(View v,
											MotionEvent event) {
										// TODO Auto-generated method stub
										Intent i = new Intent(
												getApplicationContext(),
												HomeActivity.class);
										startActivity(i);
										return false;
									}
								});
						ImageView fbUserAvatar = (ImageView) findViewById(R.id.user_image);
						new ImageLoadTask("https://graph.facebook.com/"
								+ user.getId() + "/picture?type=large",
								fbUserAvatar).execute();

					} else {
						showMsg("PLease try again");
						showMsg(response.getError().getErrorMessage());
					}
				}
			}).executeAsync();

		} else if (state.isClosed()) {
			Log.i("facebook", "Logged out...");
			
			TextView user_name = (TextView) findViewById(R.id.user_name);
			ImageView user_image = (ImageView) findViewById(R.id.user_image);
			user_image.setVisibility(View.INVISIBLE);
			user_name.setVisibility(View.INVISIBLE);
			
			File myFile1 = new File("/sdcard/drugs_news_app/1234512345.txt");
			File myFile2 = new File("/sdcard/drugs_news_app/1234554321.txt");
			File imgFile = new File("/sdcard/drugs_news_app/1234123455.jpg");
			
			if (myFile1.exists())
				myFile1.delete();

			if (myFile2.exists())
				myFile2.delete();

			if (imgFile.exists())
				imgFile.delete();
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/drugs_news_app");
			if (myDir.exists()) {
				myDir.delete();
				System.out.println("Deleted...");
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		uihelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uihelper.onSaveInstanceState(outState);
	}

	@Override
	protected void onPause() {
		super.onPause();
		uihelper.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		uihelper.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uihelper.onActivityResult(requestCode, resultCode, data);
	}

	public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

		private String url;
		private ImageView imageView;

		public ImageLoadTask(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				URL urlConnection = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlConnection
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);

				String root = Environment.getExternalStorageDirectory()
						.toString();
				File myDir = new File(root + "/drugs_news_app");
				if (!myDir.exists())
					myDir.mkdirs();
				String fname = "1234123455.jpg";
				File file = new File(myDir, fname);
				if (file.exists())
					file.delete();
				try {
					FileOutputStream out = new FileOutputStream(file);
					myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
					out.flush();
					out.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

				return myBitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageBitmap(result);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_layout_login);
		uihelper = new UiLifecycleHelper(this, callback);
		uihelper.onCreate(savedInstanceState);

		TextView user_name = (TextView) findViewById(R.id.user_name);

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

		user_name.setText(aBuffer);
		File imgFile = new File("/sdcard/drugs_news_app/1234123455.jpg");

		if (imgFile.exists()) {

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());

			ImageView myImage = (ImageView) findViewById(R.id.user_image);

			myImage.setImageBitmap(myBitmap);

		}

		ArrayList<String> permission = new ArrayList<String>();
		// spermission.add("email");
		permission.add("public_profile");
		// permission.add("user_friends");

		LoginButton btn = (LoginButton) findViewById(R.id.authButton);
		btn.setPublishPermissions(permission);
		System.out.println("here");
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.examples.antibiotic", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
				LoginActivity.this);

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
