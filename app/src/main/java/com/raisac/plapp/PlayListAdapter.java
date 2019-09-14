package com.raisac.plapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayListAdapter extends ArrayAdapter<Playlist> {

    private int mLayoutResource;
    private Context mContext;
    private LayoutInflater mInflater;
    public static String TAG ="PlayListAdapter";

    public PlayListAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
        mContext = context;
        mLayoutResource = resource;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class ViewHolder {
        TextView username, count, playlitsname, number_of_playlists;
        ImageView userpic, mTrash;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView =mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.username=convertView.findViewById(R.id.username_new_playlist_model);
            holder.playlitsname=convertView.findViewById(R.id.playlistname_new_playlist_model);
            holder.number_of_playlists =convertView.findViewById(R.id.playlistCount_new_palaylist_model);
            holder.userpic =convertView.findViewById(R.id.new_playlist_userpic_playlistmodel);
            holder.mTrash =convertView.findViewById(R.id.trash);

        }else {
            holder =(ViewHolder)convertView.getTag();
        }

        try{
            //set the playlistname
            holder.playlitsname.setText(getItem(position).getPlay_name());

            //set the number of songs in playlist
            String songsString = String.valueOf(getItem(position).getSongs().size());
            holder.count.setText(songsString);

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference.child("users")
                    .orderByKey()
                    .equalTo(getItem(position).getUid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot  singleSnapshot: dataSnapshot.getChildren()){
                        Log.d(TAG, "onDataChange: Found playlist creator: "
                        + singleSnapshot.getValue(User.class).toString());
                        String createdby = singleSnapshot.getValue(User.class).getUsername();
                        holder.username.setText(createdby);
                        ImageLoader.getInstance().displayImage(
                                singleSnapshot.getValue(User.class).getPhotoUrl(), holder.userpic);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }); holder.mTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getItem(position).getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        Log.d(TAG, "onClick: asking for permission to delete icon.");
                        ((New_homePage)mContext).showDeleteChatroomDialog(getItem(position).getChatroom_id());
                    }else{
                        Toast.makeText(mContext, "You didn't create this chatroom", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }catch (NullPointerException e){
            Log.e(TAG, "getView: NullPointerException: ", e.getCause() );
        }

        return convertView;
    }

}
