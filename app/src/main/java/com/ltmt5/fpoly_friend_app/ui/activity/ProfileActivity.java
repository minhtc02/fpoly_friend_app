package com.ltmt5.fpoly_friend_app.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.viewpager.widget.PagerAdapter;

import com.github.ybq.parallaxviewpager.ParallaxViewPager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.help.ImageLoader;
import com.ltmt5.fpoly_friend_app.model.Profile;
import com.ltmt5.fpoly_friend_app.ui.fragment.SwipeViewFragment;

public class ProfileActivity extends AppCompatActivity {
    private boolean swipeViewSource;
    private ParallaxViewPager parallaxViewPager;
    private ImageLoader imageLoader;
    private FloatingActionButton fab;
    private CardView profileImageCard;

    @SuppressLint({"RestrictedApi", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        imageLoader = App.getImageLoader();

        Profile userProfileInfo = (Profile) getIntent().getExtras().getParcelable(SwipeViewFragment.EXTRA_USER_PROFILE);
        swipeViewSource = getIntent().getExtras().getBoolean(SwipeViewFragment.EXTRA_SWIPE_VIEW_SOURCE);
        parallaxViewPager = findViewById(R.id.parallax_viewpager);
        TextView profileUsername = findViewById(R.id.profile_name);
        profileUsername.setText(userProfileInfo.getName() + ", " + userProfileInfo.getAge());

        TextView userDistance = findViewById(R.id.profile_about_distance);
        userDistance.setText(userProfileInfo.getName() + " miles away");
        profileImageCard = findViewById(R.id.user_swipe_card_view);
        TextView description = findViewById(R.id.profile_description);
        description.setText(userProfileInfo.getName());
        if (!swipeViewSource) {
            TextView matchValue = findViewById(R.id.profile_match_text_view);
            matchValue.setText("match in " + userProfileInfo.getName() + "%!");
            matchValue.setVisibility(View.VISIBLE);
            ImageView profileMatchHeart = findViewById(R.id.profile_match_heart);
            profileMatchHeart.setVisibility(View.VISIBLE);
        }
        fab = findViewById(R.id.profileFab);
        if (!swipeViewSource) fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
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

    private void initViewPager(final Profile userProfileInfo) {
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
//                imageLoader.downloadImage(userProfileInfo.parallax_viewpager_item.xml().get(position), imageView);
                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                return view;
            }

            @Override
            public int getCount() {
//                return userProfileInfo.getPhotoLinks().size();
                return 1;
            }
        };
        parallaxViewPager.setAdapter(adapter);
    }


    @SuppressLint("RestrictedApi")
    private void showFab() {
        fab.animate().cancel();
        fab.setScaleX(0f);
        fab.setScaleY(0f);
        fab.setAlpha(0f);
        fab.setVisibility(View.VISIBLE);
        fab.animate().setDuration(200).scaleX(1).scaleY(1).alpha(1).setInterpolator(new LinearOutSlowInInterpolator());
    }

    @SuppressLint("RestrictedApi")
    private void hideFab() {
        fab.animate().cancel();
        fab.animate().setDuration(200).scaleX(0).scaleY(0).alpha(0).setInterpolator(new LinearOutSlowInInterpolator());
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
        if (swipeViewSource) hideFab();
    }
}
