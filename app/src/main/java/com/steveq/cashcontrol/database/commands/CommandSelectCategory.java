package com.steveq.cashcontrol.database.commands;

import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.interfaces.Command;
import com.steveq.cashcontrol.model.Receipt;

import java.util.ArrayList;

public class CommandSelectCategory implements Command {

    private String input;

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public ArrayList<Receipt> execute() {
        return ReceiptsDataSource.getInstance().selectCategory(input);
    }
}
