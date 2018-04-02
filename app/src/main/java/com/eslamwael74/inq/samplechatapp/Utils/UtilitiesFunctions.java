package com.eslamwael74.inq.samplechatapp.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by EslamWael74 on 3/28/2018.
 */
public class UtilitiesFunctions {

    public static String timeStampToDate(long timeStamp) {
        Timestamp timestamp = new Timestamp(timeStamp * 1000);
        Date date = new Date(timestamp.getTime());
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String timeStampToTime(long timeStamp) {
        Timestamp timestamp = new Timestamp(timeStamp * 1000);
        Date date = new Date(timestamp.getTime());
        return new SimpleDateFormat("h:mm a").format(date);
    }


}
