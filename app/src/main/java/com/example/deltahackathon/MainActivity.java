package com.example.deltahackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    ArrayList<ArrayList<GridItem>> myGrid = new ArrayList<>();
    ImageButton grid00,grid01,grid02,grid10,grid11,grid12,grid20,grid21,grid22,grid03,grid13,grid23,grid30,grid31,grid32,grid33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid00 = findViewById(R.id.block_00);
        grid01 = findViewById(R.id.block_01);
        grid02 = findViewById(R.id.block_02);
        grid03 = findViewById(R.id.block_03);
        grid10 = findViewById(R.id.block_10);
        grid11 = findViewById(R.id.block_11);
        grid12 = findViewById(R.id.block_12);
        grid13 = findViewById(R.id.block_13);
        grid20 = findViewById(R.id.block_20);
        grid21 = findViewById(R.id.block_21);
        grid22 = findViewById(R.id.block_22);
        grid23 = findViewById(R.id.block_23);
        grid30 = findViewById(R.id.block_30);
        grid31 = findViewById(R.id.block_31);
        grid32 = findViewById(R.id.block_32);
        grid33 = findViewById(R.id.block_33);
        for(int i=0;i<4;i++){
            ArrayList<GridItem> row = new ArrayList<>();
            for (int j=0;j<4;j++) {
                 GridItem item = new GridItem(GridItem.EMPTY);
                 row.add(item);
            }
            myGrid.add(row);
        }
        setMyGridImages();
    }

    public void setMyGridImages(){
        setMyGrid();
        grid00.setImageDrawable(getResources().getDrawable(imager(getBoard(0, 0))));
        grid01.setImageDrawable(getResources().getDrawable(imager(getBoard(0, 1))));
        grid02.setImageDrawable(getResources().getDrawable(imager(getBoard(0, 2))));
        grid10.setImageDrawable(getResources().getDrawable(imager(getBoard(1, 0))));
        grid11.setImageDrawable(getResources().getDrawable(imager(getBoard(1, 1))));
        grid12.setImageDrawable(getResources().getDrawable(imager(getBoard(1, 2))));
        grid20.setImageDrawable(getResources().getDrawable(imager(getBoard(2, 0))));
        grid21.setImageDrawable(getResources().getDrawable(imager(getBoard(2, 1))));
        grid22.setImageDrawable(getResources().getDrawable(imager(getBoard(2, 2))));
        grid30.setImageDrawable(getResources().getDrawable(imager(getBoard(3, 0))));
        grid31.setImageDrawable(getResources().getDrawable(imager(getBoard(3, 1))));
        grid32.setImageDrawable(getResources().getDrawable(imager(getBoard(3, 2))));
        grid33.setImageDrawable(getResources().getDrawable(imager(getBoard(3, 3))));
        grid03.setImageDrawable(getResources().getDrawable(imager(getBoard(0, 3))));
        grid13.setImageDrawable(getResources().getDrawable(imager(getBoard(1, 3))));
        grid23.setImageDrawable(getResources().getDrawable(imager(getBoard(2, 3))));
    }

    private void setMyGrid() {
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++){
                if(myGrid.get(i).get(j).getType()==GridItem.SOURCE){
                    switch (myGrid.get(i).get(j).getOutlet()){
                        case GridItem.UP:
                            setterFromSource(i-1,j,GridItem.DOWN);
                            break;
                        case GridItem.DOWN:
                            setterFromSource(i+1,j,GridItem.UP);
                            break;
                        case GridItem.LEFT:
                            setterFromSource(i,j-1,GridItem.RIGHT);
                            break;
                        case GridItem.RIGHT:
                            setterFromSource(i,j+1,GridItem.LEFT);
                            break;
                    }
                }
            }
    }

    private void setterFromSource(int i,int j,int inlet){
        if(i<=3&&i>=0&&j<=3&&j>=0){
            if(myGrid.get(i).get(j).getType()!=GridItem.SOURCE)
            {
                myGrid.get(i).get(j).setInlet(inlet);
                switch (myGrid.get(i).get(j).getOutlet()){
                    case GridItem.UP:
                        setterFromSource(i-1,j,GridItem.DOWN);
                        break;
                    case GridItem.DOWN:
                        setterFromSource(i+1,j,GridItem.UP);
                        break;
                    case GridItem.LEFT:
                        setterFromSource(i,j-1,GridItem.RIGHT);
                        break;
                    case GridItem.RIGHT:
                        setterFromSource(i,j+1,GridItem.LEFT);
                        break;
                }
            }
        }
    }

    public int imager(GridItem gridItem){
        if(gridItem.getType()==GridItem.EMPTY){
            if(gridItem.getOutlet()==GridItem.LEFT) return R.drawable.empty_left;
            else if(gridItem.getOutlet()==GridItem.RIGHT) return R.drawable.empty_right;
            else if(gridItem.getOutlet()==GridItem.DOWN) return R.drawable.empty_down;
            else if(gridItem.getOutlet()==GridItem.UP) return R.drawable.empty_up;
            else return R.drawable.empty_noinlet;
        }
        else if(gridItem.getType()==GridItem.SOURCE){
            if(gridItem.getOutlet()==GridItem.UP) return R.drawable.source_up;
            else if(gridItem.getOutlet()==GridItem.RIGHT) return R.drawable.source_right;
            else if(gridItem.getOutlet()==GridItem.DOWN) return R.drawable.source_down;
            else return R.drawable.source_left;
        }
        else if(gridItem.getType() == GridItem.BATTERY){
            if(gridItem.inlet==GridItem.NOTHING)return R.drawable.battery;
            else return R.drawable.battery_charged;
        }
        else{
            return R.drawable.ic_launcher_foreground;
        }
    }
    public void play(int i,int j){
        if(myGrid.get(i).get(j).getType()== GridItem.SOURCE || myGrid.get(i).get(j).getType()==GridItem.MIRROR){
            myGrid.get(i).get(j).flipIt();
            for(int k=0;k<4;k++)
                for(int l=0;l<4;l++)
                    if(myGrid.get(k).get(l).getType()==GridItem.EMPTY||myGrid.get(k).get(l).getType()==GridItem.BATTERY){
                        myGrid.get(k).get(l).inlet=myGrid.get(k).get(l).outlet=GridItem.NOTHING;
                    }
            setMyGridImages();
        }
    }

    public GridItem getBoard(int i,int j){
        return myGrid.get(i).get(j);
    }

    public void player(View view) {
        int caller = view.getId();
        switch (caller) {
            case R.id.block_00:
                play(0, 0);
                break;
            case R.id.block_01:
                play(0, 1);
                break;
            case R.id.block_02:
                play(0, 2);
                break;
            case R.id.block_10:
                play(1, 0);
                break;
            case R.id.block_11:
                play(1, 1);
                break;
            case R.id.block_12:
                play(1, 2);
                break;
            case R.id.block_20:
                play(2, 0);
                break;
            case R.id.block_21:
                play(2, 1);
                break;
            case R.id.block_03:
                play(0, 3);
                break;
            case R.id.block_13:
                play(1, 3);
                break;
            case R.id.block_23:
                play(2, 3);
                break;
            case R.id.block_30:
                play(3, 0);
                break;
            case R.id.block_31:
                play(3, 1);
                break;
            case R.id.block_32:
                play(3, 2);
                break;
            case R.id.block_33:
                play(3, 3);
                break;
            case R.id.block_22:
                play(2, 2);
                break;
        }
    }

    public void addElement(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = LayoutInflater.from(this).inflate(R.layout.popout,null);
        final EditText row = view1.findViewById(R.id.row),col = view1.findViewById(R.id.col);
        builder.setView(view1)
                .setTitle("Enter Element Location")
                .setPositiveButton("Battery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int rows = Integer.parseInt("0"+row.getText().toString()),cols = Integer.parseInt("0"+col.getText().toString());
                        if(rows<=3&&rows>=0&&cols<=3&&cols>=0) {
                            myGrid.get(rows).get(cols).setType(GridItem.BATTERY);
                            setMyGridImages();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Invalid location",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Mirror", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int rows = Integer.parseInt("0"+row.getText().toString()),cols = Integer.parseInt("0"+col.getText().toString());
                        if(rows<=3&&rows>=0&&cols<=3&&cols>=0) {
                            myGrid.get(rows).get(cols).setType(GridItem.MIRROR);
                            setMyGridImages();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Invalid location",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Source", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int rows = Integer.parseInt("0"+row.getText().toString()),cols = Integer.parseInt("0"+col.getText().toString());
                        if(rows<=3&&rows>=0&&cols<=3&&cols>=0) {
                            myGrid.get(rows).get(cols).setType(GridItem.SOURCE);
                            setMyGridImages();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Invalid location",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }
}
