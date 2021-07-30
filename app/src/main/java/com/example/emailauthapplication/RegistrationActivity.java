package com.example.emailauthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText regFullName, regEmail, regPhone,regPass,regConfPass;
    private Button registerBtn, gotoLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regFullName= findViewById(R.id.registerFullName);
        regEmail= findViewById(R.id.registerEmail);
        regPass= findViewById(R.id.registerPass);
        regPhone= findViewById(R.id.registerPhone);
        regConfPass= findViewById(R.id.confPass);

        registerBtn= findViewById(R.id.regBtn);
        gotoLogin= findViewById(R.id.gotoLogin);

        fAuth= FirebaseAuth.getInstance();

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the date from the form

                String  fullName= regFullName.getText().toString();
                String  email= regEmail.getText().toString();
                String  phone= regPhone.getText().toString();
                String  password= regPass.getText().toString();
                String  confPass= regConfPass.getText().toString();

                if (fullName.isEmpty()){
                    regFullName.setError("Full Name is Required");
                }

                if (email.isEmpty()){
                    regEmail.setError("Email is Required");
                }
                if (phone.isEmpty()){
                    regPhone.setError("Phone Number is Required");
                }

                if (password.isEmpty()){
                    regPass.setError("Password is Required");
                }

                if (confPass.isEmpty()){
                    regConfPass.setError("Confirmation of Password is Required");
                }

                if (!password.equals(confPass)){
                    regConfPass.setError("Password Do not Match");
                }

                //data is validated
                //register the user using firebase

//                Toast.makeText(RegistrationActivity.this, "Data Validated", Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // send user to next page

                        Toast.makeText(RegistrationActivity.this, "Data Validated and Registration complete Successfully.", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });






            }
        });
    }
}