package net.eazypg.tasksolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TenantHomePageActivity extends AppCompatActivity {

    EditText nameEditText, phoneEditText, roomEditText, rentEditText;
    Button saveButton;

    boolean error;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_home_page);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        roomEditText = findViewById(R.id.roomEditText);
        rentEditText = findViewById(R.id.rentEditText);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        saveButton = findViewById(R.id.saveButton);


        databaseReference1 = firebaseDatabase.getReference("Tenants/" + firebaseUser.getUid() + "/Details");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TenantDetails tenantDetails = dataSnapshot.getValue(TenantDetails.class);

                nameEditText.setText(tenantDetails.name);
                phoneEditText.setText(tenantDetails.phone);
                roomEditText.setText(tenantDetails.room);
                rentEditText.setText(tenantDetails.rent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
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


                    databaseReference = firebaseDatabase.getReference("PG/ZHIgIjmlUSS9axL39rb3aKnI19B2/OnBoardedTenants");

                    TenantDetails tenantDetails = new TenantDetails(tenantName, tenantPhone, tenantRoom, rentAmount);
                    databaseReference.child(firebaseUser.getUid()).setValue(tenantDetails);

                    databaseReference = firebaseDatabase.getReference("Tenants/" + firebaseUser.getUid() + "/Details");
                    databaseReference.setValue(tenantDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(TenantHomePageActivity.this, "Details saved", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(TenantHomePageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });
    }
}
