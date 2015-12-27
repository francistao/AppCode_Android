package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
 * 编辑界面
 * Created by dream on 15/12/17.
 */
public class EditActivity extends BookNoteActivity{

    private Button cancelButton;
    private String content;
    private Context context = this;
    private String date;
    private Date dateNow;
    private EditText editText;
    private String id;
    private Button sureButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showedit);
        this.textView = ((TextView) findViewById(R.id.editdate));
        this.editText = ((DrawLine) findViewById(R.id.edittexttwo));
        this.cancelButton = ((Button) findViewById(R.id.editbutton));
        this.sureButton = ((Button) findViewById(R.id.editbutton2));
        this.date = getIntent().getStringExtra("dateItem");
        this.content = getIntent().getStringExtra("contentItem");
        this.id = getIntent().getStringExtra("idItem");

        System.out.println("-----idItem-----id=" + id);
        this.editText.setSelection(this.editText.length());
        this.editText.setText(this.content);
        this.textView.setText(this.date);
        this.dateNow = new Date();
        this.date = this.dateNow.getDate()+"";
        this.textView.setText(this.date);
        sureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase localSqLiteDatabase = new SqliteHelper(
                        EditActivity.this.context, null, null, 0)
                        .getWritableDatabase();
                Notepad localNotepad = new Notepad();
                ChangeSqlite localChangeSqlite = new ChangeSqlite();
                String strContent = EditActivity.this.editText.getText()
                        .toString();
                if (strContent.equals("")) {
                    Toast.makeText(EditActivity.this.context, "您的输入为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String strTitle = strContent.length() > 11 ? " "
                        + strContent.substring(0, 11) : strContent;
                localNotepad.setContent(strContent);
                localNotepad.setTitle(strTitle);
                localNotepad.setData(date);
                localNotepad.setId(id);
                System.out.println("-----id-----id=" + id);
                localChangeSqlite.update(localSqLiteDatabase, localNotepad);
                finish();
            }
        });
    }
}
