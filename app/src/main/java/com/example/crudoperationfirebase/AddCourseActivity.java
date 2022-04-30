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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {

    private EditText editCourseName, editCoursePrice, editCourseSuitedFor, editCourseDescription, editCourseImageLink, editCourseLink;
    private Button btnAddCourse;
    private ProgressBar pbCourse;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String courseID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        editCourseName = findViewById(R.id.idEdtCourseName);
        editCoursePrice = findViewById(R.id.idEdtCoursePrice);
        editCourseSuitedFor = findViewById(R.id.idEdtCourseSuitedFor);
        editCourseDescription = findViewById(R.id.idEdtCourseDescription);
        editCourseImageLink = findViewById(R.id.idEdtCourseImageLink);
        editCourseLink = findViewById(R.id.idEdtCourseLink);
        btnAddCourse = findViewById(R.id.idBtnAddCourse);
        pbCourse = findViewById(R.id.idPbCourse);

        // make instance for this object firebaseDatabase
        firebaseDatabase = FirebaseDatabase.getInstance();
        // get reference for database and add the name of your database
        databaseReference = firebaseDatabase.getReference("Courses");


        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbCourse.setVisibility(View.VISIBLE);

                // make values for data to add in database , this is like tables
                String courseName = editCourseName.getText().toString();
                String coursePrice = editCoursePrice.getText().toString();
                String courseSuited = editCourseSuitedFor.getText().toString();
                String courseImgLink = editCourseImageLink.getText().toString();
                String courseLink = editCourseLink.getText().toString();
                String courseDescription = editCourseDescription.getText().toString();

                courseID = courseName;

                // i need to create model class to use in firebase and make object for this class
                CourseRVModel courseRVModel = new CourseRVModel(courseName, coursePrice, courseSuited, courseImgLink, courseLink, courseDescription, courseID);

                // in here i need to add this class in the database and make connection to database
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pbCourse.setVisibility(View.GONE);
                        databaseReference.child(courseID).setValue(courseRVModel);
                        Toast.makeText(AddCourseActivity.this, "Course Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddCourseActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        pbCourse.setVisibility(View.GONE);
                        Toast.makeText(AddCourseActivity.this, "you have problem check internet connection", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}