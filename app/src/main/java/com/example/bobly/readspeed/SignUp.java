package com.example.bobly.readspeed;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {
    EditText username, editEmail, editPassword;
    Button save;
    private FirebaseAuth mAuth;
    ProgressBar par;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        par =findViewById(R.id.par);
        editEmail = findViewById(R.id.editText2);
        editPassword = findViewById(R.id.editText3);

        save = findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
     // method for  register user
    public void register() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        // check if email is empty
        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        // check if email is not correct
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(" please enter valid email");
            editEmail.requestFocus();
            return;
        }
        // check if password is empty
        if (password.isEmpty()) {
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        // length of pass word can not exceed 6
        if (password.length() < 6) {
            editPassword.setError("minimum length of pass word should be 6");
            editPassword.requestFocus();
            return;
        }
        par.setVisibility(View.VISIBLE);
        // listener of auth in Firebase  to register user in data base
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                par.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "user register successful ", Toast.LENGTH_LONG).show();
                    // check if the user already register
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "you are already register", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }});

    }

}
