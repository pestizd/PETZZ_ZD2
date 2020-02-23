package com.example.StipeBacic;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_BILJESKE_REQUEST = 1;
    public static final int EDIT_BILJESKE_REQUEST = 2;

    private ViewModelPet viewModelPet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_biljeske);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPet.class);
                startActivityForResult(intent, ADD_BILJESKE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final PetAdapter adapter = new PetAdapter();
        recyclerView.setAdapter(adapter);

        viewModelPet = ViewModelProviders.of(this).get(ViewModelPet.class);
        viewModelPet.getAllBiljeske().observe(this, new Observer<List<IDPet>>() {
            @Override
            public void onChanged(@Nullable List<IDPet> biljeske) {
                adapter.submitList(biljeske);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModelPet.delete(adapter.getBiljeskeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Bilješka izbrisina", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new PetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IDPet IDPet) {
                Intent intent = new Intent(MainActivity.this, AddPet.class);
                intent.putExtra(AddPet.EXTRA_ID, IDPet.getId());
                intent.putExtra(AddPet.EXTRA_NASLOV, IDPet.getNaslov());
                intent.putExtra(AddPet.EXTRA_VRSTA, IDPet.getDeskripcija());
                intent.putExtra(AddPet.EXTRA_GODINA, IDPet.getPrioritet());
                startActivityForResult(intent, EDIT_BILJESKE_REQUEST);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BILJESKE_REQUEST && resultCode == RESULT_OK) {
            String naslov = data.getStringExtra(AddPet.EXTRA_NASLOV);
            String deskripcija = data.getStringExtra(AddPet.EXTRA_VRSTA);
            int prioritet = data.getIntExtra(AddPet.EXTRA_GODINA, 1);

            IDPet IDPet = new IDPet(naslov, deskripcija, prioritet);
            viewModelPet.insert(IDPet);


            Toast.makeText(this, "Bilješka spremljena", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_BILJESKE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddPet.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Ne  moze se ažurirati", Toast.LENGTH_SHORT).show();
                return;

            }


            String title = data.getStringExtra(AddPet.EXTRA_NASLOV);

            String description = data.getStringExtra(AddPet.EXTRA_VRSTA);

            int priority = data.getIntExtra(AddPet.EXTRA_GODINA, 1);

            IDPet IDPet = new IDPet(title, description, priority);
            IDPet.setId(id);
            viewModelPet.update(IDPet);

            Toast.makeText(this, "Ažuriranje", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "nije spremljeno", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.glavni_izbornik, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.delete_all_biljeske:

                viewModelPet.deleteSveBiljeske();

                Toast.makeText(this, "Sve izbrisano", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}




