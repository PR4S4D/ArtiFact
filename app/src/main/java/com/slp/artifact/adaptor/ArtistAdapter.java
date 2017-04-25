package com.slp.artifact.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slp.artifact.R;
import com.slp.artifact.artist.Artist;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {


    private List<Artist> artists;
    final private ListItemClickListener onClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ArtistAdapter(List<Artist> artists, ListItemClickListener onClickListener) {
        this.artists = artists;
        this.onClickListener = onClickListener;
    }

    @Override
    public ArtistAdapter.ArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int artistLayout = R.layout.artist;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(artistLayout, viewGroup, false);
        return new ArtistViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ArtistAdapter.ArtistViewHolder holder, int position) {
        holder.artistName.setText(artists.get(position).getName());
        Picasso.with(holder.artistImage.getContext()).load(artists.get(position).getImageLink()).into(holder.artistImage);

    }

    @Override
    public int getItemCount() {
        if (null != artists)
            return artists.size();
        return 0;
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView artistImage;
        TextView artistName;

        public ArtistViewHolder(View itemView) {

            super(itemView);
            artistImage = (ImageView) itemView.findViewById(R.id.artist_image);
            artistName = (TextView) itemView.findViewById(R.id.artist_name);
            artistImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
