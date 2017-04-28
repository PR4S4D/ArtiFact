package com.slp.artifact.artist.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.slp.artifact.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtistSearchResultsActivity extends AppCompatActivity {

    @Bind(R.id.search_query)TextView searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_search_results);
        ButterKnife.bind(this);
        searchQuery.setText(getIntent().getStringExtra("artist"));
    }
}
