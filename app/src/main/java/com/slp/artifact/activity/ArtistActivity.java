package com.slp.artifact.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.slp.artifact.R;
import com.slp.artifact.artist.Artist;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtistActivity extends AppCompatActivity {

    private Artist artist;
    @Bind(R.id.artist_image)
    ImageView artistImage;
    @Bind(R.id.listeners)
    TextView listeners;
    @Bind(R.id.artist_name)
    TextView artistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        ButterKnife.bind(this);
        artist =  getIntent().getParcelableExtra("artist");
        showArtistDetails();
    }

    private void showArtistDetails() {
        Picasso.with(this).load(artist.getImageLink()).into(artistImage);
        listeners.setText(String.valueOf(artist.getListeners()));
        artistName.setText(artist.getName());
    }
}
