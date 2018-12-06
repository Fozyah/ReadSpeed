package com.example.bobly.readspeed;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
Button sign_up;
Button login;
FirebaseAuth mAuth;
EditText userE ;
EditText userP ;
ProgressBar par;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        sign_up =findViewById(R.id.sign_up1);
        login = findViewById(R.id.login);
        userE= findViewById(R.id.userEmail);
        userP = findViewById(R.id.password);
        par = findViewById(R.id.par1);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (MainActivity.this,SignUp.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    public void login (){
        String email = userE.getText().toString().trim();
        String password = userP.getText().toString().trim();
        // check if email is empty
        if (email.isEmpty()) {
            userE.setError("Email is required");
            userE.requestFocus();
            return;
        }
        // check if email is not correct
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userE.setError(" please enter valid email");
            userE.requestFocus();
            return;
        }
        // check if password is empty
        if (password.isEmpty()) {
            userP.setError("Password is required");
            userP.requestFocus();
            return;
        }
        // length of pass word can not exceed 6
        if (password.length() < 6) {
            userP.setError("minimum length of pass word should be 6");
            userP.requestFocus();
            return;
        }
        par.setVisibility(View.VISIBLE);
mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        par.setVisibility(View.GONE);
        if (task.isSuccessful()){
           Intent intent = new Intent(MainActivity.this,MainPage.class);
           // add flag because if we do not add this when user press back from profile it will again come to the login activity
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
       }
       else {
           Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
       }
    }
});
    }

}
