package com.exapmles.fragments;

import com.examples.antibiotic.R;
import com.examples.antibiotic.R.layout;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTwitterPage extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("https://twitter.com/EgyptDrugsNews"));				
		startActivity(i);

		View view = inflater.inflate(R.layout.fragment_layout_pre_notification, container,
				false);
		return view;
	}
}