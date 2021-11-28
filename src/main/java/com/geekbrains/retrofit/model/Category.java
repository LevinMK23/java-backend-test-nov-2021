package com.geekbrains.retrofit.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Category {
    private Long id;
    private List<Product> products = new ArrayList<>();
    private String title;
}
