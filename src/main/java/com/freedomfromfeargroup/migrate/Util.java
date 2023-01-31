package com.freedomfromfeargroup.migrate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static LocalDateTime toLocalDateTime(Integer epoch) {
        if (epoch == null) return null;
        if (epoch == 0) {
            return LocalDateTime.of(2007, 1, 1, 0, 0);
        } else {
            return LocalDateTime.ofEpochSecond(epoch,0, ZoneOffset.UTC);
        }
    }


    public static String convertMessage(String message) {
        if (message == null) {
            return null;
        }

        return message
                .replaceAll("\\[url](.+)\\[/url]", "[url=$1]$1[/url]");
    }
}
