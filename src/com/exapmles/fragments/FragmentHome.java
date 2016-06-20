package com.exapmles.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.examples.GCM.NotificationsActivity;
import com.examples.antibiotic.MyListActivity;
import com.examples.antibiotic.R;
import com.examples.antibiotic.R.id;
import com.examples.antibiotic.R.layout;
import com.examples.model.DataBase;
import com.examples.model.News;

public class FragmentHome extends Fragment {
	Button btnlatest , btnOld;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		DataBase db = new DataBase(getActivity());
		ArrayList<News> s = new ArrayList<>();
		s = db.getAllCotacts();
		System.out.println(s.size());
		for (int i = 0; i < s.size(); i++) {
			System.out.println(i + " : " + s.get(i).getMessage());
			System.out.println(" : " + s.get(i).getTimestamp());
		}

		View view = inflater.inflate(R.layout.fragment_layout_pre_notification,
				container, false);
		btnlatest = (Button) view.findViewById(R.id.pre_news);
		btnlatest.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(),
						NotificationsActivity.class);
				startActivity(i);
				return false;
			}
		});
		btnOld = (Button) view.findViewById(R.id.old_news);
		btnOld.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity().getApplicationContext(),
						MyListActivity.class);
				startActivity(i);
				return false;
			}
		});
		return view;
	}

}
