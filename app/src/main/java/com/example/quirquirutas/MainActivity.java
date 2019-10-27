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
    private static final String TAG = "Antut";
    private EditText textcorreo, textpassword;
    private ProgressDialog progressDialog;
    private Button mLoginbtn;

    //Declaracion de objeto Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        mAuth = FirebaseAuth.getInstance();
        //referencias view
        textcorreo =  findViewById(R.id.txt_correo);
        textpassword =  findViewById(R.id.txt_password);
        mLoginbtn =  findViewById(R.id.btn_ingresar);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {

                    startActivity(new Intent(MainActivity.this, RutasActivity.class));

                } else {
                     Toast.makeText(MainActivity.this, "Datos Incorrectos", Toast.LENGTH_SHORT).show();

                }
            }
        };

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUsuario();
            }
        });
        progressDialog = new ProgressDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void registrarUsuario(View view) {
        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = textcorreo.getText().toString().trim();
        //trim() elimina espacios de la cadena
        String password = textpassword.getText().toString().trim();
        //Verificamos que las cajas de texto no esten vacias
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debe Ingresar un Correo Valido"
                    , Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debe Ingresar una Contraseña"
                    , Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Realizando Registro");
        progressDialog.show();
        //Creando un nuevo Usuario
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Registro Exitoso.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                //si el usuario ya esta creado
                                Toast.makeText(MainActivity.this, "El usuario ya existe",
                                        Toast.LENGTH_SHORT).show();
                            } else {
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

    private void LoginUsuario() {
        String email = textcorreo.getText().toString();
        String password = textpassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Debe Ingresar un Correo Valido"
                    , Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Debe Ingresar una Contraseña"
                    , Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
}
