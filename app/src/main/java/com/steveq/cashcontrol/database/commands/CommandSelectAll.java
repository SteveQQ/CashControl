package com.steveq.cashcontrol.database.commands;

import android.content.Context;

import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.Command;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;

import java.util.ArrayList;

public class CommandSelectAll implements Command{

    @Override
    public ArrayList<Receipt> execute() {
        return ReceiptsDataSource.getInstance().readReceipts(CatalogsActivity.currentCatalog.getId());
    }
}
