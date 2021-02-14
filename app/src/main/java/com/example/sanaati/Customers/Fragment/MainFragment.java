package com.example.sanaati.Customers.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sanaati.R;

public class MainFragment extends Fragment {
    EditText search_et;
    Button search_btn;
    CardView serviceCard, LocationsCard, topRatedLay, secondCard;
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

        serviceCard = rootView.findViewById(R.id.serviceCard);
        LocationsCard = rootView.findViewById(R.id.LocationsCard);
        topRatedLay = rootView.findViewById(R.id.topRatedLay);
        secondCard = rootView.findViewById(R.id.secondCard);

        LocationsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
        serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return rootView;
    }
}
