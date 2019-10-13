package com.example.quirquirutas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.nio.channels.InterruptedByTimeoutException;

public class MainActivity extends AppCompatActivity {
     private EditText textcorreo,textpassword;
     private ProgressDialog progressDialog;
    //Declaracion de objeto Firebase
     FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        //referencias view
        textcorreo = (EditText) findViewById(R.id.txt_correo);
        textpassword = (EditText) findViewById(R.id.txt_password);
        progressDialog = new ProgressDialog(this);
    }
    public void registrarUsuario(View view){
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = textcorreo.getText().toString().trim();
        //trim() elimina espacios de la cadena
        String password = textpassword.getText().toString().trim();
        //Verificamos que las cajas de texto no esten vacias
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Debe Ingresar un Correo Valido"
            ,Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Debe Ingresar una Contraseña"
                    ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Realizando Registro");
        progressDialog.show();
        //Creando un nuevo Usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Registro Exitoso.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                //si el usuario ya esta creado
                                Toast.makeText(MainActivity.this, "El usuario ya existe",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Error al Registrar Usuario",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();

                        // ...
                    }
                });
    }
    //Loguear
    public void siguiente(View view){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = textcorreo.getText().toString().trim();
        //trim() elimina espacios de la cadena
        String password = textpassword.getText().toString().trim();
        //Verificamos que las cajas de texto no esten vacias
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Debe Ingresar un Correo Valido"
                    ,Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Debe Ingresar una Contraseña"
                    ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Realizando Registro");
        progressDialog.show();
        //Logear  Usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Bienvenido.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MapsActivity1.class);
                            startActivity(intent);
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                //si el usuario ya esta creado
                                Toast.makeText(MainActivity.this, "contraseña erronea",
                                        Toast.LENGTH_SHORT).show();

                            }else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Error en los datos",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();

                        // ...
                    }
                });
        //Intent i = new Intent(this,MapsActivity1.class);
        //startActivity(i);
    }
}
