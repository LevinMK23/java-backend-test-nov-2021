package com.geekbrains.miniMarketApiTest;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.geekbrains.retrofit.api.MiniMarketApiService;
import com.geekbrains.retrofit.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiniMarketApiTest {

    private static MiniMarketApiService apiService;
    private static Gson gson;
    private static Long id = 0L;

    @BeforeAll
    static void beforeAll() {
        apiService = new MiniMarketApiService();
        gson = new Gson();
    }

    @Test
    void testGetProductByIdProductExist() throws IOException {

        Product product = apiService.getProduct(1);
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals("Milk", product.getTitle());
        Assertions.assertEquals("Food", product.getCategoryTitle());
    }

    @Test
    void testGetProductByIdProductNotExists() {

        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = apiService.getProduct(100);
        });
    }

    String getJsonResource() throws IOException, URISyntaxException {
        byte[] bytes = Files.readAllBytes(Paths.get((Objects.requireNonNull(getClass()
                .getResource("testGetProducts/expected.json"))).toURI()));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    @Test
    void testGetProducts() throws IOException, URISyntaxException {
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        String json = getJsonResource();
        List<Product> expected = gson.fromJson(json, type);
        List<Product> actually = apiService.getProducts();
        Assertions.assertEquals(expected.size(), actually.size());
        for (int i = 0; i < expected.size(); i++) {
            assertProduct(expected.get(i), actually.get(i));
        }
    }

    @Test
    @Order(1)
    void testCreateNewProduct() throws IOException {
        Product product = Product.builder()
                .categoryTitle("Food")
                .price(300)
                .title("Fish")
                .build();
        id = apiService.createProduct(product);
        Product expected = apiService.getProduct(id);
        Assertions.assertEquals(id, expected.getId());
    }

    @Test
    @Order(2)
    void testDeleteById() {

        Assertions.assertThrows(EOFException.class, () -> {
            apiService.deleteProduct(id);
        });

        Assertions.assertThrows(RuntimeException.class, () -> {
            apiService.getProduct(id);
        });

    }


    void assertProduct(Product expected, Product actually) {
        Assertions.assertEquals(expected.getId(), actually.getId());
        Assertions.assertEquals(expected.getTitle(), actually.getTitle());
        Assertions.assertEquals(expected.getCategoryTitle(), actually.getCategoryTitle());
        Assertions.assertEquals(expected.getPrice(), actually.getPrice());
    }

}