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
import com.google.firebase.firestore.FirebaseFirestore;
import com.suhail.dotorsideapp.databinding.ActivityFirstTimeLoginBinding;

public class FirstTimeLoginActivity extends AppCompatActivity {
ActivityFirstTimeLoginBinding binding;
private FirebaseAuth auth;
private FirebaseFirestore firestore;
private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFirstTimeLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize auth and firestore objects
        auth=FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        //check if intent is empty(to get email from previous screen)
        if(getIntent()!=null)
        email=getIntent().getStringExtra("email");

        //implement register button code
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get password from editText
                String password = binding.etPassword.getText().toString();
                String passwordConfirm = binding.etConfirmPassword.getText().toString();

                //check if user enter all required fields for register
                if(!(password.equals("") || password.equals(""))){
                    //check if both fields are matching
                    if(password.equals(passwordConfirm)){
                        //create new user operation
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //check if adding user operation was done successfully
                                if(task.isSuccessful()){
                                    firestore.collection("doctors").document(email).update("isFirstTime",false);
                                    Toast.makeText(FirstTimeLoginActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(FirstTimeLoginActivity.this,MainActivity.class));
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(FirstTimeLoginActivity.this, "error occurred during registration", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                        Toast.makeText(FirstTimeLoginActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(FirstTimeLoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
        });
    }
}