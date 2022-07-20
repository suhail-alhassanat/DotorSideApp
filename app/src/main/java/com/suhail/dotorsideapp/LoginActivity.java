package com.suhail.dotorsideapp;

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

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
ActivityLoginBinding binding;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(this,MainActivity.class));
        }

        if(getIntent()!=null){
            binding.etEmail.setText(getIntent().getStringExtra("email"));
            binding.etPassword.requestFocus();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.etEmail.getText().toString();
                String password=binding.etPassword.getText().toString();

                if(!(email.equals("") || password.equals(""))){
                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                fileList();
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