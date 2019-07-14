package net.eazypg.tasksolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class TenantLoginActivity extends AppCompatActivity {


    EditText phoneEditText;
    Button verifyButton;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    String phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_login);

        phoneEditText = findViewById(R.id.phoneEditText);

        verifyButton = findViewById(R.id.verifyButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phone = phoneEditText.getText().toString();

                databaseReference = firebaseDatabase.getReference("PG/ZHIgIjmlUSS9axL39rb3aKnI19B2/NotOnboardedTenants/" + phone);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                        if (!dataSnapshot.exists()) {

                            Toast.makeText(TenantLoginActivity.this, "Phone not added", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {



                                    String code = phoneAuthCredential.getSmsCode();

                                    Toast.makeText(TenantLoginActivity.this, "OTP Retrieved " + code, Toast.LENGTH_SHORT).show();

                                    firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(TenantLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {

                                                TenantDetails tenantDetails = dataSnapshot.getValue(TenantDetails.class);

                                                databaseReference1 = firebaseDatabase.getReference("Tenants/" + firebaseUser.getUid() + "/Details");
                                                databaseReference1.setValue(tenantDetails);

                                                databaseReference1 = firebaseDatabase.getReference("PG/ZHIgIjmlUSS9axL39rb3aKnI19B2/OnBoardedTenants/" + firebaseUser.getUid());
                                                databaseReference1.setValue(tenantDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        startActivity(new Intent(TenantLoginActivity.this, TenantHomePageActivity.class));

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Toast.makeText(TenantLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                                /*databaseReference1.setValue(dataSnapshot.getValue());*/

                                            } else {

                                                Toast.makeText(TenantLoginActivity.this, "Verification failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {

                                    Toast.makeText(TenantLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            };

                            PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone, 60, TimeUnit.SECONDS, TenantLoginActivity.this, mCallBacks);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



    }
}
