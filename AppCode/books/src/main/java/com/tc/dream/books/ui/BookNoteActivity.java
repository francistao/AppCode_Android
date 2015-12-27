package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.tc.dream.books.R;
import com.tc.dream.books.adapter.BookNoteAdapter;
import com.tc.dream.books.db.ChangeSqlite;
import com.tc.dream.books.db.SqliteHelper;
import com.tc.dream.books.model.Notepad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;


/**
 * 读书笔记界面
 * Created by dream on 15/12/12.
 */
public class BookNoteActivity extends Activity {

    public String EXPANDED = "EXPANDED";
    public BookNoteAdapter adapter;
    public ArrayList<Map<String, Object>> itemList;
    public ListView listview;
    public int number;
    public Button numberButton;
    public Button topButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_note);
        this.numberButton = ((Button) findViewById(R.id.number));
        this.topButton = ((Button) findViewById(R.id.topButton));
        this.listview = ((ListView) findViewById(R.id.listview));
        // this.listView.setDivider(getResources().getDrawable(android.R.color.white));
        this.listview.setDivider(null);
        this.listview.setOnItemClickListener(new ItemClick());
        this.topButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookNoteActivity.this,
                        WriteActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUpdate();
    }

    public void showUpdate() {
        itemList = new ArrayList<Map<String, Object>>();
        SQLiteDatabase localSqLiteDatabase = new SqliteHelper(this, null, null, 0).getReadableDatabase();
        Iterator<Notepad> localIterator = new ChangeSqlite().query(localSqLiteDatabase).iterator();
        while(true)
        {
            if(!localIterator.hasNext())
            {
                Collections.reverse(this.itemList);
                adapter = new BookNoteAdapter(this, this.itemList);
                listview.setAdapter(adapter);
                if(itemList.size() == 0)
                {
                    number = 0;
                    numberButton.setText("");
                }
                return;
            }
            Notepad localNotepad = localIterator.next();
            HashMap<String, Object> localHashMap = new HashMap<String, Object>();
            localHashMap.put("titleItem", localNotepad.getTitle());
            localHashMap.put("dateItem", localNotepad.getData());
            localHashMap.put("contentItem", localNotepad.getContent());
            localHashMap.put("idItem", localNotepad.getId());
            localHashMap.put("EXPANDED", Boolean.valueOf(true));
            itemList.add(localHashMap);
            number = this.itemList.size();
            numberButton.setText("(" + this.number + ")");

        }
    }

    class ItemClick implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> localMap = itemList.get(position);
            if (((Boolean) localMap.get("EXPANDED")).booleanValue()) {
                localMap.put("EXPANDED", Boolean.valueOf(false));
            } else {
                localMap.put("EXPANDED", Boolean.valueOf(true));
            }
            BookNoteActivity.this.adapter.notifyDataSetChanged();
        }
    }
}
