package com.example.hanashops.model;

import java.util.List;

public class BudgetRequest {
    private String item;
    private List<VariationRequest> variations;
    private String image_url;
    private String phone_number;

    public BudgetRequest(String item, List<VariationRequest> variations, String image_url, String phone_number) {
        this.item = item;
        this.variations = variations;
        this.image_url = image_url;
        this.phone_number = phone_number;
    }
}
