package com.student_act.bookreserver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreserver.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Meeting extends Fragment {

    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    public static TextView textView;
    private RecyclerView student_list_recycler;
    private Button mMakeEntryBtn;
    private FloatingActionButton mREST;
    private View view;
    private StudentAdapter studentAdapter;
    private List<StudentMeetingDataModel> studentList;

    public Meeting() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_meeting, container, false);
        mMakeEntryBtn = view.findViewById(R.id.button);
        mREST = view.findViewById(R.id.REST);
        mREST.hide();
        student_list_recycler = view.findViewById(R.id.student_recyclerView);

        TextView textView = view.findViewById(R.id.TextLogo);
        textView.setText("Gather\nMetting");

        View view1 = view.findViewById(R.id.animation_view);
        view1.setBackgroundColor(getResources().getColor(R.color.babyPink));

        studentList = new ArrayList<>();
        studentAdapter = new StudentAdapter(studentList);
        retainList();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        student_list_recycler.setLayoutManager(layoutManager);
        student_list_recycler.setAdapter(studentAdapter);

        FragmentManager childFragment = getChildFragmentManager();
        mMakeEntryBtn.setOnClickListener(v -> {
            FragmentTransaction childFragmentTrans = childFragment.beginTransaction();
            BarCodeReader barCodeReader = new BarCodeReader();
            childFragmentTrans.add(R.id.childFragmentHolder, barCodeReader, "bar_code_reader");
            childFragmentTrans.addToBackStack(null);
            mREST.show();
            childFragmentTrans.commit();
            mMakeEntryBtn.setClickable(false);
        });

        mREST.setOnClickListener(v -> {
            List<Fragment> fragmentList = childFragment.getFragments();
            if (fragmentList != null) {
                if (fragmentList.size() > 0) {
                    Fragment fragment = fragmentList.get(0);
                    if (fragment != null) {
                        String fragmentTag = fragment.getTag();
                        assert fragmentTag != null;
                        if (fragmentTag.equals("bar_code_reader")) {
                            childFragment.popBackStackImmediate();
                            mREST.hide();
                            mMakeEntryBtn.setClickable(true);
                        }
                    }
                }
            }
        });
        return view;
    }

    public void updateStudentList(String date, String scannedData) {
        if (scannedData.equals("cdgi enter library")) {
            StudentMeetingDataModel x = new StudentMeetingDataModel("pratik katariya", "3", "CSE", date, "B");
            studentList.add(0, x);
            x.save();
            mREST.performClick();
            updateList();
        } else Toast.makeText(getActivity(), "wrong qr code", Toast.LENGTH_SHORT).show();
    }

    public void retainList() {
        List<StudentMeetingDataModel> s = StudentMeetingDataModel.listAll(StudentMeetingDataModel.class);
        for (StudentMeetingDataModel x : s)
            studentList.add(0, x);
        updateList();
    }

    public void updateList() {
        studentAdapter.notifyDataSetChanged();
    }
}
