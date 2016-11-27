package com.steveq.cashcontrol.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.utilities.Converter;

import java.util.ArrayList;

public class CatalogsAdapter extends RecyclerView.Adapter<CatalogsAdapter.ViewHolder> {

    private ArrayList<Catalog> mCatalogs = null;
    private Converter mConverter;

    public CatalogsAdapter(ArrayList<Catalog> catalogs) {
        mCatalogs = catalogs;
        mConverter = new Converter();
    }

    public void setCatalogs(ArrayList<Catalog> catalogs) {
        mCatalogs = catalogs;
    }

    @Override
    public CatalogsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CatalogsAdapter.ViewHolder holder, int position) {

        Catalog catalog = mCatalogs.get(position);
        holder.catalogName.setText(catalog.getName());
        holder.catalogSum.setText(String.format("%s  %s", Double.toString(catalog.getSum()), catalog.getCurrency()));
        holder.catalogDaterange.setText(String.format("From  %s  To  %s",
                                                        mConverter.timestampToString(catalog.getStartTime()),
                                                        mConverter.timestampToString(catalog.getEndTime())));

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
