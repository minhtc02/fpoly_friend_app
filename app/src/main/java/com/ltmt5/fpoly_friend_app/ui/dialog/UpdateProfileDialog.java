package com.ltmt5.fpoly_friend_app.ui.dialog;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.HobbiesAdapter;
import com.ltmt5.fpoly_friend_app.databinding.DialogUpdateProfileBinding;
import com.ltmt5.fpoly_friend_app.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class UpdateProfileDialog extends BaseDialogFragment implements HobbiesAdapter.ItemClick {
    DialogUpdateProfileBinding binding;
    OnClickListener onClickListener;
    String name = "name";
    String data = "none";
    HobbiesAdapter hobbiesAdapter;
    List<Hobbies> list;
    List<String> stringList;

    public UpdateProfileDialog() {
    }

    public UpdateProfileDialog(String name, String data) {
        this.name = name;
        this.data = data;
    }

    public static UpdateProfileDialog newInstance(String name, String data) {
        return new UpdateProfileDialog(name, data);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        binding = DialogUpdateProfileBinding.inflate(inflater, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        list = new ArrayList<>();
        stringList = new ArrayList<>();
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(App.context);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        layoutManager.setAlignItems(AlignItems.CENTER);
        hobbiesAdapter = new HobbiesAdapter(App.context, this);
        binding.rec.setLayoutManager(layoutManager);
        binding.rec.setAdapter(hobbiesAdapter);
        switch (name) {
            case "age":
                binding.tv1.setText("N??m sinh");
                binding.rec.setVisibility(View.GONE);
                binding.ed1.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "gender":
                binding.tv1.setText("Gi???i t??nh");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListGender(), data);
                list.add(new Hobbies(data));
                break;
            case "education":
                binding.tv1.setText("Chuy??n ng??nh");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListEducation(), data);
                list.add(new Hobbies(data));
                break;
            case "hobbies":
                binding.tv1.setText("S??? th??ch");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getHoobies(), data);
                hobbiesAdapter.setMultiple(true);
                list.add(new Hobbies(data));
                break;
            case "description":
                binding.tv1.setText("M?? t???");
                binding.rec.setVisibility(View.GONE);
                break;
            case "location":
                binding.tv1.setText("?????a ch???");
                binding.rec.setVisibility(View.GONE);
                break;
            case "zodiac":
                binding.tv1.setText("Cung ho??ng ?????o");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListZodiac(), data);
                list.add(new Hobbies(data));
                break;
            case "personality":
                binding.tv1.setText("T??nh c??ch");
                binding.rec.setVisibility(View.GONE);
                break;
            case "favoriteSong":
                binding.tv1.setText("B??i h??t y??u th??ch");
                binding.rec.setVisibility(View.GONE);
                break;
            case "sexualOrientation":
                binding.tv1.setText("Xu h?????ng t??nh d???c");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListSexualOrientation(), data);
                list.add(new Hobbies(data));
                break;
            case "showPriority":
                binding.tv1.setText("??u ti??n hi???n th???");
                binding.cvEd.setVisibility(View.GONE);
                hobbiesAdapter.setData(getListShowPriority(), data);
                list.add(new Hobbies(data));
                break;
            case "name":
            default:
                binding.tv1.setText("H??? t??n");
                binding.rec.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCancel.setOnClickListener(v -> {
            onClickListener.onCancel();
            dismiss();
        });
        binding.btnApply.setOnClickListener(v -> {
            String data = null;
            if (!name.equals("hobbies")) {
                if (name.equals("gender") || name.equals("education") || name.equals("sexualOrientation") || name.equals("zodiac") || name.equals("showPriority")) {
                    if (list.size() != 0) {
                        data = list.get(0).getName();
                    }
                } else {
                    data = binding.ed1.getText().toString().trim();
                }
            }
            for (Hobbies hobbies : list) {
                stringList.add(hobbies.getName());
            }
            if (validate()) {
                onClickListener.onApply(data, stringList);
                dismiss();
            }
        });
    }

    private boolean validate() {
        boolean isDone = true;
        switch (name) {
            case "age":
                if (binding.ed1.getText().toString().toString().equals("")) {
                    Toast.makeText(getActivity(), "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                    isDone = false;
                }
                else {
                    int age = Integer.parseInt(binding.ed1.getText().toString().trim());
                    if (age < 1970 || age > 2022) {
                        Toast.makeText(getActivity(), "N??m kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                        isDone = false;
                    }
                }

                break;
            case "description":
            case "location":
            case "personality":
            case "favoriteSong":
            case "name":
                if (binding.ed1.getText().toString().toString().equals("")) {
                    Toast.makeText(getActivity(), "Kh??ng ???????c ????? tr???ng", Toast.LENGTH_SHORT).show();
                    isDone = false;
                }
                break;
            default:
                binding.tv1.setText("H??? t??n");
                binding.rec.setVisibility(View.GONE);
                break;

        }
        return isDone;
    }

    List<Hobbies> getListGender() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Nam"));
        list.add(new Hobbies("N???"));
        list.add(new Hobbies("Kh??c"));
        return list;
    }

    List<Hobbies> getListEducation() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Ph??t tri???n ph???n m???m"));
        list.add(new Hobbies("L???p tr??nh Web"));
        list.add(new Hobbies("L???p tr??nh Mobile"));
        list.add(new Hobbies("???ng d???ng ph???n m???m"));
        list.add(new Hobbies("X??? l?? d??? li???u"));
        list.add(new Hobbies("Digital Marketing"));
        list.add(new Hobbies("Marketing & Sale"));
        list.add(new Hobbies("Quan h??? c??ng ch??ng (PR) & T??? ch???c s??? ki???n"));
        list.add(new Hobbies("Qu???n tr??? Kh??ch s???n"));
        list.add(new Hobbies("Qu???n tr??? Nh?? h??ng"));
        list.add(new Hobbies("Logistic"));
        list.add(new Hobbies("C??ng ngh??? k??? thu???t ??i???u khi???n & T??? ?????ng ho??"));
        list.add(new Hobbies("C??ng ngh??? k??? thu???t ??i???n, ??i???n t???"));
        list.add(new Hobbies("??i???n c??ng nghi???p"));
        list.add(new Hobbies("Thi???t k??? ????? h???a"));
        list.add(new Hobbies("H?????ng d???n du l???ch"));
        list.add(new Hobbies("C??ng ngh??? k??? thu???t c?? kh?? "));
        list.add(new Hobbies("Ch??m s??c da v?? Spa"));
        list.add(new Hobbies("Trang ??i???m ngh??? thu???t"));
        list.add(new Hobbies("Phun th??u th???m m???"));
        list.add(new Hobbies("C??ng ngh??? m??ng"));
        return list;
    }

    List<Hobbies> getHoobies() {
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

    List<Hobbies> getListZodiac() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Cung B???o B??nh"));
        list.add(new Hobbies("Cung Song Ng??"));
        list.add(new Hobbies("Cung B???ch D????ng"));
        list.add(new Hobbies("Cung Kim Ng??u"));
        list.add(new Hobbies("Cung Song T???"));
        list.add(new Hobbies("Cung C??? Gi???i"));
        list.add(new Hobbies("Cung S?? T???"));
        list.add(new Hobbies("Cung X??? N???"));
        list.add(new Hobbies("Cung Thi??n B??nh"));
        list.add(new Hobbies("Cung B??? C???p"));
        list.add(new Hobbies("Cung Nh??n M??"));
        list.add(new Hobbies("Cung Ma K???t"));
        return list;
    }

    List<Hobbies> getListSexualOrientation() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("D??n th?????ng"));
        list.add(new Hobbies("D??? t??nh"));
        list.add(new Hobbies("?????ng t??nh"));
        list.add(new Hobbies("Chuy???n gi???i"));
        list.add(new Hobbies("Song t??nh"));
        list.add(new Hobbies("V?? t??nh"));
        list.add(new Hobbies("To??n t??nh"));
        list.add(new Hobbies("??i t??nh"));
        list.add(new Hobbies("Kh??c"));
        return list;
    }

    List<Hobbies> getListShowPriority() {
        List<Hobbies> list = new ArrayList<>();
        list.add(new Hobbies("Nam"));
        list.add(new Hobbies("N???"));
        list.add(new Hobbies("Kh??c"));
        list.add(new Hobbies("T???t c???"));
        return list;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        //Make dialog full screen with transparent background
        if (dialog != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCancelable(true);
        }
    }

    @Override
    public void clickItem(Hobbies hobbies) {
        if (!name.equals("hobbies")) {
            list.clear();
            list.add(hobbies);
        } else {
            if (hobbies.isSelected()) {
                list.add(hobbies);
            } else {
                list.remove(hobbies);
            }
        }
    }


    public interface OnClickListener {
        void onApply(String data, List<String> list);

        void onCancel();
    }
}


