package net.eazypg.tasksolution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OnBoardedActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView tenantOnBoardedRecycleView;

    ArrayList<TenantDetails> tenantOnBoardedDetailsArrayList;

    TenantOnBoardedDetailList tenantOnBoardedDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarded);

        tenantOnBoardedDetailsArrayList = new ArrayList<>();

        tenantOnBoardedRecycleView = findViewById(R.id.tenantOnBoardedRecycleView);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/OnBoardedTenants");


        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tenantOnBoardedDetailsArrayList.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {

                    TenantDetails tenantDetails = snapshot.getValue(TenantDetails.class);
                    tenantOnBoardedDetailsArrayList.add(tenantDetails);
                    Log.e("OnBoarded:" , tenantDetails.name);

                }

                tenantOnBoardedDetailList = new TenantOnBoardedDetailList(tenantOnBoardedDetailsArrayList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OnBoardedActivity.this);
                tenantOnBoardedRecycleView.setLayoutManager(layoutManager);
                tenantOnBoardedRecycleView.setItemAnimator(new DefaultItemAnimator());
                tenantOnBoardedRecycleView.setAdapter(tenantOnBoardedDetailList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
