package net.eazypg.tasksolution;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerHomePageActivity extends AppCompatActivity {

    EditText nameEditText, phoneEditText, roomEditText, rentEditText;
    Button addTenantButton, viewNotOnboardedTenantButton, viewOnboardedTenantButton;

    boolean error;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_page);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        roomEditText = findViewById(R.id.roomEditText);
        rentEditText = findViewById(R.id.rentEditText);

        addTenantButton = findViewById(R.id.addTenantButton);
        viewNotOnboardedTenantButton = findViewById(R.id.viewNotOnboardedTenantButton);
        viewOnboardedTenantButton = findViewById(R.id.viewOnboardedTenantButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        viewNotOnboardedTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ManagerHomePageActivity.this, NotOnBoardedActivity.class));

            }
        });

        viewOnboardedTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(ManagerHomePageActivity.this , OnBoardedActivity.class));


            }
        });

        addTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tenantName = nameEditText.getText().toString();
                String tenantPhone = phoneEditText.getText().toString();
                String tenantRoom = roomEditText.getText().toString();
                String rentAmount = rentEditText.getText().toString();

                error = false;

                if (tenantName.isEmpty()) {

                    error = true;
                    nameEditText.setError("Required");
                }

                if (tenantPhone.isEmpty()) {

                    error = true;
                    phoneEditText.setError("Required");
                }

                if (tenantRoom.isEmpty()) {

                    error = true;
                    roomEditText.setError("Required");
                }

                if (rentAmount.isEmpty()) {

                    error = true;
                    rentEditText.setError("Required");
                }

                if (!error) {


                    databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/NotOnboardedTenants");

                    TenantDetails tenantDetails = new TenantDetails(tenantName, tenantPhone, tenantRoom, rentAmount);
                    databaseReference.child(tenantPhone).setValue(tenantDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            nameEditText.setText("");
                            phoneEditText.setText("");
                            roomEditText.setText("");
                            rentEditText.setText("");
                            Toast.makeText(ManagerHomePageActivity.this, "Tenant Added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ManagerHomePageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });


    }
}
