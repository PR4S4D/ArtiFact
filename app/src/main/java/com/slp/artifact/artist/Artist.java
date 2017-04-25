package com.slp.artifact.artist;

import android.os.Parcel;
import android.os.Parcelable;

import com.slp.artifact.artist.similar.SimilarArtist;

import java.util.ArrayList;
import java.util.List;

public class Artist implements Parcelable {
    private String name;
    private long listeners;
    private String imageLink;
    private String publishedOn;
    private String summary;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getListeners() {
        return listeners;
    }

    public void setListeners(long listeners) {
        this.listeners = listeners;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        this.publishedOn = publishedOn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }

    public List<SimilarArtist> getSimilarArtists() {
        return similarArtists;
    }

    public void setSimilarArtists(List<SimilarArtist> similarArtists) {
        this.similarArtists = similarArtists;
    }

    private String wikiLink;
    private List<SimilarArtist> similarArtists;

    public Artist(){

    }


    protected Artist(Parcel in) {
        name = in.readString();
        listeners = in.readLong();
        imageLink = in.readString();
        publishedOn = in.readString();
        summary = in.readString();
        wikiLink = in.readString();
        if (in.readByte() == 0x01) {
            similarArtists = new ArrayList<SimilarArtist>();
            in.readList(similarArtists, SimilarArtist.class.getClassLoader());
        } else {
            similarArtists = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(listeners);
        dest.writeString(imageLink);
        dest.writeString(publishedOn);
        dest.writeString(summary);
        dest.writeString(wikiLink);
        if (similarArtists == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(similarArtists);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}