package com.example.sarvasva.app.fragments.clubProfiles;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sarvasva.R;
import com.example.sarvasva.app.Classes.HorizontalSliderAdapter;
import com.example.sarvasva.app.activities.ProductDetailsActivity;
import com.example.sarvasva.app.fragments.ClubDirectory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AveonProfile extends Fragment {

    FragmentTransaction fragmentTransaction;
    private void changeFragment(Fragment fragment) {
        fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.fui_slide_in_right,R.anim.fui_slide_out_left);
        fragmentTransaction.replace(R.id.main_activity_frame_layout, fragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    private FrameLayout parentFrameLayout;

    public String aveonGallaryList ;
    private RecyclerView photoGalleryRv;
    private Button viewAllGallery;

    private String president , jointPresident, vicePresident , clubAbout, firstHead, secondHead, thirdHead, fourthHead;

    private List<String> productImagesList = new ArrayList<>();
    private FirebaseFirestore firestore;

    private TextView clubNameTv ,clubAnnouncementTv , firstHeadTv, secondHeadTv, thirdHeadTv, fourthHeadTv,clubAboutTv , presidentTv , jointPresidentTv, vicePresidentTv, extraPresidentTv ;
    private ImageView clubDp , clubBackground;

    private ImageView link1Iv,link2Iv , link3Iv , link4Iv;
    private String  link1 , link2, link3 , link4;

    public AveonProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_profile, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                changeFragment(new ClubDirectory());

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        CardView card = view.findViewById(R.id.edc_vicepresident2);
        card.setVisibility(View.INVISIBLE);


        link1Iv= view.findViewById(R.id.president_lkdin);
        link2Iv= view.findViewById(R.id.jointpresident_lkdin);
        link3Iv= view.findViewById(R.id.vicepresident_lkdin);

        link4Iv= view.findViewById(R.id.extrapresident_lkdin);



        photoGalleryRv = view.findViewById(R.id.horizontalScrollRecycleView);
        viewAllGallery = view.findViewById(R.id.viewAllPhotoBtn);
        clubDp = view.findViewById(R.id.club_dp);
        clubAnnouncementTv = view.findViewById(R.id.club_announcements);
        clubAboutTv = view.findViewById(R.id.club_about);
        clubNameTv = view.findViewById(R.id.club_name);
        clubBackground = view.findViewById(R.id.club_cover);


        firstHeadTv = view.findViewById(R.id.firstheadTV);
        secondHeadTv = view.findViewById(R.id.secondHeadTv);
        thirdHeadTv = view.findViewById(R.id.thirdHeadTv);
        fourthHeadTv = view.findViewById(R.id.fourthHeadTv);
        jointPresidentTv = view.findViewById(R.id.club_joint_president);
        presidentTv = view.findViewById(R.id.club_president);
        vicePresidentTv = view.findViewById(R.id.club_vice_president);




        firstHeadTv.setText("Captain");
        secondHeadTv.setText("Vice-Captain");
        thirdHeadTv.setText("Vice-Captain");


        clubNameTv.setText("TEAM AVEON RACING");
        clubAnnouncementTv.setText("New REcruiment Soon ");
        clubDp.setImageResource(R.drawable.aveon_logo);
        clubBackground.setImageResource(R.drawable.aveoncar);


        viewAllGallery = view.findViewById(R.id.viewAllPhotoBtn);
        viewAllGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProductDetailsActivity.class);
                intent.putExtra("gallery_reference" , "aveon" );
                startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        productImagesList = new ArrayList<>();

        firestore = FirebaseFirestore.getInstance();



        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        photoGalleryRv.setLayoutManager(manager);



        //end gallary imag set

        FirebaseFirestore.getInstance().collection("CLUB_PROFILE").document(
                "AVEON")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {    DocumentSnapshot shot = task.getResult();
                    if (shot.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + shot.getData());


                        productImagesList = (List<String>) shot.get("aveon");

                        president = (String)shot.get("CAPTAIN");
                        jointPresident = (String)shot.get("SECOND VICE CAPTAIN");
                        vicePresident = (String)shot.get("VICE CAPTAIN");
                        clubAbout = (String)shot.get("ABOUT");

                        link1 =  (String)shot.get("LINK1");
                        link2 =  (String)shot.get("LINK2");
                        link3 =  (String)shot.get("LINK3");
                        link4 =  (String)shot.get("LINK4");
                        //setting the data
                        link1Iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gotoUrl(link1);
                            }
                        });
                        link2Iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gotoUrl(link2);
                            }
                        });
                        link3Iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gotoUrl(link3);
                            }
                        });
                        link4Iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gotoUrl(link4);
                            }
                        });



                        //setting the data

                        presidentTv.setText(president);
                        vicePresidentTv.setText(vicePresident);
                        jointPresidentTv.setText(jointPresident);
                        clubAboutTv.setText(clubAbout);


                        HorizontalSliderAdapter homePageAdapter = new HorizontalSliderAdapter(productImagesList);
                        photoGalleryRv.setAdapter(homePageAdapter);
                        homePageAdapter.notifyDataSetChanged();


                    }



                    else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }
        });









    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }
}