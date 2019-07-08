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

import java.util.Calendar;

public class ManagerFillDetailsActivity extends AppCompatActivity {

    EditText nameEditText, phoneEditText, pgNameEditText, pincodeEditText;
    Button saveButton;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_fill_details);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        pgNameEditText = findViewById(R.id.pgNameEditText);
        pincodeEditText = findViewById(R.id.pincodeEditText);

        saveButton = findViewById(R.id.saveButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String pgName = pgNameEditText.getText().toString();
                String pincode = pincodeEditText.getText().toString();


                databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/PGDetails");

                PGDetails pgDetails = new PGDetails(name, phone, pgName, pincode, Calendar.getInstance().getTime());

                databaseReference.setValue(pgDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(ManagerFillDetailsActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ManagerFillDetailsActivity.this, ManagerHomePageActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ManagerFillDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
}
