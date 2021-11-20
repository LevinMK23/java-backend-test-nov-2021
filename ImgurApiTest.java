package com.geekbrains.restApi.imgur;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;


import static com.geekbrains.restApi.imgur.ImgurApiParams.FILE_URL;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiTest {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParams.API_URL + "/" + ImgurApiParams.API_VERSION;

        requestSpecification = new RequestSpecBuilder()
                .setAuth(oauth2(ImgurApiParams.TOKEN))
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectBody("data", is(notNullValue()))
                .expectBody("success", is(true))
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType("application/json")
                .expectStatusCode(200)
                .build();
    }

    @DisplayName("Тест на получение базовой информации об аккаунте")
    @Test
    @Order(1)
    void testAccountBase() {
        String url = "/account/" + "LShubin";
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("data.url", is("LShubin"))
                .expectBody("data.reputation", is(0))
                .build();
        given().when()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .when()
                .get(url)
                .prettyPeek();
    }

    @DisplayName("Тест загрузки изображения")
    @Test
    @Order(2)
    void testUploadImage() {
        String url = "/image";
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("data.id", is(notNullValue()))
                .build();
        given().when()
                .spec(requestSpecification)
                .log()
                .all()
                .multiPart("image", FILE_URL)
                .expect()
                .spec(responseSpecification)
                .when()
                .post(url)
                .prettyPeek();
    }
    @DisplayName("Тест получения изображения")
    @Test
    @Order(3)
    void testGetImage() {
        String imageHash = "9nZOI7z";
        String url = "image/" + imageHash;
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("data.id", is(imageHash))
                .build();
        given().when()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .when()
                .get(url)
                .prettyPeek();
    }
    @DisplayName("Тест обновления информации о изображении")
    @Test
    @Order(4)
    void testUpdateImageInfo() {
        String imageHash = "9nZOI7z";
        String url = "image/" + imageHash;
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addFormParam("title", "Jhon Bon Jovi")
                .addFormParam("description", "My favorite car")
                .build();
        given().when()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .when()
                .post(url)
                .prettyPeek();
    }
    @DisplayName("Тест добавления изображения в избранное")
    @Test
    @Order(5)
    void testFavoriteAnImage() {
        String imageHash = "9nZOI7z";
        String url = "image/" + imageHash + "/favorite";
        given().when()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .when()
                .post(url)
                .prettyPeek();
    }

    @DisplayName("Тест добавления альбома")
    @Test
    @Order(6)
    void createAlbumTest() {
        String url = "album";
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addFormParam("title", "My first album")
                .addFormParam("description", "This album is cool")
                .build();
        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("data.id", is(notNullValue()))
                .build();
        given().when()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .when()
                .post(url)
                .prettyPeek();
    }

    @DisplayName("Тест удаления альбома")
    @Test
    @Order(7)
    void deleteAlbumTest() {
        String albumHash = "nOgRaZZ";
        String url = "album/" + albumHash;
        given().when()
                .spec(requestSpecification)
                .log()
                .all()
                .expect()
                .spec(responseSpecification)
                .when()
                .delete(url)
                .prettyPeek();
    }
}