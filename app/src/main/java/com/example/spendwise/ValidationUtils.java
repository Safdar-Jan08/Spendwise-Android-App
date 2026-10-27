package com.example.spendwise;

import android.text.TextUtils;
import android.util.Patterns;

import com.google.android.material.textfield.TextInputEditText;

public final class ValidationUtils {

    private ValidationUtils() { }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /** At least 6 characters, containing at least one letter and one digit. */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) return false;
        boolean hasLetter = false, hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = true;
            if (Character.isDigit(c)) hasDigit = true;
        }
        return hasLetter && hasDigit;
    }

    /** Trimmed text (for name / email). */
    public static String textOf(TextInputEditText field) {
        return field.getText() == null ? "" : field.getText().toString().trim();
    }

    /** Untrimmed text (for passwords). */
    public static String rawOf(TextInputEditText field) {
        return field.getText() == null ? "" : field.getText().toString();
    }
}