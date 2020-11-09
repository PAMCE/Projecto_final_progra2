package com.example.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.fragments.AmarilloFragment;
import com.example.myapplication.fragments.AzulFragment;
import com.example.myapplication.fragments.RojoFragment;
import com.example.myapplication.fragments.VerdeFragment;
import com.example.myapplication.main.SectionsPagerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class  InstruccionesActivity extends AppCompatActivity implements RojoFragment.OnFragmentInteractionListener, AzulFragment.OnFragmentInteractionListener, VerdeFragment.OnFragmentInteractionListener, AmarilloFragment.OnFragmentInteractionListener {
    ViewPager viewPager;
    private LinearLayout linearPuntos;
    private TextView[]puntosSlide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        linearPuntos=findViewById(R.id.idLinearPuntos);
        agregarIndicadorPuntos(0);
        viewPager.addOnPageChangeListener(viewListener);
    }
    private void agregarIndicadorPuntos(int pos){
        puntosSlide=new TextView[4];
        linearPuntos.removeAllViews();
        for(int i=0;i<puntosSlide.length;i++){
            puntosSlide[i]=new TextView(this);
            puntosSlide[i].setText(Html.fromHtml("&#8226;"));
            puntosSlide[i].setTextSize(35);
            puntosSlide[i].setTextColor(getResources().getColor(R.color.colorNegro));
            linearPuntos.addView(puntosSlide[i]);

        }
        if(puntosSlide.length>0){
            puntosSlide[pos].setTextColor(getResources().getColor(R.color.colorAmarillo));
        }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int pos) {
            agregarIndicadorPuntos(pos);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}