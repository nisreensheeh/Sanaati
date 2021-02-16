package com.example.sanaati.Customers.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanaati.Customers.Class.EmpByJob;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Class.Users;

import java.util.ArrayList;

public class ServisesActivity extends AppCompatActivity {
    TextView data;
    ExpandableListView expandbleLis;
    ArrayList<Users> info;
    ArrayList<EmpByJob> empByJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servises);

        data = findViewById(R.id.data);
        expandbleLis = (ExpandableListView)findViewById(R.id.expandableListView);
        expandbleLis.setAdapter((BaseExpandableListAdapter)null);
        expandbleLis.setGroupIndicator(null);

        info = new ArrayList<>();
        empByJob = new ArrayList<>();

        //getEmployees

        if(empByJob.size()!=0)
            empByJob.clear();

        if(empByJob.isEmpty()){
            data.setVisibility(View.VISIBLE);
        }else {
            NewAdapter mNewAdapter = new NewAdapter();
            mNewAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            expandbleLis.setAdapter(mNewAdapter);
            expandbleLis.setGroupIndicator(null);
        }
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
            hirfa.setText("اسم الحرفة");

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