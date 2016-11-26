package com.steveq.cashcontrol.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.model.Catalog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CatalogsAdapter extends RecyclerView.Adapter<CatalogsAdapter.ViewHolder> {

    private ArrayList<Catalog> mCatalogs = null;

    public CatalogsAdapter(ArrayList<Catalog> catalogs) {
        mCatalogs = catalogs;
    }

    @Override
    public CatalogsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_card, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogsAdapter.ViewHolder holder, int position) {

        Catalog catalog = mCatalogs.get(position);
        holder.catalogName.setText(catalog.getName());
        holder.catalogSum.setText(Double.toString(catalog.getSum()));
        String startRange = convertTimestamp(catalog.getStartTime());
        String endRange = convertTimestamp(catalog.getEndTime());
        holder.catalogDaterange.setText(String.format("From %s To %s", startRange, endRange));

    }

    private String convertTimestamp(int startTime) {
        Date date = new Date(startTime);
        return String.format("%02d/%02d/%d", date.getDay(), date.getMonth(), date.getYear());
    }

    @Override
    public int getItemCount() {
        return mCatalogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView catalogName;
        TextView catalogSum;
        TextView catalogDaterange;

        public ViewHolder(View itemView) {
            super(itemView);

            catalogName = (TextView) itemView.findViewById(R.id.catalogNameTextView);
            catalogSum = (TextView) itemView.findViewById(R.id.catalogSumTextView);
            catalogDaterange = (TextView) itemView.findViewById(R.id.catalogDaterangeTextView);
        }
    }

}
