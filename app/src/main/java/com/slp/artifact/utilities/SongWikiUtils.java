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

    public static List<Artist> getTopChartArtists() throws IOException, JSONException {
        List<Artist> topChartArtist = new ArrayList<>();
        String topArtistDetails = NetworkUtils.getResponseFromHttpUrl(new URL(TOP_ARTISTS_END_POINT));
        JSONObject topArtistJsonObject = new JSONObject(topArtistDetails);
        JSONObject artists = topArtistJsonObject.getJSONObject(ARTISTS);
        JSONArray artistArray = (JSONArray) artists.get("artist");

        String name;
        long listeners;
        String imageLink;

        for (int i = 0; i < artistArray.length(); i++) {
            JSONObject artist = (JSONObject) artistArray.get(i);
            if (null != artist) {
                name = (String) artist.get("name");
                listeners = Long.valueOf((String) artist.get("listeners"));
                imageLink = getArtistImage((JSONArray) artist.get("image"));
                topChartArtist.add(new Artist(name, listeners, imageLink));
            }
        }
        return topChartArtist;
    }

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
                name = URLEncoder.encode(name, "UTF-8");
                URL url = new URL(ARTIST_INFO_END_POINT + name);
                String artistDetails = NetworkUtils.getResponseFromHttpUrl(url);
                Log.i("getTopChartArtist: ", artistDetails);
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
            artist.setImageLink(getArtistImage(artistObject.getJSONArray("image")));
        }
        return artist;
    }

    public static void setArtistDetails(Artist artist) throws IOException, JSONException {
        if(null != artist){
           String name = URLEncoder.encode(artist.getName(), "UTF-8");
            URL url = new URL(ARTIST_INFO_END_POINT + name);
            String artistDetails = NetworkUtils.getResponseFromHttpUrl(url);

            if (null != artistDetails) {
                JSONObject jsonObject = new JSONObject(artistDetails);
                JSONObject artistObject = jsonObject.getJSONObject("artist");

                JSONObject artistBio = artistObject.getJSONObject("bio");
                artist.setPublishedOn(artistBio.getString("published"));
                artist.setSummary(artistBio.getString("summary"));
            }
        }
    }

    private static String getArtistImage(JSONArray imageArray) throws JSONException {
        if (null != imageArray) {
            JSONObject imageObj = (JSONObject) imageArray.get(IMAGE_SIZE);
            if (null != imageObj) {
                return imageObj.getString("#text");
            }


        }
        return null;
    }
}

