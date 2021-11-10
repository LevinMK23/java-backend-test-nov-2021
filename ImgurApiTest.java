package com.geekbrains.restApi.imgur;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;


import static com.geekbrains.restApi.imgur.ImgurApiParams.FILE_URL;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static io.restassured.RestAssured.given;

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
        String url = "/account/" + "LShubin";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("status", is(200))
                .body("data.url", is("LShubin"))
                .body("data.reputation", is(0))
                .log()
                .all()
                .when()
                .get(url);
    }

    @DisplayName("Тест загрузки изображения")
    @Test
    @Order(2)
    void testUploadImage() {
        String url = "/image";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .multiPart("image", FILE_URL)
                .expect()
                .log()
                .all()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .contentType("application/json")
                .when()
                .post(url)
                .then()
                .statusCode(200);
                //.prettyPeek()
                //.extract()
                //.response()
                //.jsonPath()
                //.getString("data.id");

    }
    @DisplayName("Тест получения изображения")
    @Test
    @Order(3)
    void testGetImage() {
        String imageHash = "TGtQxL4";
        String url = "image/" + imageHash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("status", is(200))
                .body("success", is(true))
                .body("data.id", is(imageHash))
                .log()
                .all()
                .when()
                .get(url);
    }
    @DisplayName("Тест обновления информации о изображении")
    @Test
    @Order(4)
    void testUpdateImageInfo() {
        String imageHash = "TGtQxL4";
        String url = "image/" + imageHash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "Jhon Bon Jovi")
                .formParam("description", "My favorite car")
                .expect()
                .log()
                .all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .post(url);
    }
    @DisplayName("Тест метки любимое изображение")
    @Test
    @Order(5)
    void testFavoriteAnImage() {
        String imageHash = "TGtQxL4";
        String url = "image/" + imageHash + "/favorite";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .log()
                .all()
                .when()
                .post(url);
    }

    @DisplayName("Тест добавления альбома")
    @Test
    @Order(6)
    void createAlbumTest() {
        String url = "album";
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .formParam("title", "My first album")
                .formParam("description", "This album is cool")
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .contentType("application/json")
                .when()
                .post(url)
                .prettyPeek()
                .then()
                .statusCode(200);
                //.extract()
                //.response()
                //.jsonPath()
                //.getString("data.deletehash");
    }

    @DisplayName("Тест удаления альбома")
    @Test
    @Order(7)
    void deleteAlbumTest() {
        String albumHash = "7hJbXDT";
        String url = "album/" + albumHash;
        given().when()
                .auth()
                .oauth2(ImgurApiParams.TOKEN)
                .log()
                .all()
                .delete(url)
                .prettyPeek()
                .then()
                .log()
                .all()
                .statusCode(200);
    }


}
