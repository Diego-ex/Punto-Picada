package com.poblete.punto_picada;
/*
    Version by Draigh on 25/11/2020.
 */
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    TextView bienvenidoLabel, continuarLabel, nuevoUsuario;
    ImageView signUpImageView;
    TextInputLayout nameTextField, usuarioSignUpTextField, contrasenaTextField;
    MaterialButton inicioSesion;
    TextInputEditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference rDatabase;
    private String name = "";
    private String email = "";
    private String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //referencias
        referencias();

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionBack();
            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        connectFirebaseDataBase();

    }//Fin del onCreate (para no perder de vista)

    public void referencias(){
        //Referencias
        signUpImageView = findViewById(R.id.signUpImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        nameTextField = findViewById(R.id.nameTextField);
        usuarioSignUpTextField = findViewById(R.id.usuarioSignUpTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
    }

    public void connectFirebaseDataBase(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        rDatabase = database.getReference();
        //msjToast("Conectado a firebase");
    }

    public void validate(){
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (name.isEmpty()){
            nameEditText.setError("Este campo no debe quedar vacio");
            return;
        }else {
            nameEditText.setError(null);
        }

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
        // determino si las contraseñas ingresadas son iguales
        if (!confirmPassword.equals(password)){
            confirmPasswordEditText.setError("Deben ser iguales");
            return;
        }else {
            //método de registrar en firebase
            registrar(name, email, password);
        }
    }

    public void registrar(final String name, final String email, final String password){
        mAuth.createUserWithEmailAndPassword( email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //si se registró con éxito
                        if (task.isSuccessful()){

                            Map<String, Object> UserMap = new HashMap<>();
                            UserMap.put("Nombre", name);
                            UserMap.put("E-mail", email);
                            UserMap.put("Contraseña", password);

                            String id = mAuth.getCurrentUser().getUid();

                            rDatabase.child("Usuarios").child(id).setValue(UserMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                //si los datos se crearon correctamente
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {
                                    if (task2.isSuccessful()){
                                        Intent intent = new Intent(SignUpActivity.this, UserActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        msjToast("Error en los datos");
                                    }
                                }
                            });
                        }else {
                            msjToast("Falló en registrarse");
                        }
                    }
                });
    }
    /*public void limpiarForm(){
        nameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
    }*/
    @Override
    public void onBackPressed(){
        //cuando presionen la flecha atras
        transitionBack();
    }
    public void transitionBack(){
        //animacion para ir a la activity login
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);

        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(signUpImageView, "logoImageTransition");
        pairs[1] = new Pair<View, String>(bienvenidoLabel, "bienvenidoTransition");
        pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTransition");
        pairs[3] = new Pair<View, String>(usuarioSignUpTextField, "emailInputTextTransition");
        pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTransition");
        pairs[5] = new Pair<View, String>(inicioSesion, "btnSignInTransition");
        pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTransition");

        startActivity(intent);
        finish();
    }

    public void msjToast(String mensaje){
        Toast.makeText(getApplication(),mensaje,Toast.LENGTH_LONG).show();
    }
}