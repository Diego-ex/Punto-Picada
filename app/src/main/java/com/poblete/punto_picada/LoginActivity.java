package com.poblete.punto_picada;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView bienvenidoLabel, continuarLabel, nuevoUsuario, olvidasteContra;
    ImageView loginImageView;
    TextInputLayout usuarioTextField, contrasenaTextField;
    MaterialButton inicioSesion;
    TextInputEditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    //Referencias
        loginImageView = findViewById(R.id.loginImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioTextField = findViewById(R.id.usuarioTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        olvidasteContra = findViewById(R.id.olvidasteContra);

        mAuth = FirebaseAuth.getInstance();

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //animacion para ir a la activity login
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);

                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(loginImageView, "logoImageTransition");
                pairs[1] = new Pair<View, String>(bienvenidoLabel, "bienvenidoTransition");
                pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTransition");
                pairs[3] = new Pair<View, String>(usuarioTextField, "emailInputTextTransition");
                pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTransition");
                pairs[5] = new Pair<View, String>(inicioSesion, "btnSignInTransition");
                pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTransition");

                startActivity(intent);
                finish();
            }
        });

        olvidasteContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });


        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }//Fin del onCreate (para no perder de vista)


    public void validate(){

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo invalido");
            return;
        }else {
            emailEditText.setError(null);
        }

        // si la contraseña está vacía o tiene menos de 8 caracteres
        if (password.isEmpty() || password.length() < 8){
            passwordEditText.setError("Se necesitan más de 8 caracteres");
            return;
        }else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            //quiero que contenga un número esta contraseña
            passwordEditText.setError("Al menos un número");
            return;
        }else {
            //si no hay error se continua con la lógica
            passwordEditText.setError(null);
        }
        //método de registrar en firebase
        iniciarSesion(email, password);
    }

    public void iniciarSesion(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            msjToast("E-mail y Contraseña equivocadas, Trata de nuevo!");
                        }
                    }
                });
    }






    public void msjToast(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();
    }


}