package com.exapmles.fragments;

import com.examples.antibiotic.LoginActivity;
import com.examples.antibiotic.R;
import com.examples.antibiotic.R.layout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentLogin extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Intent i = new Intent(getActivity().getApplicationContext(),
				LoginActivity.class);
		startActivity(i);
		View view = inflater.inflate(R.layout.fragment_layout_login, container,
				false);
		return view;
	}
}
