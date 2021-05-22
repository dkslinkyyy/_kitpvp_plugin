package eu.tribusmc.tribuskitpvp.miscelleanous;

import java.util.concurrent.TimeUnit;

public class TimeFormat {


    public static String formated(long millis)  {
        int day = (int)TimeUnit.SECONDS.toDays(millis);
        long hours = TimeUnit.SECONDS.toHours(millis) - (day *24);
        long minutes = TimeUnit.SECONDS.toMinutes(millis) - (TimeUnit.SECONDS.toHours(millis)* 60);
        long second = TimeUnit.SECONDS.toSeconds(millis) - (TimeUnit.SECONDS.toMinutes(millis) *60);


        String a = (hours == 1) ? " timme" : " timmar";
        String b = (minutes == 1) ? " minut" : " minuter";
        String c = (second == 1) ? " sekund" : " sekunder";

        String seconds = second + c;
        String minute = minutes + b;
        String hour = hours + a;
        String hoursMinutes = hours +  a + " och " + minutes + b;
        String minutesSeconds = minutes + b + " och " + second + c;

        if(hours <= 0) {
            if(minutes <= 0) {
                return seconds;
            }else{
                if(second <=0) {
                    return minute;
                }
            }
            return minutesSeconds;
        }else {
            if(minutes <=0) {
                return hour;
            }
        }


        return hoursMinutes;
    }
}
