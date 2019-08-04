package com.example.deltahackathon;

import android.drm.DrmStore;

public class GridItem {
    public static final int SOURCE = 1;
    public static final int BATTERY = 2;
    public static final int MIRROR = 3;
    public static final int EMPTY = 4;

    public static final int UP = -1;
    public static final int RIGHT = -2;
    public static final int DOWN = -3;
    public static final int LEFT = -4;

    public static final int NOTHING = 0;

    int type,inlet,outlet;

    public GridItem(int option) {
         type = option;
         if(type == SOURCE) {
             inlet = NOTHING;
             outlet = UP;
         }
         else if(type == BATTERY){
             inlet = outlet = NOTHING;
         }
         else inlet=outlet=NOTHING;

    }

    public GridItem(){
        type = EMPTY;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if(type == SOURCE) {
            inlet = NOTHING;
            outlet = UP;
        }
        else if(type == BATTERY){
            inlet = outlet = NOTHING;
        }
        else inlet=outlet=NOTHING;
    }

    public void setInlet(int inlet) {
        this.inlet = inlet;
        if(type==EMPTY) {
            if(inlet==LEFT) outlet= RIGHT;
            else if(inlet==RIGHT) outlet=LEFT;
            else if(inlet==UP) outlet=DOWN;
            else outlet=UP;
        }
        else if(type==MIRROR) {
            if(inlet!=LEFT)outlet=inlet-1;
            else outlet=UP;
        }
    }

    public int getInlet() {
        return inlet;
    }

    public int getOutlet() {
        return outlet;
    }

    public void flipIt(){
        if(type!=SOURCE) {
            if (inlet != LEFT) inlet--;
            else inlet = UP;
            setInlet(inlet);
        }
        else{
            outlet--;
            if(outlet==-5)outlet=UP;
        }
    }
}
