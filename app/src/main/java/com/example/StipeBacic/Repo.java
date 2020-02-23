package com.example.StipeBacic;


import android.app.Application;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import java.util.List;

public class Repo {
    private query_pet querypet;
    private LiveData<List<IDPet>> allBiljeske;

    public Repo(Application application) {
        DBPet database = DBPet.getInstance(application);
        querypet = database.biljeskeQuery();
        allBiljeske = querypet.getAllBiljeske();
    }

    public void insert(IDPet IDPet) {
        new InsertNoteAsyncTask(querypet).execute(IDPet);
    }
    public void update(IDPet IDPet) {
        new UpdateNoteAsyncTask(querypet).execute(IDPet);
    }

    public void delete(IDPet IDPet) {
        new DeleteNoteAsyncTask(querypet).execute(IDPet);
    }

    public void deleteSveBiljeske() {
        new DeleteAllNotesAsyncTask(querypet).execute();
    }

    public LiveData<List<IDPet>> getAllBiljeske() {

        return allBiljeske;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<IDPet, Void, Void> {
        private query_pet querypet;

        private InsertNoteAsyncTask(query_pet querypet) {
            this.querypet = querypet;
        }

        @Override
        protected Void doInBackground(IDPet... biljeske) {
            querypet.insert(biljeske[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<IDPet, Void, Void> {
        private query_pet querypet;

        private UpdateNoteAsyncTask(query_pet querypet) {
            this.querypet = querypet;
        }

        @Override
        protected Void doInBackground(IDPet... biljeske) {
            querypet.update(biljeske[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<IDPet, Void, Void> {
        private query_pet querypet;

        private DeleteNoteAsyncTask(query_pet querypet) {
            this.querypet = querypet;
        }

        @Override
        protected Void doInBackground(IDPet... biljeske) {
            querypet.delete(biljeske[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private query_pet querypet;

        private DeleteAllNotesAsyncTask(query_pet noteDao) {
            this.querypet = querypet;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            querypet.deleteAllNotes();
            return null;
        }
    }
}