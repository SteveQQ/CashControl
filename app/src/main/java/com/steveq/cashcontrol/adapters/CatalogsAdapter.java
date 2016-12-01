package com.steveq.cashcontrol.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.interfaces.ActionListener;
import com.steveq.cashcontrol.interfaces.ItemOnClickListener;
import com.steveq.cashcontrol.interfaces.ItemOnLongClickListener;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.utilities.Converter;

import java.util.ArrayList;

public class CatalogsAdapter extends RecyclerView.Adapter<CatalogsAdapter.ViewHolder> {

    private final ItemOnLongClickListener mLongListener;
    private final ItemOnClickListener mClickListener;
    private ArrayList<Catalog> mCatalogs = null;
    private Converter mConverter;

    public CatalogsAdapter(ActionListener listener, ArrayList<Catalog> catalogs) {
        mLongListener = (ItemOnLongClickListener)listener;
        mClickListener = (ItemOnClickListener)listener;
        mCatalogs = catalogs;
        mConverter = new Converter();
    }

    public void setCatalogs(ArrayList<Catalog> catalogs) {
        mCatalogs = catalogs;
    }
    public void refreshData(){
        setCatalogs(CatalogsDataSource.getInstance().readCatalogs());
        notifyDataSetChanged();
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
        holder.catalogSum.setText(String.format("%s  %s", Double.toString(catalog.getPrice()), catalog.getCurrency()));
        holder.catalogDaterange.setText(String.format("From  %s  To  %s",
                                                        mConverter.timestampToString(catalog.getStartTime()),
                                                        mConverter.timestampToString(catalog.getEndTime())));
        holder.bindLongListener(mCatalogs.get(position), mLongListener);
        holder.bindClickListener(mCatalogs.get(position), mClickListener);

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

        public void bindLongListener(final Catalog catalog, final ItemOnLongClickListener longListener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.onLongClick(catalog);
                    return true;
                }
            });
        }

        public void bindClickListener(final Catalog catalog, final ItemOnClickListener clickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(catalog);
                }
            });
        }
    }

}
