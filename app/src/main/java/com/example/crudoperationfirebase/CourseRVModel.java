package com.example.crudoperationfirebase;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseRVModel implements Parcelable { // use class parcelable and implements the method
    private String courseName;
    private String coursePrice;
    private String courseSuitedFor;
    private String courseImgLink;
    private String courseLink;
    private String courseDescription;
    private String courseID;


    // you must to make this empty constructor to use in firebaseDatabase
    public CourseRVModel() {
    }

    public CourseRVModel(String courseName, String coursePrice, String courseSuitedFor, String courseImgLink, String courseLink, String courseDescription, String courseID) {
        this.courseName = courseName;
        this.coursePrice = coursePrice;
        this.courseSuitedFor = courseSuitedFor;
        this.courseImgLink = courseImgLink;
        this.courseLink = courseLink;
        this.courseDescription = courseDescription;
        this.courseID = courseID;
    }

    protected CourseRVModel(Parcel in) {
        courseName = in.readString();
        coursePrice = in.readString();
        courseSuitedFor = in.readString();
        courseImgLink = in.readString();
        courseLink = in.readString();
        courseDescription = in.readString();
        courseID = in.readString();
    }

    public static final Creator<CourseRVModel> CREATOR = new Creator<CourseRVModel>() {
        @Override
        public CourseRVModel createFromParcel(Parcel in) {
            return new CourseRVModel(in);
        }

        @Override
        public CourseRVModel[] newArray(int size) {
            return new CourseRVModel[size];
        }
    };

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseSuitedFor() {
        return courseSuitedFor;
    }

    public void setCourseSuitedFor(String courseSuitedFor) {
        this.courseSuitedFor = courseSuitedFor;
    }

    public String getCourseImgLink() {
        return courseImgLink;
    }

    public void setCourseImgLink(String courseImgLink) {
        this.courseImgLink = courseImgLink;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(courseName);
        parcel.writeString(coursePrice);
        parcel.writeString(courseSuitedFor);
        parcel.writeString(courseImgLink);
        parcel.writeString(courseLink);
        parcel.writeString(courseDescription);
        parcel.writeString(courseID);
    }
}
