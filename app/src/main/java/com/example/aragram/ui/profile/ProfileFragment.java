package com.example.aragram.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aragram.R;

public class ProfileFragment extends Fragment {

    TextView noOfPosts;
    TextView noOfFollowers;
    TextView noOfFollowing;
    Button editButton;
    TextView bio;
    Button messageButton;
    ProfileViewModel profileViewModel;
    User finaluser;
    ProgressBar progressBar;
    TextView websiteUser;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_profile, container, false);

        profileViewModel=new ViewModelProvider(this,new ProfileViewModelFactory(getContext())).get(ProfileViewModel.class);
       profileViewModel.getProfileData();
        initializeViews(v);

        profileViewModel.userMutableLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {

                finaluser=user;
                Log.d("haha", "onChanged: "+"good luck"+finaluser.getUsername());
                setViews();
                usernameToolbar.setText(finaluser.getUsername());
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),Edit_Profile.class);
                startActivity(intent);
            }
        });
       noOfFollowers.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getActivity(), FollowersActivity.class);
               intent.putExtra("myProfile",finaluser.getUsername());
               intent.putExtra("flag",false);
               startActivity(intent);

           }
       });
       noOfFollowing.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent =new Intent(getActivity(), FollowersActivity.class);
               intent.putExtra("myProfile",finaluser.getUsername());
               intent.putExtra("flag",true);
               startActivity(intent);

           }
       });



        return v;
    }

    private void setViews() {
        noOfPosts.setText(String.valueOf(finaluser.getPosts()));
        noOfFollowing.setText(String.valueOf(finaluser.getFollowing()));
        noOfFollowers.setText(String.valueOf(finaluser.getFollowers()));
        progressBar.setVisibility(View.GONE);
        bio.setText(finaluser.getBio());
        if(finaluser.getUserProfilePicture()!=null)
        {
            Picasso.with(getContext()).load(finaluser.getUserProfilePicture()).into(profilePicture);

        }
        websiteUser.setText(finaluser.getWebsite());
    }

    private void initializeViews(View v)
    {
        bio=v.findViewById(R.id.textView4);
        toolbar=v.findViewById(R.id.main_toolbar);
        usernameToolbar=v.findViewById(R.id.toolbar_text);
        progressBar=v.findViewById(R.id.profile_progressbar);
        noOfFollowers=v.findViewById(R.id.number_of_followers);
        noOfFollowing=v.findViewById(R.id.number_of_following);
        noOfPosts=v.findViewById(R.id.number_of_posts);
        editButton=v.findViewById(R.id.edit_profile_button);
        //messageButton=v.findViewById(R.id.message_button);
        progressBar.setVisibility(View.VISIBLE);
        profilePicture=v.findViewById(R.id.user_profile_picture);
        websiteUser=v.findViewById(R.id.website_text);
}