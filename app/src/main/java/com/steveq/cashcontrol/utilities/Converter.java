package com.steveq.cashcontrol.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

    SimpleDateFormat sdf;

    public Converter() {
        sdf = new SimpleDateFormat("dd/mm/yyyy");
    }

    public String timestampToString(long timestamp){
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    public long stringToTimestamp(String input){
        Date date = new Date();
        try {
            date = sdf.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();

    }
}
