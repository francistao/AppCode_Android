package com.tc.dream.books.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dream on 15/12/20.
 */
public class NoteBookBaseActivity extends Activity{

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public void startActivity(Class<?> paramClass) {
        startActivity(new Intent(this, paramClass));
    }

}
