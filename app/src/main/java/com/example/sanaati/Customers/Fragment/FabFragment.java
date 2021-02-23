package com.example.sanaati.Customers.Fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.sanaati.Customers.Activity.MainActivity;
import com.example.sanaati.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class FabFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fab, container, false);

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        builder1.setView(inflater1.inflate(R.layout.help_dialog, null));
        final AlertDialog dialog1 = builder1.create();
        ((FrameLayout) dialog1.getWindow().getDecorView().findViewById(android.R.id.content)).setForeground(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
        lp1.copyFrom(dialog1.getWindow().getAttributes());
        lp1.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog1.show();
        dialog1.getWindow().setAttributes(lp1);
        final Button close=dialog1.findViewById(R.id.btn2);
        final CircleImageView im1=dialog1.findViewById(R.id.im);
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        return rootView;
    }
}
