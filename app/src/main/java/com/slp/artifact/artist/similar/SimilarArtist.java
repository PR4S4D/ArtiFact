package com.slp.artifact.artist.similar;

import android.os.Parcel;
import android.os.Parcelable;

public class SimilarArtist implements Parcelable {
    private String name;
    private String imageLink;

    protected SimilarArtist(Parcel in) {
        name = in.readString();
        imageLink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageLink);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SimilarArtist> CREATOR = new Parcelable.Creator<SimilarArtist>() {
        @Override
        public SimilarArtist createFromParcel(Parcel in) {
            return new SimilarArtist(in);
        }

        @Override
        public SimilarArtist[] newArray(int size) {
            return new SimilarArtist[size];
        }
    };
}
