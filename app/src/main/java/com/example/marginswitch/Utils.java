package com.example.marginswitch;

import android.app.Activity;

public class Utils {
    public final static int THEME_BIG = 0;
    public final static int THEME_MED = 1;
    public final static int THEME_SMALL = 2;
    private static int sTheme;

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case THEME_BIG:
                activity.setTheme(R.style.ThemeBig);
                break;
            case THEME_MED:
                activity.setTheme(R.style.ThemeMed);
                break;
            case THEME_SMALL:
                activity.setTheme(R.style.ThemeSmall);
                break;
        }
    }

    public static int getsTheme() {
        return sTheme;
    }
}
