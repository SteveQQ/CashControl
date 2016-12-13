package com.steveq.cashcontrol.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.adapters.ReceiptsAdapter;
import com.steveq.cashcontrol.database.CatalogsDataSource;
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.AlertListener;
import com.steveq.cashcontrol.interfaces.ItemOnLongClickListener;
import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Item;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;
import com.steveq.cashcontrol.ui.fragments.dialogs.SimpleAlertDialogFragment;

import java.util.ArrayList;

public class ReceiptsFragment extends Fragment implements ItemOnLongClickListener, AlertListener{

    private RecyclerView mRecyclerView;
    public ReceiptsAdapter mAdapter;


    public ReceiptsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(ReceiptsActivity.FRAGMENT_NAME, "Receipts");
        this.setArguments(bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipts, container, false);
        createRecyclerView(view);

        return view;
    }

    private void createRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.receiptsRecycler);
        mAdapter = new ReceiptsAdapter(this,((ReceiptsActivity)getActivity()).mQueriesController.commandExecute());

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLongClick(Item item) {

        Receipt receipt = (Receipt) item;
        SimpleAlertDialogFragment alertDialog = new SimpleAlertDialogFragment();
        alertDialog.setMessage("Are you sure deleting receipt?");
        alertDialog.show(getActivity().getFragmentManager(), SimpleAlertDialogFragment.TAG);

        Bundle bundle = new Bundle();
        bundle.putParcelable(SimpleAlertDialogFragment.ITEM_KEY, receipt);
        alertDialog.setArguments(bundle);

    }

    @Override
    public void reactOnAlert(Object... obj) {
        Receipt item = (Receipt) obj[0];

//        if(item instanceof Catalog) {
//            ArrayList<Receipt> receipts = ReceiptsDataSource.getInstance().readReceipts(((Catalog)item).getId());
//            for(Receipt rec : receipts){
//                ReceiptsDataSource.getInstance().deleteReceipt(rec);
//            }
//            CatalogsDataSource.getInstance().deleteCatalog((Catalog)item);
//        } else if (item instanceof Receipt){

        if(item != null){
            ReceiptsDataSource.getInstance().deleteReceipt((Receipt)item);
            double p = ReceiptsDataSource
                    .getInstance()
                    .priceSum();
            CatalogsDataSource.getInstance().updateCatalogSum(CatalogsActivity.currentCatalog, p);
            CatalogsActivity.currentCatalog.setPrice(p);
        }
    }
}
