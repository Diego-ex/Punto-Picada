package com.poblete.punto_picada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    TextView titleTextView, emailTextView;
    MaterialButton btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        titleTextView = findViewById(R.id.titleTextView);
        emailTextView = findViewById(R.id.emailTextView);
        btnLogOut = findViewById(R.id.btnLogOut);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            emailTextView.setText(user.getEmail());
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}