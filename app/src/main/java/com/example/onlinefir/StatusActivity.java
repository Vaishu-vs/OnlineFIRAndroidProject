package com.example.onlinefir;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class StatusActivity extends Fragment {
    Button viewbtn;
    EditText victimid;
    TextView tview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_status, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewbtn = (Button) view.findViewById(R.id.viewfir);
        victimid = (EditText) view.findViewById(R.id.edittext_victimid);
        tview = (TextView) view.findViewById(R.id.textfir);
        Intent i = getActivity().getIntent();

        int id = i.getIntExtra("id", 0);
        victimid.setText(String.valueOf(id));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ShowStatusActivity.class);
                i.putExtra("victimid", victimid.getText().toString());
                startActivity(i);
            }
        });
    }
}
