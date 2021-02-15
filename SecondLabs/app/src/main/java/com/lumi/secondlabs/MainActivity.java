package com.lumi.secondlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.lumi.secondlabs.list.CatAdapter;
import com.lumi.secondlabs.list.OnItemClickListener;
import com.lumi.secondlabs.model.Cat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnItemClickListener {

    private static final int INDEX_LINEAR = 0;
    private static final int INDEX_GRID = 1;

    private Spinner mSelectLayoutSpinner;
    private RecyclerView mListCatRecycler;
    private Toolbar mToolbar;

    private CatAdapter mAdapter;
    private GridLayoutManager layoutManager;

    private String[] mArrayLayout = {"Linear", "Grid"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectLayoutSpinner = findViewById(R.id.selectLayout_spinner);
        configurationSpinner();

        mListCatRecycler = findViewById(R.id.catList_recyclerView);
        layoutManager = new GridLayoutManager(this, 1);
        mListCatRecycler.setLayoutManager(layoutManager);
        mAdapter = new CatAdapter(createCatList(), layoutManager, this);
        mListCatRecycler.setAdapter(mAdapter);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.titleMainActivity);
        setSupportActionBar(mToolbar);


    }

    //i это позиция, а l это id

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case INDEX_LINEAR: {
                layoutManager.setSpanCount(1);
                break;
            } case INDEX_GRID: {
                layoutManager.setSpanCount(2);
                break;
            }
        }
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    @Override
    public void clickItem(Cat cat) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(Cat.class.getSimpleName(), cat);
        startActivity(intent);
    }

    private void configurationSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mArrayLayout);
        mSelectLayoutSpinner.setAdapter(adapter);
        //Выделяем по умолчанию первый элемент Linear
        mSelectLayoutSpinner.setSelection(0);
        mSelectLayoutSpinner.setOnItemSelectedListener(this);
    }

    private ArrayList<Cat> createCatList() {
        ArrayList<Cat> list = new ArrayList<>();
        list.add(new Cat(0, R.drawable.cat1, "Барсик", "Милый котик с тяжелой судьбой. Любит играть и воообще делать ужасные вещи"));
        list.add(new Cat(1, R.drawable.cat2, "Мурзик", "Любиабильный мурзик. Он постоянно мурчит у тебя за ухом и готов придти на помощь в любой ситуации. Ему 6 лет и уже любит кошачью мяту"));
        list.add(new Cat(2, R.drawable.cat3, "Бейсик", "Котенок рыжий, полосатый, серый, дымчатый, черный с белыми лапками, усатый, с маленькими ушками, пушистый, гладкошерстный, с большими ушами, толстый, большой, красивый, симпатичный, с пятнышком и т.д."));
        list.add(new Cat(3, R.drawable.cat4, "Ваня", "игривый, смешной, ловит мышей, любит смотреть в окно, играть, спать, бегать за игрушкой, играть с мышкой, аккуратный, послушный, спокойный, чистоплотный, шустрый, мурлыкает, дружит с собакой, попугаем."));
        list.add(new Cat(4, R.drawable.cat5, "Кабан", "Очень многое можно коротко наговорить про кота (кошечку, котенка), только лишь, при помощи одних эпитетов: озорной, шустрый, энергичный, хорошенький, пушистый, гладкошерстный, игривый, неугомонный, послушный, смешной, тихий, непривередливый, скромный, умный (смышленный), понимающий, красивый, любимый, хорошенький, милый, добрый, симпатичный, ленивый, обжористый, ласковый, диковатый, домашний и др."));
        list.add(new Cat(5, R.drawable.cat6, "Денис", "У меня есть прехорошенький, премиленький котенок серого цвета с белыми лапками. Ему три месяца, - еще маленький. Его имя Мартин. Живет это пушистое, энергичное создание в кухне и холле. С утра до вечера тот не сидит, ни минуты, на месте, а прыгает и бегает, - до чего неугомонный \"комочек счастья"));
        list.add(new Cat(6, R.drawable.cat7, "Марфа", "Мою кошка зовут Марфа - она чёрно-белого окраса. Спинка у неё чёрная, а грудка и концы лапок белые. Мордочка у неё тоже белого окраса, а около ушей и сами ушки чёрного окраса. Глаза у Марфы зелёные, а носик розовый. Шерсть у моей кошки короткая и блестящая. У Марфы длинные и тонкие лапы и длинный тонкий чёрный хвост, которым она полностью обвивает свои передние лапки, когда сидит. Ещё у Марфы очень длинные когти по сравнению с другими кошками - я замеряла длину её когтя линейкой и длина когтя получилась двенадцать миллиметров."));
        list.add(new Cat(7, R.drawable.cat8, "Лапушок", "У меня есть любимый домашний питомец - британский кот, он гладкошерстный, но это не \"мешает\" ему линять круглый год, он уж он \"шерстистый\". Ему уже почти 10 лет. Когда он был маленьким, то он был игривым, очень подвижным котенком. А вообще котята могут быть - озорными, шкодливыми, \"живыми\", ласковыми, спокойными, мырлычащими, иногда агрессивными. Когда бы его кастрировали, то он стал менее активным, прожорливым, очень любит спать, стал более спокойным и уравновешенным. Коты и кошки могут быть непослушными, забияками, драчливыми, толстыми, грациозными, уравновешенными по характеру. Могут быть пушистыми, лысыми, полосатыми, однотонными, черными, белыми, рыжими."));
        list.add(new Cat(8, R.drawable.cat9, "Добряк", "Милый котик с тяжелой судьбой. Любит играть и воообще делать ужасные вещи"));
        list.add(new Cat(9, R.drawable.cat10, "Джокер", "Милый котик с тяжелой судьбой. Любит играть и воообще делать ужасные вещи"));
        return list;
    }
}