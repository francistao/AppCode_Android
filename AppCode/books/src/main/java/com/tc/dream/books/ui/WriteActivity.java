package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tc.dream.books.R;
import com.tc.dream.books.db.ChangeSqlite;
import com.tc.dream.books.db.SqliteHelper;
import com.tc.dream.books.model.Notepad;
import com.tc.dream.books.view.DrawLine;

import java.util.Date;

/**
 * Created by dream on 15/12/20.
 */
public class WriteActivity extends NoteBookBaseActivity {
    private Button cancelButton;
    private Context context = this;
    private String date;
    private EditText editText;
    private Date getDate;
    private Button sureButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writedown);
        this.textView = ((TextView)findViewById(R.id.writedate));
        this.editText = ((DrawLine)findViewById(R.id.edittext));
        this.cancelButton = ((Button)findViewById(R.id.button));
        this.sureButton = ((Button)findViewById(R.id.button2));
        this.getDate = new Date();
        this.date = this.getDate.getDate()+"";
        this.textView.setText(this.date);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase localSqLiteDatabase = new SqliteHelper(WriteActivity.this.context, null, null, 0).getWritableDatabase();
                Notepad localNotepad = new Notepad();
                ChangeSqlite localChangeSqlite = new ChangeSqlite();
                String strContent = WriteActivity.this.editText.getText().toString();

                if (strContent.equals("")) {
                    Toast.makeText(WriteActivity.this.context, "您没有输入任何内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                String strTitle=strContent.length()>11?" "+strContent.substring(0, 11):strContent;
                localNotepad.setContent(strContent);
                localNotepad.setTitle(strTitle);
                localNotepad.setData(date);
                Log.d("---------->", date);
                localChangeSqlite.add(localSqLiteDatabase, localNotepad);

                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
