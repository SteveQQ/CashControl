package com.steveq.cashcontrol.utilities;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {

    private FileUtils(){}

    public static boolean isExternalStorageAvailable(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static boolean isExternalWritable(){
        String state = Environment.getExternalStorageState();
        return !Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

}
