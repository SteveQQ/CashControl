package com.steveq.cashcontrol.database.commands;

import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.Command;
import com.steveq.cashcontrol.model.Receipt;

import java.util.ArrayList;

public class CommandSortByName implements Command {
    @Override
    public ArrayList<Receipt> execute() {
        return ReceiptsDataSource.getInstance().sortByName();
    }
}
