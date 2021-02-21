package com.example.sanaati.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaati.Customers.Class.EmpByJob;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Activity.SignupActivity;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ServisesActivity extends AppCompatActivity {
    TextView data;
    ExpandableListView expandbleLis;
    ArrayList<Users> info;
    ArrayList<String> jobs;
    ArrayList<EmpByJob> empByJob;
    EmpByJob ej = null;
    Users ui = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servises);

        data = findViewById(R.id.data);
        expandbleLis = (ExpandableListView)findViewById(R.id.expandableListView);
        expandbleLis.setAdapter((BaseExpandableListAdapter)null);
        expandbleLis.setGroupIndicator(null);

        info = new ArrayList<>();


        jobs = new ArrayList<>();
        empByJob = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Jobs")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(int i=0; i<list.size(); i++) {
                    jobs.add(list.get(i).getString("name"));
                }
                for(int i=1; i<jobs.size(); i++){
//                    ej=new EmpByJob(jobs.get(i)+"");
//                    ui= new Users("", "", "", "", "", "", "", "", "", "", "", "", "", "");
                    empByJob.add(new EmpByJob(jobs.get(i)+""));
                }
            }
        });

        //getEmployees
        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        db1.collection("Users").document("type").collection("Employees")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        info.add(new Users(document.getString("userid"),document.getString("name"),document.getString("email"),
//                                document.getString("addressd"),document.getString("phone"),document.getString("job"),
//                                document.getString("password"),document.getString("location"),document.getString("type")
//                                , document.getString("rate"), document.getString("token"), document.getString("image")
//                                , document.getString("aid"), document.getString("comission")));
                        ej = new EmpByJob(document.getString("job"));
                        ui = new Users(document.getString("userid"),document.getString("name"),document.getString("email"),
                                document.getString("addressd"),document.getString("phone"),document.getString("job"),
                                document.getString("password"),document.getString("location"),document.getString("type")
                                , document.getString("rate"), document.getString("token"), document.getString("image")
                                , document.getString("aid"), document.getString("comission"));
                        sort(ej, ui);
                    }

                }
            }
        });

        if(empByJob.isEmpty()){
            data.setVisibility(View.VISIBLE);
        }else {
            NewAdapter mNewAdapter = new NewAdapter();
            mNewAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            expandbleLis.setAdapter(mNewAdapter);
            expandbleLis.setGroupIndicator(null);
        }
    }

    private void sort(EmpByJob ej, Users ui) {
        int pos = -1;
        for(int i=0; i<empByJob.size();i++)
            if(empByJob.get(i).job.equals(ui.job))
                pos=i;

        if(pos!=-1){
            empByJob.get(pos).job = ej.job;
            empByJob.get(pos).Add(ui);}
    }

    class NewAdapter extends BaseExpandableListAdapter {

        public LayoutInflater minflater;
        public Activity activity;

        public NewAdapter() {

        }

        public void setInflater(LayoutInflater mInflater, Activity act) {
            this.minflater = mInflater;
            activity = act;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) ServisesActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.emp_list, null);
            }

            ImageView mImage1,mImage2,mImage3,mImage4,mImage5;
            TextView mName1,mName2,mName3,mName4,mName5, mJOB1,mJOB2,mJOB3,mJOB4,mJOB5, mDistanse1, mDistanse2, mDistanse3, mDistanse4, mDistanse5;
            CardView mCardView1,mCardView2,mCardView3,mCardView4,mCardView5;

            mImage1 = convertView.findViewById(R.id.postImage1);
            mName1 = convertView.findViewById(R.id.tvEmpName1);
            mJOB1 = convertView.findViewById(R.id.tvCraft1);
            mDistanse1 = convertView.findViewById(R.id.distance1);
            mCardView1 = convertView.findViewById(R.id.cardview1);


            mImage2 = convertView.findViewById(R.id.postImage2);
            mName2 = convertView.findViewById(R.id.tvEmpName2);
            mJOB2 = convertView.findViewById(R.id.tvCraft2);
            mDistanse2 = convertView.findViewById(R.id.distance2);
            mCardView2 = convertView.findViewById(R.id.cardview2);

            mImage3 = convertView.findViewById(R.id.postImage3);
            mName3 = convertView.findViewById(R.id.tvEmpName3);
            mJOB3 = convertView.findViewById(R.id.tvCraft3);
            mDistanse3 = convertView.findViewById(R.id.distance3);
            mCardView3 = convertView.findViewById(R.id.cardview3);

            mImage4 = convertView.findViewById(R.id.postImage4);
            mName4 = convertView.findViewById(R.id.tvEmpName4);
            mJOB4 = convertView.findViewById(R.id.tvCraft4);
            mDistanse4 = convertView.findViewById(R.id.distance4);
            mCardView4 = convertView.findViewById(R.id.cardview4);

            mImage5 = convertView.findViewById(R.id.postImage5);
            mName5 = convertView.findViewById(R.id.tvEmpName5);
            mJOB5 = convertView.findViewById(R.id.tvCraft5);
            mDistanse5 = convertView.findViewById(R.id.distance5);
            mCardView5 = convertView.findViewById(R.id.cardview5);
            try {
                mName1.setText(empByJob.get(childPosition).info.get(0).name);
                mJOB1.setText(empByJob.get(childPosition).info.get(0).job);
                mDistanse1.setText(empByJob.get(childPosition).info.get(0).location);
                mName2.setText(empByJob.get(childPosition).info.get(1).name);
                mJOB2.setText(empByJob.get(childPosition).info.get(1).job);
                mDistanse2.setText(empByJob.get(childPosition).info.get(1).location);
                mName3.setText(empByJob.get(childPosition).info.get(2).name);
                mJOB3.setText(empByJob.get(childPosition).info.get(2).job);
                mDistanse3.setText(empByJob.get(childPosition).info.get(2).location);
                mName4.setText(empByJob.get(childPosition).info.get(3).name);
                mJOB4.setText(empByJob.get(childPosition).info.get(3).job);
                mDistanse4.setText(empByJob.get(childPosition).info.get(3).location);
                mName5.setText(empByJob.get(childPosition).info.get(4).name);
                mJOB5.setText(empByJob.get(childPosition).info.get(4).job);
                mDistanse5.setText(empByJob.get(childPosition).info.get(4).location);
            }catch (Exception e){}

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public int getGroupCount() {
            return info.size();
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
            super.onGroupCollapsed(groupPosition);
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) ServisesActivity.this.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.emp_list, null);
            }
            TextView hirfa = (TextView) convertView
                    .findViewById(R.id.hirfa_v);
            hirfa.setText(empByJob.get(groupPosition).job);

            ImageView img=convertView.findViewById(R.id.im);
            if(isExpanded){
                img.setImageResource(R.drawable.ic_lessw);
            }else{
                img.setImageResource(R.drawable.ic_addw);
            }

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }
}