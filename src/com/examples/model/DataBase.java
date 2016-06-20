package com.examples.model;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "drugs_news.db";
	public static final String NEWS_TABLE_NAME = "news";
	public static final String NEWS_COLUMN_ID = "id";
	public static final String NEWS_COLUMN_MESSAGE = "message";
	public static final String NEWS_COLUMN_TIMESTAMP = "timestamp";
	@SuppressWarnings("rawtypes")
	private HashMap hp;

	public DataBase(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table news "
				+ "(id integer primary key AUTOINCREMENT, message text,timestamp DEFAULT CURRENT_TIMESTAMP)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS news");
		onCreate(db);
	}

	public boolean insertNews(String message) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("message", message); 
		db.insert("news", null, contentValues);
		return true;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db
				.rawQuery("select * from news where id=" + id + "", null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db, NEWS_TABLE_NAME);
		return numRows;
	}

	public boolean updateContact(Integer id, String name, String phone,
			String email, String street, String place) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		contentValues.put("phone", phone);
		contentValues.put("email", email);
		contentValues.put("street", street);
		contentValues.put("place", place);
		db.update("NEWS", contentValues, "id = ? ",
				new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteContact(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("news", "id = ? ",
				new String[] { Integer.toString(id) });
	}

	// public ArrayList<String> getAllCotacts() {
	// ArrayList<String> array_list = new ArrayList<String>();
	//
	// // hp = new HashMap();
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor res = db.rawQuery("select * from news", null);
	// res.moveToFirst();
	//
	// while (res.isAfterLast() == false) {
	// array_list.add(res.getString(res.getColumnIndex(NEWS_COLUMN_MESSAGE)));
	// res.moveToNext();
	// }
	// return array_list;
	// }
	public ArrayList<News> getAllCotacts() {
		ArrayList<News> array_list = new ArrayList<News>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from news ORDER BY timestamp DESC", null);
		res.moveToFirst();

		News n;
		while (res.isAfterLast() == false) {
			n = new News(
					res.getString(res.getColumnIndex(NEWS_COLUMN_MESSAGE)),
					res.getString(res.getColumnIndex(NEWS_COLUMN_TIMESTAMP)));
			array_list.add(n);
			res.moveToNext();
		}
		return array_list;
	}
}