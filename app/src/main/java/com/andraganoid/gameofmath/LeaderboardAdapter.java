package com.andraganoid.gameofmath;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LeaderboardAdapter extends BaseExpandableListAdapter {

    private Context context;
    //    private ArrayList <String> groupTitle;
//    private HashMap <String, ArrayList <String>> allBoards;
    private ArrayList <Level> levels;

    public LeaderboardAdapter(Context context, ArrayList <Level> levels) {
        this.context = context;
//        this.groupTitle = title;
////        this.allBoards = allBoards;
        this.levels = levels;
    }


    @Override
    public int getGroupCount() {
        return levels.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (groupPosition+1 < levels.size()) {
            return levels.get(groupPosition).getLevelName().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return levels.get(groupPosition).getGameName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ((levels.get(groupPosition).getLevelNameItem(childPosition)));
        //+ levels.get(groupPosition).getLevelDescItem(childPosition)
        //  return allBoards.get(groupTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.leaderboards_groups, null);
        }

        ((TextView) convertView.findViewById(R.id.lb_exp_title)).setText(levels.get(groupPosition).getGameName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.leaderboards_items, null);
        }

        ((TextView) convertView.findViewById(R.id.lb_exp_name)).setText(levels.get(groupPosition).getLevelNameItem(childPosition));
        ((TextView) convertView.findViewById(R.id.lb_exp_desc)).setText(levels.get(groupPosition).getLevelDescItem(childPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
