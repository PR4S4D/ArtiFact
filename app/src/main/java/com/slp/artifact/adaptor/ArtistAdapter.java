package com.slp.artifact.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.slp.artifact.R;
import com.slp.artifact.artist.Artist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> implements Filterable {


    private List<Artist> artists;
    private List<Artist> artistList;
    final private ListItemClickListener onClickListener;


    @Override
    public Filter getFilter() {

        return new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                artists = (List<Artist>) results.values;
                ArtistAdapter.this.notifyDataSetChanged();

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Artist> filteredResults = null;
                if (constraint.length() == 0) {
                    filteredResults = artistList;
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase());
                }

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;
            }
        };
    }

    protected List<Artist> getFilteredResults(String constraint) {
        List<Artist> results = new ArrayList<>();

        for (Artist artist : artists) {
            if (artist.getName().toLowerCase().contains(constraint)) {
                results.add(artist);
            }
        }
        return results;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public ArtistAdapter(List<Artist> artists, ListItemClickListener onClickListener) {
        this.artists = artists;
        this.artistList = artists;
        this.onClickListener = onClickListener;
    }

    @Override
    public ArtistAdapter.ArtistViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int artistLayout = R.layout.artist_card;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(artistLayout, viewGroup, false);
        return new ArtistViewHolder(view);

    }

    public Artist getItem(int position){
        if(null != artists)
            return  artists.get(position);
        return null;
    }

    @Override
    public void onBindViewHolder(ArtistAdapter.ArtistViewHolder holder, int position) {
        holder.artistName.setText(artists.get(position).getName());
        Picasso.with(holder.artistImage.getContext()).load(artists.get(position).getImageLink()).into(holder.artistImage);

    }

    public void filter(String text) {

        artists.clear();
        if(text.isEmpty()){
            artists.addAll(artists);
        } else{
            text = text.toLowerCase();
            for(Artist item: artists){
                if(item.getName().toLowerCase().contains(text) || item.getName().toLowerCase().contains(text)){
                    artists.add(item);
                }
            }
        }
        notifyDataSetChanged();
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
