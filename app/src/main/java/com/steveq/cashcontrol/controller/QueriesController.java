package com.steveq.cashcontrol.controller;

import com.steveq.cashcontrol.database.commands.NullCommand;
import com.steveq.cashcontrol.interfaces.Command;
import com.steveq.cashcontrol.model.Receipt;

import java.util.ArrayList;

public class QueriesController {

    Command queryCommand;

    public QueriesController() {
        Command nullCommand = new NullCommand();
    }

    public void setQueryCommands(Command queryCommands) {
        this.queryCommand = queryCommands;
    }

    public ArrayList<Receipt> commandExecute(){
        return queryCommand.execute();
    }

}
