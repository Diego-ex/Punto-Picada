package com.poblete.punto_picada;
/*
    Version by Draigh on 25/11/2020.
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SocialActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton fbImageButton, instaImageButton;
    private TextView picadaTitle, redesTextView3, socialByTextView, devTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        //
        references();
        //
        clickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fbImageButton:
                Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/New.PuntoPicada"));
                startActivity(fbIntent);
                break;
            case R.id.instaImageButton:
                Intent instaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/punto.picada1/"));
                startActivity(instaIntent);
                break;
        }
    }

    private void references(){
        //
        fbImageButton = findViewById(R.id.fbImageButton);
        instaImageButton = findViewById(R.id.instaImageButton);
        //
        picadaTitle = findViewById(R.id.picadaTitle);
        redesTextView3 = findViewById(R.id.redesTextView3);
        socialByTextView = findViewById(R.id.socialByTextView);
        devTextView2 = findViewById(R.id.devTextView2);
    }

    private void clickListener(){
        fbImageButton.setOnClickListener(this);
        instaImageButton.setOnClickListener(this);
    }
}