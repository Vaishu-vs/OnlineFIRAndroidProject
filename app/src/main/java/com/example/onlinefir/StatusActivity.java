package com.example.onlinefir;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StatusActivity extends Fragment implements View.OnClickListener{
    private Button buttonViewFir;
    private EditText edittextVictimId;
    private ProgressBar progressBar;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("COMPLAIN");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_status, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonViewFir = (Button) view.findViewById(R.id.buttonViewFir);
        edittextVictimId = (EditText) view.findViewById(R.id.edittextVictimId);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        buttonViewFir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                viewComplain();
                break;
        }
    }

    private void viewComplain() {
        progressBar.setVisibility(View.VISIBLE);
        String victimId = edittextVictimId.getText().toString();
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("Id", victimId);
        taskMap.put("UID", currentuser);
        // hide the progress bar
        progressBar.setVisibility(View.GONE);
        myRef.push().setValue(taskMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(getContext(), DisplayComplainActivity.class);
                startActivity(intent);
            }
        });
    }
}
