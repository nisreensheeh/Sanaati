package com.example.sanaati.Customers.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.sanaati.R;

public class MainFragment extends Fragment {
    EditText search_et;
    Button search_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home, container, false);

        search_et = rootView.findViewById(R.id.search_et);
        search_btn = rootView.findViewById(R.id.search_btn);

        return rootView;
    }
}
