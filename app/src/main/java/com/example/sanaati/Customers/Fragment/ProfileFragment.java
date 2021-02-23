package com.example.sanaati.Customers.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.sanaati.Customers.Activity.MainActivity;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment{

    ImageView star1, star2, star3, star4, star5;
    TextView name, email, address, phone, job;
    CircleImageView profImg;
    Button request_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_profile, container, false);


        name = rootView.findViewById(R.id.name);
        job = rootView.findViewById(R.id.job);
        address = rootView.findViewById(R.id.address);
        email = rootView.findViewById(R.id.email);
        phone = rootView.findViewById(R.id.phone);
        profImg = rootView.findViewById(R.id.img);
        star1 = rootView.findViewById(R.id.star1);
        star2 = rootView.findViewById(R.id.star2);
        star3 = rootView.findViewById(R.id.star3);
        star4 = rootView.findViewById(R.id.star4);
        star5 = rootView.findViewById(R.id.star5);

        name.setText(getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE).getString("name",""));
        job.setText(getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE).getString("job",""));
        address.setText(getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE).getString("addressd",""));
        if(getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE).getString("email","").equals("")){
            email.setVisibility(View.GONE);
        }else {
            email.setVisibility(View.VISIBLE);
            email.setText(getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE).getString("email",""));

        }
        phone.setText(getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE).getString("phone",""));


        Uri Imagedata= Uri.parse(getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE).getString("image",""));
        Picasso.get().load(Imagedata).into(profImg);

        return rootView;
    }

}
