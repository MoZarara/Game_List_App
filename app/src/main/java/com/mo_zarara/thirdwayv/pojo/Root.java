package com.mo_zarara.thirdwayv.pojo;

import java.util.List;

public class Root {

    private List<GamesModel> results;

    public Root(List<GamesModel> results) {
        this.results = results;
    }


    public List<GamesModel> getResults() {
        return results;
    }

    public void setResults(List<GamesModel> results) {
        this.results = results;
    }
}
