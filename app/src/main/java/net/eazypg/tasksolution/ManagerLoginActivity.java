package net.eazypg.tasksolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManagerLoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button signUpButton, loginButton;

    boolean error;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        signUpButton = findViewById(R.id.signUpButton);
        loginButton = findViewById(R.id.loginButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {

            startActivity(new Intent(ManagerLoginActivity.this, ManagerHomePageActivity.class));
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                error = false;

                if (email.isEmpty()) {

                    error = true;
                    emailEditText.setError("Required");
                }

                if (password.isEmpty()) {

                    error = true;
                    passwordEditText.setError("Required");
                }


                if (!error) {


                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        ProgressDialog progressDialog = ProgressDialog.show(ManagerLoginActivity.this, "","Signing in..", true);

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();
                                firebaseUser = firebaseAuth.getCurrentUser();

                                // ToDo: Go to Home


                            }
                            else {

                                Toast.makeText(ManagerLoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }


            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                error = false;

                if (email.isEmpty()) {

                    error = true;
                    emailEditText.setError("Required");
                }

                if (password.isEmpty()) {

                    error = true;
                    passwordEditText.setError("Required");
                }

                if (!error) {

                    final ProgressDialog progressDialog = ProgressDialog.show(ManagerLoginActivity.this, "","Signing up..", true);

                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            // ToDo: Go to next screen to fill details

                            startActivity(new Intent(ManagerLoginActivity.this, ManagerFillDetailsActivity.class));

                            progressDialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ManagerLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
                }




            }
        });
    }
}
