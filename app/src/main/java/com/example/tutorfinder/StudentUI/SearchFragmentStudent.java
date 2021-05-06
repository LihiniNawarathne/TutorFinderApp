package com.example.tutorfinder.StudentUI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tutorfinder.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFragmentStudent extends Fragment {


    Button btnSearch,btnCLICk;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_student, container, false);


        btnSearch = view.findViewById(R.id.btnSearchSubject);
        btnCLICk = view.findViewById(R.id.btnGrabOffer);
        spinner =view.findViewById(R.id.SubjectSpinner);

        //set spinner
        List<String> list = new ArrayList<>();

        list.add("Mathematics");
        list.add("Commerce");
        list.add("Sinhala");
        list.add("Biology");

        ArrayAdapter<String> dataAdapter =new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(dataAdapter);


        //'Search' Button pressed
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get spinner value
                String subjectName  = spinner.getSelectedItem().toString();


                Intent intent = new Intent(getActivity(), searchClass.class);
                Bundle bundle = new Bundle();
                bundle.putString("subjectName",subjectName );
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        //'Click' Button pressed
        btnCLICk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //redirect to Courser Offer select page
                Intent intent = new Intent(getActivity(), CourseOffersStudent.class);
                startActivity(intent);

            }
        });


        return view;
    }


}