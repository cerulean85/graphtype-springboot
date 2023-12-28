package com.graphtype.etc;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
    public static String getCurrentDateTime() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String formattedDate = dateFormat.format(currentDate);
        return formattedDate;
    }

    public static String getCurrentDateTimeKR() {
        // 한국 시간을 지정합니다.
        ZoneId zoneId = ZoneId.of("Asia/Seoul");

        // 현재 날짜와 시간을 한국 시간으로 가져옵니다.
        LocalDateTime now = LocalDateTime.now(zoneId);

        // 출력 형식을 지정합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }
}
