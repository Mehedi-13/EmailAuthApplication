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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

    public class ResetPasswordActivity extends AppCompatActivity {

    private EditText userPass, userConfirmPass;
    private Button saveBtn;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userConfirmPass= findViewById(R.id.confNewPass);
        userPass= findViewById(R.id.enterNewPass);

        user=FirebaseAuth.getInstance().getCurrentUser();

        saveBtn= findViewById(R.id.resetPassBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPass.getText().toString().isEmpty()){
                    userPass.setError("Required Field");
                    return;
                }

                if (userConfirmPass.getText().toString().isEmpty()){
                    userConfirmPass.setError("Required Field");
                    return;
                }

                if (!userPass.getText().toString().equals(userConfirmPass.getText().toString())){
                    userConfirmPass.setError("Password Do not Match");
                    return;
                }

                user.updatePassword(userPass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ResetPasswordActivity.this, "Password updated.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(ResetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}