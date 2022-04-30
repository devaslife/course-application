package com.example.crudoperationfirebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CourseRVAdapter extends RecyclerView.Adapter<CourseRVAdapter.CrudViewHolder> {
    private ArrayList<CourseRVModel> courseRVAdapters;
    private Context context;
    private int lastPos = -1;
    private OnCourseItemClickListener onCourseItemClickListener;

    public CourseRVAdapter(ArrayList<CourseRVModel> courseRVAdapters, Context context, OnCourseItemClickListener onCourseItemClickListener) {
        this.courseRVAdapters = courseRVAdapters;
        this.context = context;
        this.onCourseItemClickListener = onCourseItemClickListener;
    }

    @NonNull
    @Override
    public CrudViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_rv_items, parent, false);
        return new CrudViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CrudViewHolder holder, int position) {
        CourseRVModel courseRVModel = courseRVAdapters.get(position);
        holder.tvCourseName.setText(courseRVModel.getCourseName());
        holder.tvCoursePrice.setText("Rs. " + courseRVModel.getCoursePrice());

        //
        Glide
                .with(context)
                .load(courseRVModel.getCourseImgLink())
                .into(holder.imgCourse);

        setAnimation(holder.itemView, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCourseItemClickListener.onItemClicked(position);
            }
        });
    }

    // this method to make animation
    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;

        }
    }

    @Override
    public int getItemCount() {
        return courseRVAdapters.size();
    }

    public class CrudViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCourse;
        private TextView tvCourseName, tvCoursePrice;

        public CrudViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCourse = itemView.findViewById(R.id.idIVCourse);
            tvCourseName = itemView.findViewById(R.id.idTvCourseName);
            tvCoursePrice = itemView.findViewById(R.id.idTvCoursePrice);
        }
    }

    public interface OnCourseItemClickListener {
        void onItemClicked(int position);
    }
}
