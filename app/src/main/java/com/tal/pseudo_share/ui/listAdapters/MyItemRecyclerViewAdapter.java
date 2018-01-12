package com.tal.pseudo_share.ui.listAdapters;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.model.utils.DateConverter;
import com.tal.pseudo_share.data.Pseudo;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final MutableLiveData<List<Pseudo>> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(MutableLiveData<List<Pseudo>> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setItem(mValues.getValue().get(position));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.getValue().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView author;
        public final TextView type;
        public final TextView date;
        public Pseudo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.name);
            author=view.findViewById(R.id.author);
            type=view.findViewById(R.id.type);
            date=view.findViewById(R.id.date);
        }

        public void setItem(Pseudo item) {
            this.mItem = item;
            name.setText(item.getName());
            author.setText(item.getAuthor());
            type.setText(item.getType());
            date.setText(DateConverter.onlyDate(DateConverter.toDate(item.getDate())));
        }
    }
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Pseudo item);
    }
}
