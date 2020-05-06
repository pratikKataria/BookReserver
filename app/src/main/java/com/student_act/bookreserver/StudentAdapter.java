package com.student_act.bookreserver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookreserver.R;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context context;
    private List<StudentMeetingDataModel> studList;

    public StudentAdapter(List<StudentMeetingDataModel> studList) {
        this.studList = studList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = studList.get(position).getName();
        String year = studList.get(position).getYear();
        String branch = studList.get(position).getBranch();
        String date = studList.get(position).getDateTime();
        String sec = studList.get(position).getSec();

        holder.setCard(
                name,
                year,
                branch,
                date,
                sec
        );

    }

    @Override
    public int getItemCount() {
        return studList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView name;
        private TextView branch;
        private TextView year;
        private TextView sec;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setCard(String nameString, String yearString, String branchString, String dateTimeString, String secString) {
            name = mView.findViewById(R.id.name_fill_row);
            year = mView.findViewById(R.id.year_fill_row);
            branch = mView.findViewById(R.id.branch_fill_row);
            date = mView.findViewById(R.id.date_fill_row);
            sec = mView.findViewById(R.id.sec_fill_row);

            name.setText(nameString);
            year.setText(yearString);
            branch.setText(branchString);
            date.setText(dateTimeString);
            sec.setText(secString);

        }
    }
}
