package com.example.crudoperationfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CourseRVAdapter.OnCourseItemClickListener {

    private RecyclerView recyclerView;
    private ProgressBar pbMain;
    private FloatingActionButton fbAdd;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private ArrayList<CourseRVModel> courseList;
    private RelativeLayout bottomSheetRL;

    private CourseRVAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // and in here i need to read the database and add in recyclerView
        recyclerView = findViewById(R.id.recyclerCourse);
        pbMain = findViewById(R.id.idMainPb);
        fbAdd = findViewById(R.id.ibFbAddCourse);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Courses");

        bottomSheetRL = findViewById(R.id.idRLBSheet);

        courseList = new ArrayList<>();
        adapter = new CourseRVAdapter(courseList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);


        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
                startActivity(intent);
            }
        });

        getAllCourses();
    }

    // this method to get all courses into database and add in recyclerView
    private void getAllCourses() {
        courseList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbMain.setVisibility(View.GONE);
                courseList.add(snapshot.getValue(CourseRVModel.class));
                adapter.notifyDataSetChanged(); // to update the values

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbMain.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                pbMain.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pbMain.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        displayBottomSheet(courseList.get(position));
    }

    // this method to bottom dialog
    private void displayBottomSheet(CourseRVModel courseRVModel) {
        // i need to use the method bottomSheet
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        // i need to inflate to items for this dialog
        TextView tvName, tvDescription, tvSuited, tvPrice;
        Button btnDetails, btnEdit;
        ImageView imgCourse;

        tvName = layout.findViewById(R.id.idTvCourseNameDialog);
        tvDescription = layout.findViewById(R.id.idTvCourseDescDialog);
        tvSuited = layout.findViewById(R.id.idTvCourseSuitedDialog);
        tvPrice = layout.findViewById(R.id.idTvCoursePriceDialog);

        btnEdit = layout.findViewById(R.id.idBtnEditCourseDialog);
        btnDetails = layout.findViewById(R.id.idBtnViewDetailsDialog);

        imgCourse = layout.findViewById(R.id.idImgCourseDialog);

        tvName.setText(courseRVModel.getCourseName());
        tvDescription.setText(courseRVModel.getCourseDescription());
        tvSuited.setText(courseRVModel.getCourseSuitedFor());
        tvPrice.setText("Rs. " + courseRVModel.getCoursePrice());

        Glide.with(MainActivity.this).load(courseRVModel.getCourseImgLink()).into(imgCourse);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditCourseActivity.class);
                intent.putExtra("course", courseRVModel);
                startActivity(intent);
            }
        });

        // this button to intent in the webSite
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(courseRVModel.getCourseLink()));
                startActivity(intent);
            }
        });
    }

    // i need to use the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.logOut:
                firebaseAuth.signOut();
                Toast.makeText(this, "user logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}