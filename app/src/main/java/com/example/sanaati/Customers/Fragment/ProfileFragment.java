package com.example.sanaati.Customers.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment{

//    ImageView star1, star2, star3, star4, star5;
    TextView  name_txt, email_txt,phone_txt, address_txt, job_txt;
    LinearLayout emailLin;
    EditText name_ed, email_ed, phone_ed, address_ed, job_ed;
    CircleImageView profImg, edit, save;
    Button request_btn;
    RatingBar rBar;
    RelativeLayout saveRel, editRel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_my_profile, container, false);

        name_ed = rootView.findViewById(R.id.name_ed);
        name_txt = rootView.findViewById(R.id.name_txt);
        job_ed = rootView.findViewById(R.id.job_ed);
        job_txt = rootView.findViewById(R.id.job_txt);
        address_txt = rootView.findViewById(R.id.address_txt);
        address_ed = rootView.findViewById(R.id.address_ed);
        email_txt = rootView.findViewById(R.id.email_txt);
        email_ed = rootView.findViewById(R.id.email_ed);
        phone_txt = rootView.findViewById(R.id.phone_txt);
        phone_ed = rootView.findViewById(R.id.phone_ed);
        profImg = rootView.findViewById(R.id.img);
        edit = rootView.findViewById(R.id.edit);
        save = rootView.findViewById(R.id.save);
        emailLin = rootView.findViewById(R.id.email_Lay);
        saveRel = rootView.findViewById(R.id.saveRel);
        editRel = rootView.findViewById(R.id.editRel);

        rBar = rootView.findViewById(R.id.ratingBar);
        rBar.setRating(Float.parseFloat(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("rate","")));


        name_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("name",""));
        job_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job",""));
        address_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("addressd",""));
        if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("email","").equals("")){
            emailLin.setVisibility(View.GONE);
        }else {
            emailLin.setVisibility(View.VISIBLE);
            email_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("email",""));

        }
        phone_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("phone",""));


        Uri Imagedata= Uri.parse(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image",""));
        Picasso.get().load(Imagedata).into(profImg);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_txt.setVisibility(View.GONE);
                name_ed.setVisibility(View.VISIBLE);
                name_ed.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("name",""));

                phone_txt.setVisibility(View.GONE);
                phone_ed.setVisibility(View.VISIBLE);
                phone_ed.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("phone",""));

                email_txt.setVisibility(View.GONE);
                email_ed.setVisibility(View.VISIBLE);
                email_ed.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("email",""));

                address_txt.setVisibility(View.GONE);
                address_ed.setVisibility(View.VISIBLE);
                address_ed.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("addressd",""));

                job_txt.setVisibility(View.GONE);
                job_ed.setVisibility(View.VISIBLE);
                job_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job",""));

                editRel.setVisibility(View.GONE);
                saveRel.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                user.put("name", name_ed.getText().toString());
                user.put("email", email_ed.getText().toString());
                user.put("addressd",address_ed.getText().toString() );
                user.put("phone", phone_ed.getText().toString());
                user.put("job",job_ed.getText().toString() );
//                user.put("password",password );
//                user.put("image",uri.toString());
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("Info",MODE_PRIVATE).edit();
                editor.putString("name", name_ed.getText().toString());
                editor.putString("email", email_ed.getText().toString());
                editor.putString("addressd",address_ed.getText().toString() );
                editor.putString("phone", phone_ed.getText().toString());
                editor.putString("job",job_ed.getText().toString() );
//                editor.putString("password",password );
//                editor.putString("image",uri.toString());
                editor.apply();

                name_txt.setVisibility(View.VISIBLE);
                name_ed.setVisibility(View.GONE);
                name_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("name",""));

                phone_txt.setVisibility(View.VISIBLE);
                phone_ed.setVisibility(View.GONE);
                phone_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("phone",""));

                email_txt.setVisibility(View.VISIBLE);
                email_ed.setVisibility(View.GONE);
                email_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("email",""));

                address_txt.setVisibility(View.VISIBLE);
                address_ed.setVisibility(View.GONE);
                address_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("addressd",""));

                job_txt.setVisibility(View.VISIBLE);
                job_ed.setVisibility(View.GONE);
                job_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job",""));

                editRel.setVisibility(View.VISIBLE);
                saveRel.setVisibility(View.GONE);

                //fire
            }
        });
        return rootView;
    }

}
