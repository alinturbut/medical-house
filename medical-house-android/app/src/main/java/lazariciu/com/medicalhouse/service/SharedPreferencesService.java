package lazariciu.com.medicalhouse.service;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesService {
    public static final String MEDICAL_HOUSE_PREFS = "lazariciu.com.medicalhouse";
    public static final String LOGGED_USER_PREFS = "user.logged";
    public static final String LOGGED_USER_EMAIL = "user.logged.email";

    public static void saveLoggedUser(Context ctx, String json) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(MEDICAL_HOUSE_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(LOGGED_USER_PREFS, json);
        editor.apply();
    }

    public static void saveLoggedUserEmail(Context ctx, String json) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(MEDICAL_HOUSE_PREFS, Context.MODE_PRIVATE).edit();
        editor.putString(LOGGED_USER_EMAIL, json);
        editor.apply();
    }

    public static void removeLoggedUserSession(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(MEDICAL_HOUSE_PREFS, Context.MODE_PRIVATE);
        prefs.edit().remove(LOGGED_USER_PREFS).apply();
    }

    public static String getLoggedUser(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(MEDICAL_HOUSE_PREFS, Context.MODE_PRIVATE);

        return prefs.getString(LOGGED_USER_PREFS, "");
    }

    public static String getLoggedUserEmail(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(MEDICAL_HOUSE_PREFS, Context.MODE_PRIVATE);

        return prefs.getString(LOGGED_USER_EMAIL, "");
    }
}
