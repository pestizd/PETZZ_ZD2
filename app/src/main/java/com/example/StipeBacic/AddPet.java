package com.example.StipeBacic;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPet extends AppCompatActivity {
    public static final String EXTRA_NASLOV =
            "EXTRA_NASLOV";
    public static final String EXTRA_VRSTA =
            "EXTRA_VRSTA";
    public static final String EXTRA_GODINA =
            "EXTRA_GODINA";
    public static final String EXTRA_ID =
            "EXTRA ID";

    private EditText editTextNaslov;
    private EditText editTextVrsta;
    private NumberPicker numberPickergodina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_menu);

        editTextNaslov = findViewById(R.id.edit_text_naslov);

        editTextVrsta = findViewById(R.id.edit_text_desckripcija);

        numberPickergodina = findViewById(R.id.number_picker_prioritet);

        numberPickergodina.setMinValue(0);

        numberPickergodina.setMaxValue(99);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Uredi");
            editTextNaslov.setText(intent.getStringExtra(EXTRA_NASLOV));

            editTextVrsta.setText(intent.getStringExtra(EXTRA_VRSTA));

            numberPickergodina.setValue(intent.getIntExtra(EXTRA_GODINA, 1));
        } else {
            setTitle("Dodaj");
        }
    }

    private void saveNote() {
        String naslov = editTextNaslov.getText().toString();
        String deskripcija = editTextVrsta.getText().toString();
        int prioritet = numberPickergodina.getValue();

        if (naslov.trim().isEmpty() || deskripcija.trim().isEmpty()) {
            Toast.makeText(this, "Molimo vas unesite naslov i opis", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NASLOV,naslov);
        data.putExtra(EXTRA_VRSTA, deskripcija);
        data.putExtra(EXTRA_GODINA, prioritet);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();


        menuInflater.inflate(R.menu.biljeske_menu_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:


                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}