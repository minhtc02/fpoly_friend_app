package com.ltmt5.fpoly_friend_app.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityProfileBinding;
import com.ltmt5.fpoly_friend_app.model.Hobbies;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.fragment.SwipeViewFragment;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements HobbiesAdapter.ItemClick {
    ActivityProfileBinding binding;
    UserProfile userProfileInfo;
    Context context;
    HobbiesAdapter hobbiesAdapter;
    private boolean swipeViewSource;
    private CardView profileImageCard;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        context = App.context;
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        hobbiesAdapter = new HobbiesAdapter(this, this);
        binding.recHobbies.setLayoutManager(layoutManager);
        binding.recHobbies.setAdapter(hobbiesAdapter);

        userProfileInfo = (UserProfile) getIntent().getSerializableExtra(SwipeViewFragment.EXTRA_USER_PROFILE);
        swipeViewSource = getIntent().getExtras().getBoolean(SwipeViewFragment.EXTRA_SWIPE_VIEW_SOURCE);


        binding.tvName.setText(userProfileInfo.getName());
        binding.tvAge.setText("" + (2022 - userProfileInfo.getAge()));
        binding.tvDistance.setText(userProfileInfo.getEducation());

        profileImageCard = findViewById(R.id.user_swipe_card_view);

        if (userProfileInfo.getDescription() != null) {
            binding.tvDescription.setText(userProfileInfo.getDescription());
        } else {
            binding.tvDescription.setText(userProfileInfo.getGender());
        }
        List<Hobbies> hobbies = new ArrayList<>();
        if (userProfileInfo.getHobbies() != null) {
            for (String s : userProfileInfo.getHobbies()) {
                hobbies.add(new Hobbies(s));
            }
            hobbiesAdapter.setData(hobbies, "none");
        }

        if (!swipeViewSource) binding.profileFab.setVisibility(View.VISIBLE);

        binding.profileFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(profileImageCard, "radius", 0);
                animator.setDuration(250);
                animator.start();
            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                if (swipeViewSource) showFab();
            }

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {

            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        initViewPager(userProfileInfo);
    }

    private void initViewPager(final UserProfile userProfileInfo) {
        PagerAdapter adapter = new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object obj) {
                container.removeView((View) obj);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(container.getContext(), R.layout.parallax_viewpager_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.item_img);
                Glide.with(context).load(userProfileInfo.getImageUri()).centerCrop().into(imageView);
//                Glide.with(context).load(Uri.parse(userProfileInfo.getImageUri())).centerCrop().error(R.drawable.demo1).into(imageView);
//                imageLoader.downloadImage(userProfileInfo.parallax_viewpager_item.xml().get(position), imageView);
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                return view;
            }

            @Override
            public int getCount() {
//                return bitmaps.size();
                return 1;
            }
        };
        binding.parallaxViewpager.setAdapter(adapter);
    }


    @SuppressLint("RestrictedApi")
    private void showFab() {
        binding.profileFab.animate().cancel();
        binding.profileFab.setScaleX(0f);
        binding.profileFab.setScaleY(0f);
        binding.profileFab.setAlpha(0f);
        binding.profileFab.setVisibility(View.VISIBLE);
        binding.profileFab.animate().setDuration(200).scaleX(1).scaleY(1).alpha(1).setInterpolator(new LinearOutSlowInInterpolator());
    }

    @SuppressLint("RestrictedApi")
    private void hideFab() {
        binding.profileFab.animate().cancel();
        binding.profileFab.animate().setDuration(200).scaleX(0).scaleY(0).alpha(0).setInterpolator(new LinearOutSlowInInterpolator());
        binding.profileFab.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
        if (swipeViewSource) hideFab();
    }

    @Override
    public void clickItem(Hobbies hobbies) {

    }
}
