package com.ltmt5.fpoly_friend_app.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.R;
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.ActivityQuestion5Binding;
import com.ltmt5.fpoly_friend_app.help.PublicData;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class Question5Activity extends AppCompatActivity implements HobbiesAdapter.ItemClick {
    ActivityQuestion5Binding binding;
    HobbiesAdapter hobbiesAdapter;
    List<Hobbies> hobbiesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestion5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
        binding.btnNext.setOnClickListener(v -> {
            if (validate()) {
            List<String> list = new ArrayList<>();
            for (Hobbies hobbies : hobbiesList) {
                list.add(hobbies.getName());
            }
            PublicData.profileTemp.setHobbies(list);
            startActivity(new Intent(this, WelcomeActivity.class));
            }
        });
        binding.btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void initView() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        hobbiesAdapter = new HobbiesAdapter(this, this);
        hobbiesAdapter.setData(getList(), "hoobies");
        binding.recHobbies.setLayoutManager(layoutManager);
        binding.recHobbies.setAdapter(hobbiesAdapter);
        hobbiesAdapter.setMultiple(true);
    }

    private void setClick() {
    }

    List<Hobbies> getList() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Th??? h??? 9x"));
        list.add(new Hobbies("Harry Potter"));
        list.add(new Hobbies("SoundCloud"));
        list.add(new Hobbies("Spa"));
        list.add(new Hobbies("Ch??m s??c b???n th??n"));
        list.add(new Hobbies("Heavy Metal"));
        list.add(new Hobbies("Ti???c gia ????nh"));
        list.add(new Hobbies("Gin Toxic"));
        list.add(new Hobbies("Th??? d???c d???ng c???"));
        list.add(new Hobbies("Hot Yoga"));
        list.add(new Hobbies("Thi???n"));
        list.add(new Hobbies("Sushi"));
        list.add(new Hobbies("Spotify"));
        list.add(new Hobbies("Hockey"));
        list.add(new Hobbies("B??ng r???"));
        list.add(new Hobbies("?????u th??"));
        list.add(new Hobbies("T???p luy???n t???i nh??"));
        list.add(new Hobbies("Nh?? h??t"));
        list.add(new Hobbies("Kh??m ph?? qu??n c?? ph??"));
        list.add(new Hobbies("Thu??? cung"));
        list.add(new Hobbies("Gi??y sneaker"));
        list.add(new Hobbies("Instagram"));
        list.add(new Hobbies("Su???i n?????c n??ng"));
        list.add(new Hobbies("??i d???o"));
        list.add(new Hobbies("Ch???y b???"));
        list.add(new Hobbies("Du l???ch"));
        list.add(new Hobbies("Giao l??u ng??n ng???"));
        list.add(new Hobbies("Phim ???nh"));
        list.add(new Hobbies("Ch??i guitar"));
        list.add(new Hobbies("Ph??t tri???n x?? h???i"));
        list.add(new Hobbies("T???p gym"));
        list.add(new Hobbies("M???ng x?? h???i"));
        list.add(new Hobbies("Hip-hop"));
        list.add(new Hobbies("Ch??m s??c da"));
        list.add(new Hobbies("J-pop"));
        list.add(new Hobbies("Shisha"));
        list.add(new Hobbies("Cricket"));
        list.add(new Hobbies("Phim truy???n h??nh H??n Qu???c"));
        return list;
    }

    boolean validate() {
        boolean isDone = true;
        if (hobbiesList.size() < 5) {
            Toast.makeText(this, "B???n ph???i ch???n ??t nh???t 5 s??? th??ch", Toast.LENGTH_SHORT).show();
            isDone = false;
        }
        return isDone;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void clickItem(Hobbies hobbies) {
        if (hobbies.isSelected()) {
            hobbiesList.add(hobbies);
        } else {
            hobbiesList.remove(hobbies);
        }
        if (hobbiesList.size() >= 5) {
            binding.btnNext.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_1));
            binding.tvNext.setText("Ti???p t???c 5/5");
        } else {
            binding.btnNext.setCardBackgroundColor(ContextCompat.getColor(this, R.color.prime_4));
            binding.tvNext.setText("Ti???p t???c " + hobbiesList.size() + "/5");
        }

    }
}