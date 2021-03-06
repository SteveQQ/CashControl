package com.steveq.cashcontrol.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.ActionListener;
import com.steveq.cashcontrol.interfaces.ItemOnLongClickListener;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.utilities.Converter;

import java.util.ArrayList;

public class ReceiptsAdapter extends RecyclerView.Adapter<ReceiptsAdapter.ViewHolder> {

    private final ItemOnLongClickListener mLongListener;
    private ArrayList<Receipt> mReceipts = null;
    private Converter mConverter;

    public ReceiptsAdapter(ActionListener longListener, ArrayList<Receipt> data) {
        mLongListener = (ItemOnLongClickListener)longListener;
        mConverter = new Converter();
        refreshData(data);
    }

    public void setReceipts(ArrayList<Receipt> receipts) {
        mReceipts = receipts;
    }
    public void refreshData(ArrayList<Receipt> data){
        setReceipts(data);
        notifyDataSetChanged();
    }
    @Override
    public ReceiptsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptsAdapter.ViewHolder holder, int position) {

        Receipt receipt = mReceipts.get(position);

        holder.receiptName.setText(receipt.getName());
        holder.receiptCategory.setText(receipt.getCategory());
        holder.receiptPrice.setText(String.format("%.2f %s", receipt.getPrice(), CatalogsActivity.currentCatalog.getCurrency()));
        holder.receiptDate.setText(mConverter.timestampToString(receipt.getDate()));
        holder.bindLongListener(receipt, mLongListener);
    }

    @Override
    public int getItemCount() {
        return mReceipts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView receiptName;
        TextView receiptCategory;
        TextView receiptPrice;
        TextView receiptDate;

        public ViewHolder(View itemView) {
            super(itemView);

            receiptName = (TextView) itemView.findViewById(R.id.receiptNameTextView);
            receiptCategory = (TextView) itemView.findViewById(R.id.receiptCategoryTextView);
            receiptPrice = (TextView) itemView.findViewById(R.id.receiptPriceTextView);
            receiptDate = (TextView) itemView.findViewById(R.id.receiptDateTextView);
        }

        public void bindLongListener(final Receipt receipt, final ItemOnLongClickListener longListener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.onLongClick(receipt);
                    return true;
                }
            });
        }

    }

}
