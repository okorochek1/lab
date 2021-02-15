package com.lumi.secondlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumi.secondlabs.model.Cat;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView mDescriptionTextView;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle arguments = getIntent().getExtras();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

        if (arguments != null){
            Cat cat = (Cat) arguments.get(Cat.class.getSimpleName());
            mImage = findViewById(R.id.imgCat_imgView);
            mImage.setImageResource(cat.getUrlImgRes());
            mDescriptionTextView = findViewById(R.id.descriptions_textView);
            mDescriptionTextView.setText(cat.getDescription());

            getSupportActionBar().setTitle(cat.getName());
        }
    }
}