package com.geekbrains.miniMarketApiTest;

import java.io.IOException;

import com.geekbrains.retrofit.api.MiniMarketApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MiniMarketApiTest {

    private static MiniMarketApiService apiService;

    @BeforeAll
    static void beforeAll() {
        apiService = new MiniMarketApiService();
    }

    @Test
    void testGetProductById() throws IOException {
//
//        Assertions.assertThrows(RuntimeException.class, () -> {
//            Product product = apiService.getProduct(100);
//        });

//        Assertions.assertEquals(1L, product.getId());
//        Assertions.assertEquals("Milk", product.getTitle());
//        Assertions.assertEquals("Food", product.getCategoryTitle());
    }

}
