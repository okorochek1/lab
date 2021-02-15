package com.lumi.threelabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URL;


public class ImagesFragment extends Fragment {

    private FloatingActionButton mFBtn;
    private ImageView mImg;
    private static final int REQUEST_CODE_CAMERA = 111;
    private static final int REQUEST_CODE_STORAGE = 112;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_images, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFBtn = view.findViewById(R.id.fab_addImages);
        mImg = view.findViewById(R.id.imageView);
        mFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogWindowSelected();
            }
        });
    }

    private void openDialogWindowSelected() {
        new AlertDialog.Builder(requireActivity()).setTitle("Выберите способ добавления фотографии")
                .setPositiveButton("Камера", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Вызов неявного интента к камере
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    }
                }).setNegativeButton("Хранилище", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Операции на доступ к хранилищу
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_STORAGE);
                    }
                }).create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null || data.getExtras() == null)return;
        switch (requestCode){
            case REQUEST_CODE_CAMERA: {
                setMainImg((Bitmap)data.getExtras().get("data"));
                break;
            }case REQUEST_CODE_STORAGE: {
                setMainImg(data.getData());
                break;
            }
    }
    }

    private void setMainImg(Bitmap img){
        mImg.setImageBitmap(img);
    }

    private void setMainImg(Uri uri){
        mImg.setImageURI(uri);
    }
}