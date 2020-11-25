package com.poblete.punto_picada;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView puntopicadaTextView, byTextView, DevTextView;
    ImageView logoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //Referencias
        puntopicadaTextView = findViewById(R.id.puntopicadaTextView);
        byTextView = findViewById(R.id.byTextView);
        DevTextView = findViewById(R.id.DevTextView);
        logoImageView = findViewById(R.id.logoImageView);
        //si el usuario ya inicio sesion
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        }else {
            //Animaciones
            animationTimeDelay(); //despues de 3 seg pasa al loginActivity
        }
        
    }//Fin del onCreate (para no perder de vista)

    public void animationTimeDelay(){
        //Agregar animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
        logoImageView.setAnimation(animacion1);
        puntopicadaTextView.setAnimation(animacion1);
        byTextView.setAnimation(animacion2);
        DevTextView.setAnimation(animacion2);
        //Después de un tiempo pasar a otra actividad
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                //Arreglo de animaciones
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View, String>(logoImageView, "logoImageTransition");
                pairs[1] = new Pair<View, String>(puntopicadaTextView, "bienvenidoTransition");

                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}