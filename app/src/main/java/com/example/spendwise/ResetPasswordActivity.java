package com.example.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPasswordActivity extends AppCompatActivity {

    private TextInputLayout tilPassword, tilConfirm;
    private TextInputEditText etPassword, etConfirm;
    private UserStore store;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = getIntent().getStringExtra(Constants.EXTRA_EMAIL);
        if (email == null) {            // opened without going through Forgot Password
            finish();
            return;
        }

        store = new UserStore(this);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirm = findViewById(R.id.tilConfirm);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);

        findViewById(R.id.btnReset).setOnClickListener(v -> attemptReset());
    }

    private void attemptReset() {
        tilPassword.setError(null);
        tilConfirm.setError(null);

        String password = ValidationUtils.rawOf(etPassword);
        String confirm = ValidationUtils.rawOf(etConfirm);
        boolean valid = true;

        if (password.isEmpty()) {
            tilPassword.setError(getString(R.string.error_password_required));
            valid = false;
        } else if (!ValidationUtils.isValidPassword(password)) {
            tilPassword.setError(getString(R.string.error_password_weak));
            valid = false;
        }

        if (confirm.isEmpty()) {
            tilConfirm.setError(getString(R.string.error_confirm_required));
            valid = false;
        } else if (!confirm.equals(password)) {
            tilConfirm.setError(getString(R.string.error_password_mismatch));
            valid = false;
        }

        if (!valid) return;

        store.updatePassword(password);
        Toast.makeText(this, R.string.msg_password_updated, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        intent.putExtra(Constants.EXTRA_EMAIL, email);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}