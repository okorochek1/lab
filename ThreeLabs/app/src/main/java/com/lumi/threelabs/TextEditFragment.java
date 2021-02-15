package com.lumi.threelabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class TextEditFragment extends Fragment implements View.OnClickListener {

    private Button mSaveTextBtn;
    private Button mOpenTextBtn;
    private EditText mTextEditText;

    private boolean isOpenFile = false;
    private final String FILENAME = "example";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSaveTextBtn = view.findViewById(R.id.saveFile_btn);

        mTextEditText = view.findViewById(R.id.areaInput_editText);
        mSaveTextBtn.setOnClickListener(this);
        mOpenTextBtn = view.findViewById(R.id.openFile_btn);
        mOpenTextBtn.setOnClickListener(this);
        disableEditText();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.saveFile_btn: {
                if (isOpenFile) saveTextFile();
                break;
            } case R.id.openFile_btn: {
                openTextFile();
                break;
            }
        }
    }

    private void saveTextFile() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(requireActivity(), "Sd-карта недоступна", Toast.LENGTH_SHORT).show();
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(getExternalPath());
            fileOutputStream.write(mTextEditText.getText().toString().getBytes());
        } catch (FileNotFoundException e) {
            Toast.makeText(requireActivity(), "Файл не найден", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(requireActivity(), "Ошибка сохранения", Toast.LENGTH_LONG).show();
        }
        finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openTextFile() {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(requireActivity(), "Sd-карта недоступна", Toast.LENGTH_SHORT).show();
            return;
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(getExternalPath());
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            mTextEditText.setText(new String(bytes));
            enabledEditText();
        }catch (IOException ex){
            Toast.makeText(requireActivity(), "Ошибка при открытии файла", Toast.LENGTH_SHORT).show();
        }finally {
            try {
                if(fileInputStream !=null)fileInputStream.close();
            } catch (IOException e) {
                Toast.makeText(requireActivity(), "Ошибка закрытия поток", Toast.LENGTH_SHORT).show();
            }
        }
    }





    private void disableEditText(){
        mTextEditText.setFocusable(false);
        mTextEditText.setLongClickable(false);
        mTextEditText.setCursorVisible(false);
    }

    private void enabledEditText(){
        mTextEditText.setFocusable(true);
        mTextEditText.setLongClickable(true);
        mTextEditText.setCursorVisible(true);
    }

    private File getExternalPath(){
        return new File(Environment.getExternalStorageDirectory(), FILENAME);
    }
}