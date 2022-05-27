package ru.mirea.netelev.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private EditText fileNameInput;
    private EditText textInput;
    private SharedPreferences preferences;
    private final String FILE_NAME = "file_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileNameInput = findViewById(R.id.editTextFileName);
        textInput = findViewById(R.id.editTextFileInput);
        preferences = getPreferences(MODE_PRIVATE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        String fileName = fileNameInput.getText().toString();
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FILE_NAME, fileName);
        editor.apply();
        try(FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE))
        {
            fileOutputStream.write(textInput.getText().toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        String fileName = preferences.getString(FILE_NAME, null);
        if (fileName != null)
        {
            try(FileInputStream fileInputStream = openFileInput(fileName)) {
                byte[] bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
                String content = new String(bytes);
                fileNameInput.setText(fileName);
                textInput.setText(content);
            } catch (IOException e) {
               e.printStackTrace();
           }
        }
    }
}