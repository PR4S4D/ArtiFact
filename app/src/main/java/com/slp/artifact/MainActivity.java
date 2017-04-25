package com.slp.artifact;

import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.slp.artifact.artist.Artist;
import com.slp.artifact.utilities.SongWikiUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Artist>> {

    LoaderManager loaderManager;
    ListView topArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(123, null, this);
        topArtists = (ListView) findViewById(R.id.top_Artists);

    }

    @Override
    public Loader<List<Artist>> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<List<Artist>>(getApplicationContext()) {
            List<Artist> artists;
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public List<Artist> loadInBackground() {
                try {
                   artists = SongWikiUtils.getTopChartArtist();
                    Log.i("loadInBackground: ", artists.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                return artists;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> loader, List<Artist> artists) {
        List<String> artistList = new ArrayList<>();
        for (Artist artist : artists) {
            artistList.add(artist.getName());

        }
        Log.i("onLoadFinished: ",artistList.toString());
        topArtists.setAdapter(new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,artistList.toArray()));
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> loader) {

    }
}
