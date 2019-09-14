package com.raisac.plapp;

public class SelectableItem extends SongInfo{
    private boolean isSelected = false;


    public SelectableItem(SongInfo item,boolean isSelected) {
        super(item.getSongname());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}