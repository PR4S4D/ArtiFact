package com.slp.artifact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.slp.artifact.activity.ArtistActivity;
import com.slp.artifact.adaptor.ArtistAdapter;
import com.slp.artifact.artist.Artist;
import com.slp.artifact.artist.search.ArtistSearchResultsActivity;
import com.slp.artifact.utilities.SongWikiUtils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Artist>>, ArtistAdapter.ListItemClickListener {


    private LoaderManager loaderManager;
    private List<Artist> topArtists;
    private RecyclerView rvArtists;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(123, null, this);
        rvArtists = (RecyclerView) findViewById(R.id.rv_artists);
        // topArtists = (ListView) findViewById(R.id.top_Artists);
        RecyclerView.Adapter adapter = rvArtists.getAdapter();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem=  menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                ((ArtistAdapter)rvArtists.getAdapter()).getFilter().filter(query);
                Intent intent = new Intent(getApplicationContext(), ArtistSearchResultsActivity.class);
                intent.putExtra("artist",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                  ((ArtistAdapter)rvArtists.getAdapter()).getFilter().filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    private void initializeRecyclerView(List<Artist> artists) {
        if (null != artists) {
            topArtists = artists;
            rvArtists.setAdapter(new ArtistAdapter(artists, MainActivity.this));
            int gridSize =2;
            rvArtists.setLayoutManager(new GridLayoutManager(this,gridSize));
            rvArtists.setHasFixedSize(true);
        }
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
                    artists = SongWikiUtils.getTopChartArtists();
                    Log.i("loadInBackground: ", artists.toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

                return artists;
            }
        };
    }

    @Override
    public void onBackPressed() {
        searchView.onActionViewCollapsed();
            super.onBackPressed();

    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> loader, List<Artist> artists) {

        initializeRecyclerView(artists);
        Log.i("onLoadFinished: ", artists.toString());
        // topArtists.setAdapter(new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,artistList.toArray()));
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> loader) {

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(null != topArtists){
                Intent artistIntent = new Intent(this, ArtistActivity.class);
            Artist clickedArtist = ((ArtistAdapter)rvArtists.getAdapter()).getItem(clickedItemIndex);
            artistIntent.putExtra("artist",clickedArtist);
            startActivity(artistIntent);

        }
    }
}
