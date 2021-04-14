package com.example.sanaati.Employees.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaati.Customers.Activity.EmpProfileActivity;
import com.example.sanaati.Customers.Activity.MainActivity;
import com.example.sanaati.Customers.Activity.ServisesActivity;
import com.example.sanaati.Employees.Class.Talabat;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Activity.LoginActivity;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.iigo.library.PeasLoadingView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TalabatActivity extends AppCompatActivity {
    ArrayList<Talabat> talabat;
    ExpandableListAdapter expandableListAdapter;
    TextView data;
    ExpandableListView expandbleLis;
    CardView lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talabat);

        PeasLoadingView peasLoadingView = findViewById(R.id.plv_loading1);
        peasLoadingView.setPeasCount(20);//set the peas count
        peasLoadingView.setInterpolator(new LinearInterpolator()); //set the animation interpolator
        peasLoadingView.setPeasColors(new int[]{Color.RED, Color.WHITE, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.GRAY}); //set the color array
        peasLoadingView.setVisibility(View.INVISIBLE);
        data = findViewById(R.id.data);
        lay = findViewById(R.id.lay);
        expandbleLis =findViewById(R.id.expandableListView);

        expandbleLis.setGroupIndicator(null);
        talabat = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Talabat").whereEqualTo("empId", getSharedPreferences("Info",Context.MODE_PRIVATE).getString("userid","")).whereEqualTo("status","قيد الطلب")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {


            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Toast.makeText(TalabatActivity.this, "h", Toast.LENGTH_SHORT).show();
                List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();
                if(gr==null){
                    peasLoadingView.stop(); //stop animation
                    peasLoadingView.setVisibility(View.GONE);
                }else{
                    if (gr.size() != 0) {
                        for (int i = 0; i < gr.size(); i++) {
                            talabat.add(new Talabat(gr.get(i).getString("talabId"),gr.get(i).getString("clientId"), gr.get(i).getString("clientName"),gr.get(i).getString("clientaddress"),
                                    gr.get(i).getString("clientlocation"), gr.get(i).getString("empId"),
                                    gr.get(i).getString("empName"),gr.get(i).getString("empService"), gr.get(i).getString("requestDate"), gr.get(i).getString("requestTime"),
                                    gr.get(i).getString("empArrivedDateTime"), gr.get(i).getString("empLeavedDateTime"), gr.get(i).getString("clientLocation")
                                    , gr.get(i).getString("empLocation"), gr.get(i).getString("totalAmount"), gr.get(i).getString("companyComission")
                                    , gr.get(i).getString("customerEmpRate"), gr.get(i).getString("status")));
                        }
                    }
                }
                if(talabat.size()>0){
                    peasLoadingView.stop();
                    peasLoadingView.setVisibility(View.GONE);
                    expandbleLis.setAdapter((BaseExpandableListAdapter)null);
                    lay.setVisibility(View.VISIBLE);
                    expandableListAdapter =  new NewAdapter(TalabatActivity.this);
                    expandbleLis.setAdapter(expandableListAdapter);
                    expandbleLis.setGroupIndicator(null);
                }else{
                    lay.setVisibility(View.INVISIBLE);
                    expandbleLis.setAdapter((BaseExpandableListAdapter)null);
                    data.setVisibility(View.VISIBLE);
                    peasLoadingView.stop(); //stop animation
                    peasLoadingView.setVisibility(View.GONE);
                }
            }});
    }

    class NewAdapter extends BaseExpandableListAdapter {

        public Activity activity;

        public NewAdapter(Activity context) {
            this.activity = context;
        }


        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return talabat.get(groupPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) TalabatActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.talabat_child_list, null);

            Button accept = convertView.findViewById(R.id.btn1);
            Button reject = convertView.findViewById(R.id.btn2);


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
            return talabat.size();
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

            LayoutInflater layoutInflater = (LayoutInflater) TalabatActivity.this.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//            if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.talabat_list_group, null);
//            }
            TextView talab_id_v = (TextView) convertView
                    .findViewById(R.id.talab_id_v);
            TextView talab_status_v = (TextView) convertView
                    .findViewById(R.id.talab_status_v);
            TextView talab_location_v = (TextView) convertView
                    .findViewById(R.id.talab_location_v);
            TextView talab_address_v = (TextView) convertView
                    .findViewById(R.id.talab_address_v);

            talab_id_v.setText(talabat.get(groupPosition).talabId);
            talab_status_v.setText(talabat.get(groupPosition).status);
            talab_location_v.setText("المسافة بين الموظف والزبون");
            talab_address_v.setText(talabat.get(groupPosition).clientaddress);

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