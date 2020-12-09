package com.poblete.punto_picada;
/*
    Version by Draigh on 25/11/2020.
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RatingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView picadaTitle, tyTextView, questValora;
    TextInputEditText emailValorar, comentarioValorar;
    MaterialButton sendMailButton;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        //
        references();
        //
        userEmailValorar();
        //
        sendMailButton.setOnClickListener(this);
    }

    public void references(){
        picadaTitle = findViewById(R.id.picadaTitle);
        tyTextView = findViewById(R.id.tyTextView);
        questValora = findViewById(R.id.questValora);
        emailValorar = findViewById(R.id.emailValorar);
        comentarioValorar = findViewById(R.id.comentarioValorar);
        sendMailButton = findViewById(R.id.sendMailButton);
        ratingBar = findViewById(R.id.ratingBar);
    }

    public void userEmailValorar(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            emailValorar.setText(user.getEmail());
        }
    }


    public void sendMail(String comentario){

        String [] TO = {"diego.pobletesanhueza@gmail.com"};
        String [] CC = {"punto.picada1@gmail.com"};
        //
        Intent sendEmailIntent = new Intent(Intent.ACTION_SENDTO);
        //
        sendEmailIntent.setData(Uri.parse("mailTo:"));
        sendEmailIntent.setType("text/plain");
        sendEmailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        sendEmailIntent.putExtra(Intent.EXTRA_CC, CC);
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, "Punto Picada App - - - - Comentarios/Sugerencias");
        sendEmailIntent.putExtra(Intent.EXTRA_TEXT, comentario);



        startActivity(Intent.createChooser(sendEmailIntent, "Enviar correo..."));
        Log.e("Enviar Correo", "Se agradece su opinión, muchas gracias por su tiempo en esta app");

    }

    @Override
    public void onClick(View v) {
        sendMail(emailValorar.getText().toString().trim()+":\n"+
                comentarioValorar.getText().toString().trim()+":\n"+ratingBar.getRating()+" En valoración a la app");
    }
}