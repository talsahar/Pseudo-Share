package com.tal.pseudo_share.ui.main.categories;

import android.arch.lifecycle.LiveData;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tal.pseudo_share.R;
import com.tal.pseudo_share.data.DateConverter;
import com.tal.pseudo_share.data.Pseudo;
import com.tal.pseudo_share.data.PseudoType;

import java.util.Date;
import java.util.List;

public class CategoriesViewAdapter extends RecyclerView.Adapter<CategoriesViewAdapter.ViewHolder>{

    private final PseudoType[] mValues;
    private final OnListFragmentInteractionListener mListener;

    public CategoriesViewAdapter(CategoriesMainFragment listener, PseudoType... items) {
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
        holder.setItem(mValues[position]);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView author;
        public final TextView type;
        public final TextView date;
        public PseudoType mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = view.findViewById(R.id.name);
            author=view.findViewById(R.id.author);
            type=view.findViewById(R.id.type);
            date=view.findViewById(R.id.date);
        }

        public void setItem(PseudoType item) {
            this.mItem = item;
            name.setText(item.name());
            author.setText("");
            type.setText("");
            date.setText("");
        }
    }
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(PseudoType item);
    }


}
