package com.example.StipeBacic;

import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "IDPet")
public class IDPet {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String naslov;

    private String deskripcija;

    private int prioritet;

    public IDPet(String naslov, String deskripcija, int prioritet) {
        this.naslov = naslov;
        this.deskripcija = deskripcija;
        this.prioritet= prioritet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getDeskripcija() {
        return deskripcija;
    }

    public int getPrioritet() {
        return prioritet;
    }
}