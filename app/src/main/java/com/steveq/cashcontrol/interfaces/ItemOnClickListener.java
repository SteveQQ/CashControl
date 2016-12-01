package com.steveq.cashcontrol.interfaces;

import com.steveq.cashcontrol.model.Catalog;
import com.steveq.cashcontrol.model.Item;

public interface ItemOnClickListener extends ActionListener {
    void onClick(Item item);
}
