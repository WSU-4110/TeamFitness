package com.example.myapplication.ui.progress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.databinding.FragmentProgressBinding;

import android.content.res.TypedArray;

import com.example.myapplication.R;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.Post;
import com.example.myapplication.ui.MilestonePostsAdapter;

import java.util.ArrayList;

public class ProgressFragment extends Fragment {

    private FragmentProgressBinding binding;
    private RecyclerView miRecyclerView;
    private ArrayList<Post> miPostsData;
    private MilestonePostsAdapter miAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProgressViewModel progressViewModel =
                new ViewModelProvider(this).get(ProgressViewModel.class);

        binding = FragmentProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProgress;
        progressViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Initialize the RecyclerView.
        miRecyclerView = root.findViewById(R.id.recyclerView);

// Set the RecyclerView to horizontal scrolling.
        miRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        miPostsData = new ArrayList<>();

        miAdapter = new MilestonePostsAdapter(getContext(), miPostsData);
        miRecyclerView.setAdapter(miAdapter);

        initializeData();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // remove the item from the database
                miPostsData.remove(viewHolder.getAdapterPosition());
                // notify the adapter about the removed item
                miAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // attach the helper to the recycler view
        helper.attachToRecyclerView(miRecyclerView);

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
        miPostsData.clear();
        TypedArray postsImageResources =
                getResources().obtainTypedArray(R.array.milestone_posts_images);

        // Create the ArrayList of Posts objects with titles and
        // information about each post.
        for(int i=0;i<postsList.length;i++){
            miPostsData.add(new Post(postsList[i],postsInfo[i],
                    postsImageResources.getResourceId(i,0)));
        }

        // Clean up the data in the typed array once you have created the Post data ArrayList:
        postsImageResources.recycle();

        // Notify the adapter of the change.
        miAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


//
//
//public class ProgressFragment extends Fragment {
//
//private FragmentProgressBinding binding;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//            ViewGroup container, Bundle savedInstanceState) {
//        ProgressViewModel progressViewModel =
//                new ViewModelProvider(this).get(ProgressViewModel.class);
//
//    binding = FragmentProgressBinding.inflate(inflater, container, false);
//    View root = binding.getRoot();
//
//        final TextView textView = binding.textNotifications;
//        progressViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
//        return root;
//    }
//
//@Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}