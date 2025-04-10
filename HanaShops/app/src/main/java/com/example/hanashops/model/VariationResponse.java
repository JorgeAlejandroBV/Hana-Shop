package com.example.hanashops.model;

import java.util.List;

public class VariationResponse {
    private List<Variation> data;

    public List<Variation> getVariations() {
        return data;
    }

    public void setData(List<Variation> data) {
        this.data = data;
    }
}
