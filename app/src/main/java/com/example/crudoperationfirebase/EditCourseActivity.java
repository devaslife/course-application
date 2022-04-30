package com.example.crudoperationfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {
    private EditText editName, editPrice, editSuitedFor, editDescription, editImageLink, editLink;
    private Button btnUpdateCourse, btnDeleteCourse;
    private ProgressBar pbCourse;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String courseID;
    private CourseRVModel courseRVModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        // make instance and use the database
        firebaseDatabase = FirebaseDatabase.getInstance();

        editName = findViewById(R.id.idEditCourseName);
        editPrice = findViewById(R.id.idEditCoursePrice);
        editSuitedFor = findViewById(R.id.idEditCourseSuitedFor);
        editDescription = findViewById(R.id.idEditCourseDescription);
        editImageLink = findViewById(R.id.idEditCourseImageLink);
        editLink = findViewById(R.id.idEditCourseLink);

        btnUpdateCourse = findViewById(R.id.idBtnEditCourseUpdate);
        btnDeleteCourse = findViewById(R.id.idBtnDeleteCourse);

        pbCourse = findViewById(R.id.idPbEditCourse);


        courseRVModel = getIntent().getParcelableExtra("course");
        if (courseRVModel != null) {
            editName.setText(courseRVModel.getCourseName());
            editPrice.setText(courseRVModel.getCoursePrice());
            editSuitedFor.setText(courseRVModel.getCourseSuitedFor());
            editDescription.setText(courseRVModel.getCourseDescription());
            editImageLink.setText(courseRVModel.getCourseImgLink());
            editLink.setText(courseRVModel.getCourseLink());

            courseID = courseRVModel.getCourseID();

        }

        databaseReference = firebaseDatabase.getReference("Courses").child(courseID);

        btnUpdateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbCourse.setVisibility(View.VISIBLE);

                String courseName = editName.getText().toString();
                String coursePrice = editPrice.getText().toString();
                String courseSuited = editSuitedFor.getText().toString();
                String courseImgLink = editImageLink.getText().toString();
                String courseLink = editLink.getText().toString();
                String courseDescription = editDescription.getText().toString();

                // i need to use hashMap to save updated
                Map<String, Object> map = new HashMap<>();
                map.put("courseName", courseName);
                map.put("coursePrice", coursePrice);
                map.put("courseSuitedFor", courseSuited);
                map.put("courseImgLink", courseImgLink);
                map.put("courseLink", courseLink);
                map.put("courseDescription", courseDescription);
                map.put("courseID", courseID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pbCourse.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this, "updated successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pbCourse.setVisibility(View.GONE);
                        Toast.makeText(EditCourseActivity.this, "you have problem check internet connection", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnDeleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });

    }

    // this method to delete the course
    public void deleteCourse() {
        databaseReference.removeValue();
        Toast.makeText(this, "deleted successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditCourseActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}