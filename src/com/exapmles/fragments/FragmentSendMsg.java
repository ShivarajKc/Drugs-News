package com.exapmles.fragments;

import com.examples.antibiotic.MessageActivity;
import com.examples.antibiotic.R;
import com.examples.antibiotic.R.layout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSendMsg extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Intent i = new Intent(getActivity().getApplicationContext(),
				MessageActivity.class);
		startActivity(i);
		View view = inflater.inflate(R.layout.fragment_layout_msg, container,
				false);

		return view;
	}
}
