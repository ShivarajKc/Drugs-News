package com.examples.antibiotic;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewCustomAdapter extends BaseAdapter {
	public String title[];
	public String description[];
	public Activity context;
	public LayoutInflater inflater;

	public ListViewCustomAdapter(Activity context, String[] title,
			String[] description) {
		super();

		this.context = context;
		this.title = title;
		this.description = description;

		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class ViewHolder {
		ImageView imgViewLogo;
		TextView txtViewTitle;
		TextView txtViewDescription;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listitem_row, null);

			holder.imgViewLogo = (ImageView) convertView
					.findViewById(R.id.imgViewLogo);
			holder.txtViewTitle = (TextView) convertView
					.findViewById(R.id.txtViewTitle);
			holder.txtViewDescription = (TextView) convertView
					.findViewById(R.id.txtViewDescription);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.imgViewLogo.setImageResource(R.drawable.ic_nrecords);
		String message = title[position];
		if (message.length() <= 15)
			holder.txtViewTitle.setText(message);
		else
			holder.txtViewTitle.setText(message.substring(0, 15)+"...");
		holder.txtViewDescription.setText(description[position]);

		return convertView;
	}

}