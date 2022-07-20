package com.suhail.dotorsideapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.suhail.dotorsideapp.databinding.ActivityEmailCheckBinding;
import com.suhail.dotorsideapp.models.Doctor;

import java.util.Map;

public class EmailCheckActivity extends AppCompatActivity {
ActivityEmailCheckBinding binding;
FirebaseFirestore firestore;
private final static String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEmailCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize firestore object
        firestore=FirebaseFirestore.getInstance();

        //check if user is already logged in
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        //implement button next code
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //search about email in firestore database
                firestore.collection("doctors")
                        .document(binding.etEmail.getText().toString()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Doctor doctor=task.getResult().toObject(Doctor.class);

                                if(doctor!=null){

                                    //check if this is the first time for this doctor to login(must complete registration)
                                if(doctor.isFirstTime()){
                                     startActivity(new Intent(EmailCheckActivity.this,FirstTimeLoginActivity.class).putExtra("email",binding.etEmail.getText().toString()));
                                    }
                                    else
                                        startActivity(new Intent(EmailCheckActivity.this,LoginActivity.class).putExtra("email",binding.etEmail.getText().toString()));
                            }
                            else
                                        Toast.makeText(EmailCheckActivity.this, "Wrong Email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}