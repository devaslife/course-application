package com.example.crudoperationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText editUserName, editPassword, editCnfPassword;
    private Button btnRegister;
    private ProgressBar progLogin;
    private TextView txtLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        editUserName = findViewById(R.id.idEdtUserName);
        editPassword = findViewById(R.id.idEdtPassword);
        editCnfPassword = findViewById(R.id.idEdtCnfPwd);
        btnRegister = findViewById(R.id.idBtnRegister);
        progLogin = findViewById(R.id.idPbLogin);
        txtLogin = findViewById(R.id.idTvLogin);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progLogin.setVisibility(View.VISIBLE);
                String userName = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                String cnfPassword = editCnfPassword.getText().toString();

                // i need to test if the password not equal config password or any edit is empty
                if (!password.equals(cnfPassword)) {
                    Toast.makeText(RegisterActivity.this, "please write again password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password) && TextUtils.isEmpty(cnfPassword)) {
                    Toast.makeText(RegisterActivity.this, "Please complete all values ", Toast.LENGTH_SHORT).show();
                } else {
                    // create new email and password
                    firebaseAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progLogin.setVisibility(View.GONE);
                                Toast.makeText(RegisterActivity.this, "successful create new email...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progLogin.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "you have problem check the internet...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
}