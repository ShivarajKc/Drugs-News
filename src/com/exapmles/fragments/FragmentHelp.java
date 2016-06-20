package com.exapmles.fragments;

import com.examples.antibiotic.R;
import com.examples.antibiotic.R.id;
import com.examples.antibiotic.R.layout;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentHelp extends Fragment {
	TextView phone;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_layout_help, container,
				false);

		phone = (TextView) view.findViewById(R.id.frag_help_text);
		phone.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_DIAL);
				i.setData(Uri.parse("tel:01227462847"));
				startActivity(i);
				return false;
			}
		});

		return view;
	}

}
