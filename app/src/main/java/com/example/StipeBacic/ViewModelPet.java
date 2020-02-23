package com.example.StipeBacic;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelPet extends AndroidViewModel {
    private Repo repository;
    private LiveData<List<IDPet>>sveBiljeske;

    public ViewModelPet(@NonNull Application application) {
        super(application);
        repository = new Repo(application);
        sveBiljeske= repository.getAllBiljeske();
    }

    public void insert(IDPet IDPet) {
        repository.insert(IDPet);
    }

    public void update(IDPet IDPet) {
        repository.update(IDPet);
    }

    public void delete(IDPet IDPet) {
        repository.delete(IDPet);
    }

    public void deleteSveBiljeske() {
        repository.deleteSveBiljeske();
    }

    public LiveData<List<IDPet>>getAllBiljeske() {

        return sveBiljeske;
    }
}