package com.crown.proadmin;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GeneralStatics {
    public static void showKeyBoard(EditText editText) {
        Context context = editText.getContext();
        editText.post(() -> {
            editText.requestFocusFromTouch();
            InputMethodManager lManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            lManager.showSoftInput(editText, 0);
        });
    }

    public static void hideKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String JSONParsingStringFromObject(JSONObject jsonObject, String paramName) {
        try {
            return jsonObject.getString(paramName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static JSONArray JSONParsingArrayFromString(String string) {
        try {
            return new JSONArray(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public static JSONObject JSONParsingObjectFromString(String string) {
        try {
            return new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static int JSONParsingIntFromObject(JSONObject jsonObject, String paramName) {
        try {
            return jsonObject.getInt(paramName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static JSONObject JSONParsingObjectFromArray(JSONArray jsonArray, int index) {
        try {
            return jsonArray.getJSONObject(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static JSONArray JSONParsingArrayFromObject(JSONObject jsonObject, String paramName) {
        try {
            return jsonObject.getJSONArray(paramName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }
}
