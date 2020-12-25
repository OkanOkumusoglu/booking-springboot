package com.ok;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationChecks {


    public static boolean isValidEmailAddress(String email) {
        final String EMAIL_REGEX =
                "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidTime(String time) {
        final String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

        Pattern p = Pattern.compile(regex);

        if (time == null) {
            return false;
        }

        Matcher m = p.matcher(time);

        return m.matches();
    }
}
