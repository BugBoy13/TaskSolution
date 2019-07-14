package net.eazypg.tasksolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotOnBoardedActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<TenantDetails> tenantDetailsArrayList;

    RecyclerView tenantsRecyclerView;

    TenantNotOnBoardedDetailList tenantNotOnBoardedDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_on_boarded);

        tenantDetailsArrayList = new ArrayList<>();

        tenantsRecyclerView = findViewById(R.id.tenantsRecyclerView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/NotOnboardedTenants");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tenantDetailsArrayList.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                    tenantDetailsArrayList.add(tenantDetails);
                }

                tenantNotOnBoardedDetailList = new TenantNotOnBoardedDetailList(tenantDetailsArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(NotOnBoardedActivity.this);
                tenantsRecyclerView.setLayoutManager(layoutManager);
                tenantsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                tenantsRecyclerView.setAdapter(tenantNotOnBoardedDetailList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
