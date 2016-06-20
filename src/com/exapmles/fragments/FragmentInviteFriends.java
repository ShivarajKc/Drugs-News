package com.exapmles.fragments;

import com.examples.antibiotic.InviteFriendsActivity;
import com.examples.antibiotic.R;
import com.examples.antibiotic.R.layout;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentInviteFriends extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Intent i = new Intent(getActivity().getApplicationContext(),
				InviteFriendsActivity.class);
		startActivity(i);
		View view = inflater.inflate(R.layout.fragment_layout_invite, container,
				false);

		return view;
	}
}
