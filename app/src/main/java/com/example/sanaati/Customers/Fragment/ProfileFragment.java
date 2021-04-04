package com.example.sanaati.Customers.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.sanaati.Customers.Activity.MainActivity;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Activity.SignupActivity;
import com.example.sanaati.UsersAuth.Class.MultiSelectionSpinner;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment  implements MultiSelectionSpinner.OnMultipleItemsSelectedListener{

//    ImageView star1, star2, star3, star4, star5;
    TextView  name_txt, email_txt,phone_txt, address_txt;
    LinearLayout emailLin;
    EditText name_ed, email_ed, phone_ed, address_ed;
    CircleImageView profImg, edit, save;
    Button request_btn;
    RatingBar rBar;
    RelativeLayout saveRel, editRel;
    private static final int ImageBack = 1;
    String ImageUri="";
    Uri ImageData;
    GridView gridView;
    ArrayList<String> info;
    MultiSelectionSpinner multiSelectionListSpinner;
    ArrayList<String> jobs;
    String jobListString;
    RelativeLayout job_spinner_lay2;
    TextView jobTxt;
    String temp;
    Uri imgData;
    int jobNum;

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
        gridView = rootView.findViewById(R.id.gridViewCustom);
        job_spinner_lay2 = rootView.findViewById(R.id.job_spinner_lay2);
        jobTxt = rootView.findViewById(R.id.jobTxt);


        jobs = new ArrayList<>();
        jobs.add("--اختر--");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Jobs")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(int i=0; i<list.size(); i++) {
                    jobs.add(list.get(i).getString("name"));
                }
                multiSelectionListSpinner = (MultiSelectionSpinner) rootView.findViewById(R.id.spinner_string_list);
                multiSelectionListSpinner.setItems(jobs);
//                multiSelectionListSpinner.setSelection(new int[]{0});
                multiSelectionListSpinner.setListener(ProfileFragment.this);
            }
        });



        rBar = rootView.findViewById(R.id.ratingBar);
        rBar.setRating(Float.parseFloat(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("rate","")));


        name_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("name",""));

        if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("type","").toString().equals("موظف")){

             temp = getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job","");

            String [] arr = temp.split(",");
            info = new ArrayList<>();

            for(int i=0; i<arr.length; i++)
                info.add(arr[i]);

            if(info.isEmpty()){
//                lin.setVisibility(LinearLayout.INVISIBLE);
//                data.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.INVISIBLE);
            }else{
                gridView.setVisibility(View.VISIBLE);
                jobTxt.setVisibility(View.VISIBLE);
                gridView.setAdapter(new CustomGridViewAdapter());
            }

        }else{
            job_spinner_lay2.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            jobTxt.setVisibility(View.GONE);
        }

        address_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("addressd",""));
        if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("email","").equals("")){
            emailLin.setVisibility(View.GONE);
        }else {
            emailLin.setVisibility(View.VISIBLE);
            email_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("email",""));

        }
        phone_txt.setText(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("phone",""));

        if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image","").equals("")){
            Uri myUri = Uri.parse("member.pic");
            Picasso.get().load(myUri).placeholder(R.drawable.initimage).into(profImg);
        }else{
             imgData= Uri.parse(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image",""));
            Picasso.get().load(imgData).into(profImg);
        }

        profImg.setEnabled(false);
        profImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,ImageBack);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    profImg.setEnabled(true);

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
                    ArrayList<String> indexs;
                    indexs = new ArrayList<>();
                    if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("type","").toString().equals("موظف")){
                        job_spinner_lay2.setVisibility(View.VISIBLE);

                        for (int i = 0; i< jobs.size() ; i++){
                            if(temp.contains(jobs.get(i))){
                                indexs.add(jobs.get(i));
                            }
                        }
                        multiSelectionListSpinner.setSelection(indexs);
                        gridView.setVisibility(View.GONE);
                        jobTxt.setVisibility(View.VISIBLE);
                    }else{
                        job_spinner_lay2.setVisibility(View.GONE);
                        gridView.setVisibility(View.GONE);
                        jobTxt.setVisibility(View.GONE);
                    }

                    editRel.setVisibility(View.GONE);
                    saveRel.setVisibility(View.VISIBLE);

                }catch (Exception e){
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jobNum>4){
                    Toast.makeText(getActivity(), "يمكنك اختيار 4 وظائف كحد اعلى", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("type","").toString().equals("موظف")){

                    if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image","").equals("") || getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image","").toString().equals(imgData.toString())){
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name_ed.getText().toString());
                        user.put("email", email_ed.getText().toString());
                        user.put("addressd",address_ed.getText().toString() );
                        user.put("phone", phone_ed.getText().toString());
                        if(jobListString==null){
                            user.put("job",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job",""));
                        }else{
                            user.put("job",jobListString);
                        }
                        user.put("image",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image",""));
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Users").document("type").collection("Employees").document(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("userid",""))
                                .update(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
//                                    peasLoadingView.stop(); //stop animation
                                            Toast.makeText(getActivity(), "تم تعديل بياناتك بنجاح", Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("Info",MODE_PRIVATE).edit();
                                            editor.putString("name", name_ed.getText().toString());
                                            editor.putString("email", email_ed.getText().toString());
                                            editor.putString("addressd",address_ed.getText().toString() );
                                            editor.putString("phone", phone_ed.getText().toString());
                                            if(jobListString==null){
                                                editor.putString("job",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job",""));
                                            }else{
                                                editor.putString("job",jobListString);
                                            }
                                            editor.putString("image",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image",""));
                                            editor.apply();
                                            profImg.setEnabled(false);
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

                                            job_spinner_lay2.setVisibility(View.GONE);

                                            temp = getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job","");

                                            String [] arr = temp.split(",");
                                            info.clear();

                                            for(int i=0; i<arr.length; i++)
                                                info.add(arr[i]);

                                            if(info.isEmpty()){
                                                gridView.setVisibility(View.INVISIBLE);
                                            }else{
                                                gridView.setVisibility(View.VISIBLE);
                                                jobTxt.setVisibility(View.VISIBLE);
                                                gridView.setAdapter(new CustomGridViewAdapter());
                                            }

                                            editRel.setVisibility(View.VISIBLE);
                                            saveRel.setVisibility(View.GONE);
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();
//                                peasLoadingView.stop(); //stop animation

                                    }
                                });

                    }else{

                        FirebaseStorage storage =  FirebaseStorage.getInstance();
                        final StorageReference ImageName =  storage.getReference().child("image"+ImageData.getLastPathSegment());
                        Toast.makeText(getActivity(), ImageName.toString(), Toast.LENGTH_SHORT).show();
                        ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

//                            Toast.makeText(getActivity(), "تم تحميل الصوة", Toast.LENGTH_SHORT).show();
                                ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        Map<String, Object> user = new HashMap<>();
                                        user.put("name", name_ed.getText().toString());
                                        user.put("email", email_ed.getText().toString());
                                        user.put("addressd",address_ed.getText().toString() );
                                        user.put("phone", phone_ed.getText().toString());
                                        if(jobListString==null){
                                            user.put("job",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job",""));
                                        }else{
                                            user.put("job",jobListString);
                                        }
                                        user.put("image",uri.toString());
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        db.collection("Users").document("type").collection("Employees").document(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("userid",""))
                                                .update(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
//                                    peasLoadingView.stop(); //stop animation
                                                            Toast.makeText(getActivity(), "تم تعديل بياناتك بنجاح", Toast.LENGTH_SHORT).show();
                                                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("Info",MODE_PRIVATE).edit();
                                                            editor.putString("name", name_ed.getText().toString());
                                                            editor.putString("email", email_ed.getText().toString());
                                                            editor.putString("addressd",address_ed.getText().toString() );
                                                            editor.putString("phone", phone_ed.getText().toString());
                                                            if(jobListString==null){
                                                                editor.putString("job",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job",""));
                                                            }else{
                                                                editor.putString("job",jobListString);
                                                            }

                                                            editor.putString("image",uri.toString());
                                                            editor.apply();
                                                            profImg.setEnabled(false);
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

                                                            job_spinner_lay2.setVisibility(View.GONE);

                                                            temp = getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("job","");

                                                            String [] arr = temp.split(",");
                                                            info.clear();

                                                            for(int i=0; i<arr.length; i++)
                                                                info.add(arr[i]);

                                                            if(info.isEmpty()){
                                                                gridView.setVisibility(View.INVISIBLE);
                                                            }else{
                                                                gridView.setVisibility(View.VISIBLE);
                                                                jobTxt.setVisibility(View.VISIBLE);
                                                                gridView.setAdapter(new CustomGridViewAdapter());
                                                            }

                                                            editRel.setVisibility(View.VISIBLE);
                                                            saveRel.setVisibility(View.GONE);
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getActivity(), "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                    }
                                });
                            }
                        });

                    }

                }else {
                    if(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image","").equals("") || getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image","").toString().equals(imgData.toString())) {
                        Map<String, Object> user = new HashMap<>();
                        user.put("name", name_ed.getText().toString());
                        user.put("email", email_ed.getText().toString());
                        user.put("addressd",address_ed.getText().toString() );
                        user.put("phone", phone_ed.getText().toString());
                        user.put("image",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image",""));

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("Users").document("type").collection("Customers").document(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("userid",""))
                                .update(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getActivity(), "تم تعديل بياناتك بنجاح", Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("Info",MODE_PRIVATE).edit();
                                            editor.putString("name", name_ed.getText().toString());
                                            editor.putString("email", email_ed.getText().toString());
                                            editor.putString("addressd",address_ed.getText().toString() );
                                            editor.putString("phone", phone_ed.getText().toString());
                                            editor.putString("image",getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("image",""));

                                            editor.apply();

                                            profImg.setEnabled(false);
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

                                            job_spinner_lay2.setVisibility(View.GONE);
                                            gridView.setVisibility(View.GONE);
                                            jobTxt.setVisibility(View.GONE);

                                            editRel.setVisibility(View.VISIBLE);
                                            saveRel.setVisibility(View.GONE);

                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }else{
                        FirebaseStorage storage =  FirebaseStorage.getInstance();;
                        final StorageReference ImageName =  storage.getReference().child("image"+ImageData.getLastPathSegment());
                        ImageName.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("name", name_ed.getText().toString());
                                        user.put("email", email_ed.getText().toString());
                                        user.put("addressd",address_ed.getText().toString() );
                                        user.put("phone", phone_ed.getText().toString());
                                        user.put("image",uri.toString());

                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        db.collection("Users").document("type").collection("Customers").document(getActivity().getSharedPreferences("Info", MODE_PRIVATE).getString("userid",""))
                                                .update(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(getActivity(), "تم تعديل بياناتك بنجاح", Toast.LENGTH_SHORT).show();
                                                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("Info",MODE_PRIVATE).edit();
                                                            editor.putString("name", name_ed.getText().toString());
                                                            editor.putString("email", email_ed.getText().toString());
                                                            editor.putString("addressd",address_ed.getText().toString() );
                                                            editor.putString("phone", phone_ed.getText().toString());
                                                            editor.putString("image",uri.toString());

                                                            editor.apply();

                                                            profImg.setEnabled(false);
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

                                                            job_spinner_lay2.setVisibility(View.GONE);
                                                            gridView.setVisibility(View.GONE);
                                                            jobTxt.setVisibility(View.GONE);

                                                            editRel.setVisibility(View.VISIBLE);
                                                            saveRel.setVisibility(View.GONE);

                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getActivity(), "لقد حدث خطأ...حاول مرة اخرى", Toast.LENGTH_SHORT).show();

                                                    }
                                                });
                                    }
                                });
                            }
                        });
                    }

                }

            }
        });
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ImageBack){
            if(resultCode == RESULT_OK){
                ImageData = data.getData();
                ImageUri = String.valueOf(ImageData);
                Picasso.get().load(ImageData).into(profImg);
            }
        }
    }

    @Override
    public void selectedIndices(List<Integer> indices, MultiSelectionSpinner spinner) {

    }

    @Override
    public void selectedStrings(List<String> strings, MultiSelectionSpinner spinner) {
        switch (spinner.getId()) {
            case R.id.spinner_string_list:
                for(int i = 0; i<strings.size() ; i++){
                    if(strings.get(i).equals("--اختر--")){
                        strings.remove(strings.get(i));
                    }
                }
                 jobNum = strings.size();
                jobListString = strings.toString().substring(strings.toString().indexOf("[")+1, strings.toString().length()-1);
                break;
        }
    }


    public class CustomGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return info.size();
        }

        @Override
        public Object getItem(int position) {
            return info.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = (getActivity()).getLayoutInflater();
                convertView = inflater.inflate(R.layout.table_lay, parent, false);
            }

            TextView t2;
            t2 = convertView.findViewById(R.id.txt2);

            t2.setText(info.get(position));
            LinearLayout lin = convertView.findViewById(R.id.lin);



            return convertView; }
    }

}
