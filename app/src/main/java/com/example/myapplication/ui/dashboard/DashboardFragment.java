package com.example.myapplication.ui.dashboard;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.Post;
import com.example.myapplication.ui.PostsAdapter;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView mRecyclerView;
    private ArrayList<Post> mPostsData;
    private PostsAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Initialize the RecyclerView.
        mRecyclerView = root.findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the ArrayList that will contain the data.
        mPostsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new PostsAdapter(getContext(), mPostsData);
        mRecyclerView.setAdapter(mAdapter);

        // Get the data.
        initializeData();

        return root;
    }

    /**
     * Initialize the posts data from resources.
     */
    private void initializeData() {
        // Get the resources from the XML file.
        String[] postsList = getResources()
                .getStringArray(R.array.posts_titles);
        String[] postsInfo = getResources()
                .getStringArray(R.array.posts_info);

        // Clear the existing data (to avoid duplication).
        mPostsData.clear();
        TypedArray postsImageResources =
                getResources().obtainTypedArray(R.array.posts_images);

        // Create the ArrayList of Posts objects with titles and
        // information about each post.
        for(int i=0;i<postsList.length;i++){
            mPostsData.add(new Post(postsList[i],postsInfo[i],
                    postsImageResources.getResourceId(i,0)));
        }

        // Clean up the data in the typed array once you have created the Post data ArrayList:
        postsImageResources.recycle();

        // Notify the adapter of the change.
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
