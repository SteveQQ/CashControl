package com.steveq.cashcontrol.interfaces;

import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Item;

public interface ItemOnLongClickListener extends ActionListener{
    void onLongClick(Item item);
}
