package com.example.myapplication.ui;

public class WorkoutRoutine {
    // Member variables representing the title and information about the post.
    private String title;
//    private String info;
//    private final int imageResource;

    /**
     * Constructor for the WorkoutRoutine data model.
     *
     * @param title The name if the post.
     */
    public WorkoutRoutine(String title) {
        this.title = title;
//        this.info = info;
//        this.imageResource = imageResource;
    }

    /**
     * Gets the title of the WorkoutRoutine.
     *
     * @return The title of the WorkoutRoutine.
     */
    String getTitle() {
        return title;
    }

    /**
     * Gets the info about the WorkoutRoutine.
     *
     * @return The info about the WorkoutRoutine.
     */
//    String getInfo() {
//        return info;
//    }
//
//    public int getImageResource() {
//        return imageResource;
//    }
}
