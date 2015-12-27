package com.tc.dream.books.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.text.BidiFormatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tc.dream.books.R;
import com.tc.dream.books.db.ChangeSqlite;
import com.tc.dream.books.db.SqliteHelper;
import com.tc.dream.books.model.Notepad;
import com.tc.dream.books.ui.BookNoteActivity;
import com.tc.dream.books.ui.EditActivity;
import com.tc.dream.books.view.TextViewLine;

import java.util.ArrayList;
import java.util.Map;

/**
 * 读书笔记适配器
 * Created by dream on 15/12/17.
 */
public class BookNoteAdapter extends BaseAdapter{

    public Context context;
    public Context activity;
    public LayoutInflater inflater;
    public ArrayList<Map<String, Object>> list;

    public BookNoteAdapter(Activity activity, ArrayList<Map<String, Object>> list) {

        this.context = activity;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        SetShow setShow = new SetShow();
        Map<String, Object> map = list.get(arg0);
        boolean boo = (Boolean) map.get("EXPANDED");
        if (!boo) {
            arg1 = inflater.inflate(R.layout.showtypes, arg2, false);
            setShow.contentView = (TextView) arg1
                    .findViewById(R.id.contentTextView);
            setShow.dateView = (TextView) arg1.findViewById(R.id.dateTextView);
            String str = (String) list.get(arg0).get("titleItem");
            String dateStr = (String) list.get(arg0).get("dateItem");
            setShow.contentView.setText("   " + str);
            setShow.dateView.setText(dateStr);
            setShow.showButtonWrite = (Button) arg1
                    .findViewById(R.id.smallbutton1);
            setShow.showButtonDelete = (Button) arg1
                    .findViewById(R.id.smallbutton2);
            setShow.showButtonWrite.setOnClickListener(new WriteButtonListener(
                    arg0));
            setShow.showButtonDelete
                    .setOnClickListener(new DeleteButtonListener(arg0));
        } else {
            arg1 = inflater.inflate(R.layout.style, arg2, false);
            setShow.cContentView = (TextViewLine) arg1
                    .findViewById(R.id.changecontentview);
            setShow.cDateView = (TextView) arg1
                    .findViewById(R.id.changedateview);
            String str = (String) list.get(arg0).get("contentItem");
            String dateStr = (String) list.get(arg0).get("dateItem");
            setShow.cContentView.setText("" + str);
            setShow.cDateView.setText(dateStr);
            setShow.styleButtonWrite = (Button) arg1
                    .findViewById(R.id.stylebutton1);
            setShow.styleButtonWrite
                    .setOnClickListener(new WriteButtonListener(arg0));
            setShow.styleButtonDelete = (Button) arg1
                    .findViewById(R.id.stylebutton2);
            setShow.styleButtonDelete
                    .setOnClickListener(new DeleteButtonListener(arg0));
        }
        return arg1;
    }

    class WriteButtonListener implements View.OnClickListener
    {

        private int position;

        public WriteButtonListener(int position)
        {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Bundle b = new Bundle();
            b.putString("contentItem", (String) list.get(position).get("contentItem"));
            b.putString("dateItem", (String) list.get(position).get("idItem"));
            b.putString("idItem", (String) list.get(position).get("idItem"));
            Intent intent = new Intent((BookNoteActivity)context, EditActivity.class);
            intent.putExtras(b);
            ((BookNoteActivity)context).startActivity(intent);

        }
    }

    class DeleteButtonListener implements View.OnClickListener
    {

        private int position;

        public DeleteButtonListener(int position)
        {
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("确定要删除读书笔记吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SqliteHelper sql = new SqliteHelper(context, null, null, 0);
                    SQLiteDatabase dataBase = sql.getWritableDatabase();
                    ChangeSqlite change = new ChangeSqlite();
                    Notepad notepad = new Notepad();
                    notepad.setId((String) list.get(position).get("idItem"));
                    change.delete(dataBase, notepad);
                    ((BookNoteActivity)context).showUpdate();
                }
            });
        }
    }

    class SetShow
    {
        public TextView contentView;
        public TextView dateView;
        public TextViewLine cContentView;
        public TextView cDateView;
        public Button styleButtonWrite;
        public Button styleButtonDelete;
        public Button showButtonWrite;
        public Button showButtonDelete;
    }
}
