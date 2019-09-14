package com.raisac.plapp;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SelectableAdapter extends RecyclerView.Adapter implements SelectebleVewholder.OnItemSelectedListener {

    private final List<SelectableItem> mValues;
    SelectebleVewholder.OnItemSelectedListener listener;
    private boolean isMultiSelectionEnabled = false;


    public SelectableAdapter(SelectebleVewholder.OnItemSelectedListener listener,
                             List<SongInfo> items, boolean isMultiSelectionEnabled) {
        this.listener = listener;
        this.isMultiSelectionEnabled = isMultiSelectionEnabled;

        mValues = new ArrayList<>();
        for (SongInfo item : items) {
            mValues.add(new SelectableItem(item, false));
        }
    }

    @Override
    public SelectebleVewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.create_p_module, parent, false);

        return new SelectebleVewholder(itemView, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        SelectebleVewholder holder = (SelectebleVewholder) viewHolder;
        SelectableItem selectableItem = mValues.get(position);
        String name = selectableItem.getSongname();
        holder.textView.setText(name);
        if (isMultiSelectionEnabled) {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        } else {
            TypedValue value = new TypedValue();
            holder.textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorSingle, value, true);
            int checkMarkDrawableResId = value.resourceId;
            holder.textView.setCheckMarkDrawable(checkMarkDrawableResId);
        }

        holder.mItem = selectableItem;
        holder.setChecked(holder.mItem.isSelected());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public List<SongInfo> getSelectedItems() {

        List<SongInfo> selectedItems = new ArrayList<>();
        for (SelectableItem item : mValues) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (isMultiSelectionEnabled) {
            return SelectebleVewholder.MULTI_SELECTION;
        } else {
            return SelectebleVewholder.SINGLE_SELECTION;
        }
    }

    @Override
    public void onItemSelected(SelectableItem item) {
        if (!isMultiSelectionEnabled) {

            for (SelectableItem selectableItem : mValues) {
                if (!selectableItem.equals(item)
                        && selectableItem.isSelected()) {
                    selectableItem.setSelected(false);
                } else if (selectableItem.equals(item)
                        && item.isSelected()) {
                    selectableItem.setSelected(true);
                }
            }
            notifyDataSetChanged();
        }
        listener.onItemSelected(item);
    }
}
