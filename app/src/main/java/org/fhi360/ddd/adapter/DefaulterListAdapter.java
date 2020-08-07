package org.fhi360.ddd.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.fhi360.ddd.R;
import org.fhi360.ddd.domain.Patient;

import java.util.ArrayList;
import java.util.List;


public class DefaulterListAdapter extends BaseAdapter {
    public List<Patient> patients;
    private Activity activity;

    public DefaulterListAdapter(Activity activity, List<Patient> patients) {
        this.activity = activity;
        this.patients = patients;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return patients.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return patients.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView textFirst, expectedVisit, missedVisit;
        TextView textSecond;
        TextView textThird;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.defaulter_list_view, null);
            holder.textFirst = (TextView) convertView.findViewById(R.id.name);
            holder.textSecond = (TextView) convertView.findViewById(R.id.address);
            holder.textThird = (TextView) convertView.findViewById(R.id.phone);
            holder.missedVisit = (TextView) convertView.findViewById(R.id.dateText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Patient patient = patients.get(position);
        holder.textFirst.setText(patient.getSurname() + " " + patient.getOtherNames());
        holder.textSecond.setText(patient.getAddress());
        holder.textThird.setText(patient.getPhone());
        holder.missedVisit.setText(patient.getDateLastViralLoad());
        return convertView;
    }

}
