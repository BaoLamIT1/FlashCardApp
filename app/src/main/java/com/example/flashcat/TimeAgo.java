package com.example.flashcat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo {
    public static String convertTimeAgo(String strDate){
        String time = "";
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date pastTime = dateFormat.parse(strDate);

            Date nowaTime = new Date();

            long dateDiff = nowaTime.getTime() - pastTime.getTime();
            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            //convert datetime
            if(second == 1){
                time = second + " second ago";
            }
            else if (second > 1 && second < 60){
                time = second + " seconds ago";
            }
            else if (minute == 1){
                time = minute + " minute ago";
            }
            else if (minute > 1 && minute < 60){
                time = minute + " minutes ago";
            }
            else if(hour == 1){
                time = hour + " hour ago";
            }
            else if (hour > 1 && hour < 60){
                time = hour + " hours ago";
            }
            else if(day == 1){
                time = day + " day ago";
            }
            else if(day >=7){
                if(day == 30)
                {
                    time = (day/30) + " month ago";
                }
                else if(day > 30 && day <= 365){
                    time = (day/30) + " months ago";
                } else if (day == 365) {
                    time = (day/365) + " year ago";
                } else if (day > 365) {
                    time = (day/365) + " years ago";
                } else if (day == 7) {
                    time = (day/7) + " week ago";
                }
                else {
                    time = (day/7) + " weeks ago";
                }
            } else if (day < 7 && day>1) {
                time = day + " days ago";
            }
            else {
                return "just now";
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return time;
    }
}
