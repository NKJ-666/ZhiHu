package com.example.zhihu.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.zhihu.R;
import com.example.zhihu.bean.User;
import com.example.zhihu.databinding.ProfileFragmentBinding;
import com.example.zhihu.helper.MyDataBaseHelper;
import com.example.zhihu.util.GetRealUriUtil;
import com.example.zhihu.viewModel.LoginDialogViewModel;
import com.example.zhihu.viewModel.ProfileShareData;
import com.example.zhihu.viewModel.ProfileViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.Permission;

public class ProfileFragment extends Fragment {
    private ProfileFragmentBinding binding;
    private ProfileShareData shareData;
    private final MyDataBaseHelper helper;
    private User mUser;
    private ProfileViewModel viewModel;
    private static final int REQUEST_CAMERA = 1;
    private static final int GET_BACK_IMAGE = 2;

    public ProfileFragment(MyDataBaseHelper helper){
        this.helper = helper;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new ProfileViewModel(helper);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.profile_fragment, new LoginFragment(helper), null)
                .commit();
        binding.backgroudImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser != null){
                    chooseFromAlbum();
                }
            }
        });
        Glide.with(binding.backgroudImage.getContext()).load(R.drawable.test_backgroud).into(binding.backgroudImage);
        shareData = new ViewModelProvider(requireActivity()).get(ProfileShareData.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareData.getUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                mUser = user;
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.profile_fragment, new MyInfoFragment(helper, user), null)
                        .commit();
                if (user.getBackgroundUrl() == null){
                    Glide.with(binding.backgroudImage.getContext()).load(R.drawable.test_backgroud).into(binding.backgroudImage);
                }else {
                    Glide.with(binding.backgroudImage.getContext()).load(new File(user.getBackgroundUrl())).into(binding.backgroudImage);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case GET_BACK_IMAGE:{
                    String path = GetRealUriUtil.getImageRealPath(getContext(), data.getData());
                    mUser.setBackgroundUrl(path);
                    viewModel.updateUser(mUser);
                    Bitmap bitmap1 = BitmapFactory.decodeFile(path);
                    binding.backgroudImage.setImageBitmap(bitmap1);
                    break;
                }
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void chooseFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_BACK_IMAGE);
    }
}
