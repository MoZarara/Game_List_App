package com.mo_zarara.thirdwayv.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "gameTable")
public class GamesModel {


    @PrimaryKey(autoGenerate = true)
    private int _id;

    private String id, name, released, background_image;

    private List<GenresModel> genres;



    public GamesModel(String id, String name, String released, String background_image, List<GenresModel> genres) {
        this.id = id;
        this.name = name;
        this.released = released;
        this.background_image = background_image;
        this.genres = genres;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public List<GenresModel> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresModel> genres) {
        this.genres = genres;
    }

    public String getBackground_image() {
        return background_image;
    }

    public void setBackground_image(String background_image) {
        this.background_image = background_image;
    }
}
