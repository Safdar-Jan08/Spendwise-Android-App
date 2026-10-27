package com.example.spendwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilEmail;
    private TextInputEditText etEmail;
    private UserStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        store = new UserStore(this);
        tilEmail = findViewById(R.id.tilEmail);
        etEmail = findViewById(R.id.etEmail);

        findViewById(R.id.btnSend).setOnClickListener(v -> sendRecovery());
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void sendRecovery() {
        tilEmail.setError(null);
        final String email = ValidationUtils.textOf(etEmail);

        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.error_email_required));
            return;
        }
        if (!ValidationUtils.isValidEmail(email)) {
            tilEmail.setError(getString(R.string.error_email_invalid));
            return;
        }
        if (!store.isEmailRegistered(email)) {
            tilEmail.setError(getString(R.string.error_email_not_found));
            return;
        }

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_recovery_title)
                .setMessage(getString(R.string.dialog_recovery_message, email))
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_open_reset, (dialog, which) -> {
                    Intent intent = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                    intent.putExtra(Constants.EXTRA_EMAIL, email);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }
}