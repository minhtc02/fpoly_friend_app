package com.ltmt5.fpoly_friend_app.ui.fragment;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.SliderAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentUserBinding;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.SettingActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.UpdateProfileActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

public class UserFragment extends Fragment {
    FragmentUserBinding binding;
    MainActivity mainActivity;
    Context context;

    public UserFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater);
        mainActivity = (MainActivity) getActivity();
        context = App.context;

        final SliderAdapter sliderAdapter = new SliderAdapter(getActivity());
        binding.sliderView.setSliderAdapter(sliderAdapter);
        binding.sliderView.setIndicatorAnimation(IndicatorAnimationType.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.sliderView.setAutoCycleDirection(binding.sliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        binding.sliderView.startAutoCycle();
        setClick();
        initView();
        return binding.getRoot();
    }

    private void initView() {
        if (App.currentUser != null) {
            binding.tvName.setText(App.currentUser.getName() + " " + (2022 - App.currentUser.getAge()));
            Glide.with(context).load(App.currentUser.getImageUri()).error(R.drawable.demo).centerCrop().into(binding.profileImage);
        } else {
            Log.e(TAG, "user null");
        }
    }

    private void setClick() {
        binding.tvVIP.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
        });

        binding.btnAdd.setOnClickListener(view -> {
            mainActivity.loadFragment(new AddFragment(), R.id.navigation_add);
        });

        binding.btnUpdate.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
//            mainActivity.finish();
        });

        binding.btnSetting.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), SettingActivity.class));
//            mainActivity.finish();
        });
    }
}