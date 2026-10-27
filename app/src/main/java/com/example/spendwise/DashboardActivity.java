package com.example.spendwise;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        UserStore store = new UserStore(this);
        String firstName = store.getName().split(" ")[0];
        TextView tvGreeting = findViewById(R.id.tvGreeting);
        tvGreeting.setText(getString(R.string.dashboard_greeting, firstName));

        bindModule(R.id.cardAdd, R.string.module_add_expense);
        bindModule(R.id.cardHistory, R.string.module_history);
        bindModule(R.id.cardBudget, R.string.module_budget);
        bindModule(R.id.cardReports, R.string.module_reports);
        bindModule(R.id.cardCategories, R.string.module_categories);

        findViewById(R.id.cardProfile).setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));
    }

    private void bindModule(int cardId, int nameRes) {
        findViewById(cardId).setOnClickListener(v ->
                Toast.makeText(this,
                        getString(R.string.msg_coming_soon, getString(nameRes)),
                        Toast.LENGTH_SHORT).show());
    }
}