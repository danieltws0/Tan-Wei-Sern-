package com.example.asus.diceit;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.asus.diceit.R;
import com.example.asus.diceit.dice;
import com.example.asus.diceit.dicedata;

import org.w3c.dom.Comment;

import java.util.List;
import java.util.Random;

public class MainActivity extends ListActivity {
    private dicedata numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numbers = new dicedata(this);
        numbers.open();

        List<dice> values = numbers.getRandomNum();

        //use this SimpleCursorAdapter to show the element in a ListView
        ArrayAdapter<dice> adapter = new ArrayAdapter<dice>(this,
                android.R.layout.simple_list_item_1,values);
        setListAdapter(adapter);

    }
    //Will be called via the onClick attribute of the buttons in main.xml
    int Counter = 0;
    public void onClick(View view){
        @SuppressWarnings("unchecked")
        ArrayAdapter<dice> adapter = (ArrayAdapter<dice>) getListAdapter();
        dice randnum = null;
        switch (view.getId()){
            //ROLL DICE BUTTON
            case R.id.add:
                Counter++;
                String[] rnum = new String[] {"Move number: " + Counter + "\n" + "You rolled: [1]","Move number: " + Counter + "\n" + "You rolled: [2]","Move number: " + Counter + "\n" + "You rolled: [3]","Move number: " + Counter + "\n" + "You rolled: [4]","Move number: " + Counter + "\n" + "You rolled: [5]","Move number: " + Counter + "\n" + "You rolled: [6]"};
                int nextInt = new Random().nextInt(6);
                //save the new random number to the database
                randnum = numbers.generatenum(rnum[nextInt]);
                adapter.add(randnum);
                break;

                //DELETE OLDEST MOVE BUTTON
            case R.id.delete:
                if (getListAdapter().getCount() > 0){
                    randnum = (dice) getListAdapter().getItem(0);
                    numbers.deleteNum(randnum);
                    adapter.remove(randnum);
                }
                break;

            //RESTART GAME BUTTON
            case R.id.refresh:
                adapter.clear();
                Counter = 0;
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        numbers.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        numbers.close();
        super.onPause();
    }
}