package com.example.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilEmail, tilPassword, tilConfirm;
    private TextInputEditText etName, etEmail, etPassword, etConfirm;
    private UserStore store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        store = new UserStore(this);

        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirm = findViewById(R.id.tilConfirm);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);

        findViewById(R.id.btnCreate).setOnClickListener(v -> attemptRegister());
        findViewById(R.id.btnBackLogin).setOnClickListener(v -> finish());
    }

    private void attemptRegister() {
        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirm.setError(null);

        String name = ValidationUtils.textOf(etName);
        String email = ValidationUtils.textOf(etEmail);
        String password = ValidationUtils.rawOf(etPassword);
        String confirm = ValidationUtils.rawOf(etConfirm);
        boolean valid = true;

        if (name.isEmpty()) {
            tilName.setError(getString(R.string.error_name_required));
            valid = false;
        } else if (name.length() < 3) {
            tilName.setError(getString(R.string.error_name_short));
            valid = false;
        }

        if (email.isEmpty()) {
            tilEmail.setError(getString(R.string.error_email_required));
            valid = false;
        } else if (!ValidationUtils.isValidEmail(email)) {
            tilEmail.setError(getString(R.string.error_email_invalid));
            valid = false;
        } else if (store.isEmailRegistered(email)) {
            tilEmail.setError(getString(R.string.error_email_exists));
            valid = false;
        }

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

        store.saveUser(name, email, password);
        Toast.makeText(this, R.string.msg_register_success, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.putExtra(Constants.EXTRA_EMAIL, email);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}