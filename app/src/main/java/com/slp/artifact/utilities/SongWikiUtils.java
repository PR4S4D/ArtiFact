package com.slp.artifact.utilities;


import android.util.Log;

import com.slp.artifact.artist.Artist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SongWikiUtils implements SongWikiConstants {

    public static List<String> getTopChartArtistNames() throws IOException, JSONException {
        List<String> topChartArtist = new ArrayList<>();
        String topArtistDetails = NetworkUtils.getResponseFromHttpUrl(new URL(TOP_ARTISTS_END_POINT));
        JSONObject topArtistJsonObject = new JSONObject(topArtistDetails);
        JSONObject artists = topArtistJsonObject.getJSONObject(ARTISTS);
        JSONArray artistArray = (JSONArray) artists.get("artist");
        for (int i = 0; i < artistArray.length(); i++) {
            JSONObject artist = (JSONObject) artistArray.get(i);
            if (null != artist) {
                topChartArtist.add(artist.getString("name"));
            }
        }
        return topChartArtist;
    }

    public static List<Artist> getTopChartArtist() throws IOException, JSONException {
        List<Artist> artists =
                new ArrayList<>();
        List<String> artistNames = getTopChartArtistNames();
        if (null != artistNames) {
            for (String name : artistNames) {
                 name = URLEncoder.encode(name,"UTF-8");
                URL url = new URL(ARTIST_INFO_END_POINT + name);
                String artistDetails = NetworkUtils.getResponseFromHttpUrl(url);
                Log.i( "getTopChartArtist: ",artistDetails);
                artists.add(getArtist(artistDetails));
            }
        }
        return artists;
    }

    public static Artist getArtist(String artistDetails) throws JSONException {
        Artist artist = new Artist();
        if (null != artistDetails) {
            JSONObject jsonObject = new JSONObject(artistDetails);
            JSONObject artistObject = jsonObject.getJSONObject("artist");
            artist.setName(artistObject.getString("name"));
            JSONArray imageArray = artistObject.getJSONArray("image");
            if (null != imageArray) {
                JSONObject imageObject = (JSONObject) imageArray.get(3);
                artist.setImageLink(imageObject.getString("#text"));

            }
        }
        return artist;
    }
}

