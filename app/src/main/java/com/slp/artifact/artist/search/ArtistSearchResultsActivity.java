package com.slp.artifact.artist.search;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slp.artifact.R;
import com.slp.artifact.activity.ArtistActivity;
import com.slp.artifact.adaptor.ArtistAdapter;
import com.slp.artifact.artist.Artist;
import com.slp.artifact.utilities.NetworkUtils;
import com.slp.artifact.utilities.SongWikiUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArtistSearchResultsActivity extends AppCompatActivity implements ArtistAdapter.ListItemClickListener {

    @Bind(R.id.rv_artists)
    RecyclerView rvArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_search_results);
        ButterKnife.bind(this);
        String searchQwery = getIntent().getStringExtra("artist");
        new SearchTask().execute(searchQwery);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Artist clickedArtist = ((ArtistAdapter) rvArtists.getAdapter()).getItem(clickedItemIndex);
        Intent intent = new Intent(this, ArtistActivity.class);
        intent.putExtra("artist", clickedArtist);
        startActivity(intent);

    }

    class SearchTask extends AsyncTask<String, Void, List<Artist>> {

        @Override
        protected List<Artist> doInBackground(String... params) {
            String artist = params[0];
            List<Artist> artistResult = null;
            try {
                artistResult = SongWikiUtils.getArtistResult(artist);
                Log.i("doInBackground: ", String.valueOf(artistResult));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return artistResult;
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            rvArtists.setAdapter(new ArtistAdapter(artists,ArtistSearchResultsActivity.this));
            int gridSize =2;
            rvArtists.setLayoutManager(new GridLayoutManager(ArtistSearchResultsActivity.this,gridSize));
            rvArtists.setHasFixedSize(true);
            super.onPostExecute(artists);
        }
    }


}
