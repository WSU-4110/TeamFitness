package com.example.myapplication.ui;

public class Post {
    // Member variables representing the title and information about the post.
    private String title;
//    private String info;
//    private final int imageResource;

    /**
     * Constructor for the Post data model.
     *
     * @param title The name if the post.
     */
    public Post(String title) {
        this.title = title;
//        this.info = info;
//        this.imageResource = imageResource;
    }

    /**
     * Gets the title of the Post.
     *
     * @return The title of the Post.
     */
    String getTitle() {
        return title;
    }

    /**
     * Gets the info about the Post.
     *
     * @return The info about the Post.
     */
//    String getInfo() {
//        return info;
//    }
//
//    public int getImageResource() {
//        return imageResource;
//    }
}
