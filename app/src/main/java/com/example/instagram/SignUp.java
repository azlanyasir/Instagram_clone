package com.example.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    TextView tvLogin;
    Button btnSignUp;
    TextInputEditText etName, etPassword, etCPassword;

    FirebaseAuth auth;
    FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        tvLogin.setOnClickListener(v->{
            startActivity(new Intent(SignUp.this, MainActivity.class));
            finish();
        });

        btnSignUp.setOnClickListener(v->{
            String name = etName.getText().toString().trim();
            String password = etPassword.getText().toString();
            String cpassword = etCPassword.getText().toString();

            if(TextUtils.isEmpty(name))
            {
                etName.setError("Enter the name");
                return;
            }
            if(TextUtils.isEmpty(password))
            {
                etPassword.setError("Enter the pasword");
                return;
            }
            if(TextUtils.isEmpty(cpassword))
            {
                etCPassword.setError("Enter the pasword again");
                return;
            }
            if(!password.equals(cpassword))
            {
                etPassword.setError("Password mis-match");
                etCPassword.setError("Password mis-match");
                return;
            }

            auth.createUserWithEmailAndPassword(name,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(SignUp.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this,Profile.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
    private void init()
    {
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etCPassword = findViewById(R.id.etCPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);
        auth = FirebaseAuth.getInstance();

    }
}