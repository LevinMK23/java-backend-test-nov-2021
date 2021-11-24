package com.geekbrains.miniMarketApiTest;

import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
import com.geekbrains.retrofit.model.Category;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiniMarketApiTest {

    private static MiniMarketApiService apiService;
    private static Gson gson;
    private static Long id = 6L;

    @BeforeAll
    static void beforeAll() {
        apiService = new MiniMarketApiService();
        gson = new Gson();
    }

    @DisplayName("Тест на получение существующей категории по id")
    @Test
    void testGetCategoryById() throws IOException {
        Category category = apiService.getCategory(2);
        Assertions.assertEquals(2L, category.getId());
        Assertions.assertEquals(4L, category.getProducts().get(0).getId());
        Assertions.assertEquals("Samsung Watch X1000", category.getProducts().get(0).getTitle());
        Assertions.assertEquals(20000, category.getProducts().get(0).getPrice());
        Assertions.assertEquals("Electronic", category.getProducts().get(0).getCategoryTitle());
        Assertions.assertEquals(5L, category.getProducts().get(1).getId());
        Assertions.assertEquals("LG TV 1", category.getProducts().get(1).getTitle());
        Assertions.assertEquals(50000, category.getProducts().get(1).getPrice());
        Assertions.assertEquals("Electronic", category.getProducts().get(1).getCategoryTitle());
        Assertions.assertEquals("Electronic", category.getTitle());
    }

    @DisplayName("Тест на получение несуществующей категории по несуществующему id")
    @Test
    void testGetCategoryByNonExistentId() throws IOException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Category category = apiService.getCategory(100);
        });
    }

    @DisplayName("Тест на получение списка продуктов")
    @Test
    @Order(1)
    void testGetProducts() throws IOException {
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        String urlSource = "C:/Users/Public/Documents/JavaProjects/java-backend-test-nov-2021/target/test-classes" +
                "/com/geekbrains/miniMarketApiTest/testGetProducts/expected.json";
        String json = getJsonResource(urlSource);
        List<Product> expected = gson.fromJson(json, type);
        List<Product> actually = apiService.getProducts();
        Assertions.assertEquals(expected.size(), actually.size());
        for (int i = 0; i < expected.size(); i++) {
            assertProduct(expected.get(i), actually.get(i));
        }
    }

    @DisplayName("Тест на создание нового продукта")
    @Test
    @Order(2)
    void testCreateProduct() throws IOException {
        Product product = Product.builder()
                .title("The Astrobiologists: Volume 1")
                .price(250)
                .categoryTitle("Food")
                .build();
        id = apiService.createProduct(product);
        Product expected = apiService.getProduct(id);
        Assertions.assertEquals(id, expected.getId());
    }

    @DisplayName("Тест на обновление информации о продукте")
    @Order(3)
    @Test
    void testUpdateProduct() throws IOException {
        Product updateProduct = Product.builder()
                .id(id)
                .categoryTitle("Electronic")
                .price(300)
                .title("The Astrobiologists: Volume 2")
                .build();
        apiService.updateProduct(updateProduct);
        Product newContentProduct = apiService.getProduct(id);
        Assertions.assertEquals("The Astrobiologists: Volume 2", newContentProduct.getTitle());
        Assertions.assertEquals(300, newContentProduct.getPrice());
        Assertions.assertEquals("Electronic", newContentProduct.getCategoryTitle());
    }

    @DisplayName("Тест на получение продукта по айди")
    @Test
    @Order(4)
    void testGetProductById() throws IOException {
        Product product = apiService.getProduct(id);
        Assertions.assertEquals(id, product.getId());
        Assertions.assertEquals("The Astrobiologists: Volume 2", product.getTitle());
        Assertions.assertEquals(300, product.getPrice() );
        Assertions.assertEquals("Electronic", product.getCategoryTitle());
    }

    @DisplayName("Тест на получение несуществующего продукта по несуществующему id")
    @Test
    void testGetProductByNonExistentId() throws IOException {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = apiService.getProduct(100);
        });
    }

    @DisplayName("Тест на удаление продукта по id")
    @Test
    @Order(5)
    void testDeleteProduct() throws IOException {
        Assertions.assertThrows(EOFException.class, () -> {
            apiService.deleteProduct(id);
        });
        Assertions.assertThrows(RuntimeException.class, () -> {
            apiService.getProduct(id);
        });
    }

    String getJsonResource(String resource) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(resource));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    void assertProduct(Product expected, Product actually) {
        Assertions.assertEquals(expected.getId(), actually.getId());
        Assertions.assertEquals(expected.getTitle(), actually.getTitle());
        Assertions.assertEquals(expected.getCategoryTitle(), actually.getCategoryTitle());
        Assertions.assertEquals(expected.getPrice(), actually.getPrice());
    }

}