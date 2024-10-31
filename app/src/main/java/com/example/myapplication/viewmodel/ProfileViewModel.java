package com.example.myapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.myapplication.model.ProfileData;

public class ProfileViewModel extends ViewModel {
    // Holds the profile data that the UI observes
    private final MutableLiveData<ProfileData> profileData = new MutableLiveData<>();

    public LiveData<ProfileData> getProfileData() {
        return profileData;
    }

    public void loadProfile() {
        ProfileData profile = new ProfileData("Johnny Bravo", "Fitness Addict", "https://example.com/default_profile.jpg");
        profileData.setValue(profile);
    }

    // Method to update the profile picture URL only
    public void updateProfilePicture(String newProfilePictureUrl) {
        if (profileData.getValue() != null) {
            ProfileData currentProfile = profileData.getValue();
            ProfileData updatedProfile = new ProfileData(
                    currentProfile.getName(),
                    currentProfile.getBio(),
                    newProfilePictureUrl
            );
            profileData.setValue(updatedProfile);
        }
    }
}
