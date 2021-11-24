package com.geekbrains.restApi.imgur;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.API_URL + "/" + ImgurApiParams.API_VERSION;
    }

    @DisplayName("Тест на получение базовой информации об аккаунте")
    @Test
    @Order(1)
    void testAccountBase() {
        String url = "account/" + "levinmk23";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.bio", is("Ololo test"))
                .body("data.reputation", is(16))
                .log()
                .all()
                .when()
                .get(url);
    }

    @DisplayName("Тест обновления информации о картинке")
    @Test
    @Disabled
    @Order(2)
    void testUpdateImageInfo() {
        String imageHash = "JM5ALPM";
        String url = "image/" + imageHash;

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addFormParam("title", "Omg Braxton")
                .addFormParam("description", "Just a simple mem")
                .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("success", is(true))
                .expectBody("data", is(true))
                .expectStatusCode(200)
                .build();

        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .spec(requestSpecification)
                .expect()
                .log()
                .all()
                .spec(responseSpecification)
                .when()
                .post(url);

    }

}
