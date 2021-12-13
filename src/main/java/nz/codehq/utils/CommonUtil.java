package nz.codehq.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    public static final String CARD_SERVICE_DATE_FORMAT_YYYYMMDD_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
        return !(date.before(min) || date.after(max));
    }

    public static String getDateIso8601(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(CARD_SERVICE_DATE_FORMAT_YYYYMMDD_ISO8601);
        String temp = formatter.format(date);
        return temp.substring(0, 22) + ":" + temp.substring(22);
    }
}
