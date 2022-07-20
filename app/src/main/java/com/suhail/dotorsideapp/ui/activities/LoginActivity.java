package com.suhail.dotorsideapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.suhail.dotorsideapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize auth object
        auth=FirebaseAuth.getInstance();

        //check if user is already logged in
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(this,MainActivity.class));
        }

        //check if intent is not empty(to get email from previous screen)
        if(getIntent()!=null){
            binding.etEmail.setText(getIntent().getStringExtra("email"));
            binding.etPassword.requestFocus();
        }

        //implement login button code
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get email and password from editTexts
                String email=binding.etEmail.getText().toString();
                String password=binding.etPassword.getText().toString();

                //check if user enter all required fields to login
                if(!(email.equals("") || password.equals(""))){
                    //login operation
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //check if login is successful
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                fileList();
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                    Toast.makeText(LoginActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
            }
        });

    }
}