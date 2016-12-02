package com.steveq.cashcontrol.interfaces;

import com.steveq.cashcontrol.model.Receipt;

import java.util.ArrayList;

public interface Command {
    public ArrayList<Receipt> execute();
}
