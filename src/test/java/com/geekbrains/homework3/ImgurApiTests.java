package com.geekbrains.homework3;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import java.sql.Statement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImgurApiTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = ImgurApiParameters.API_URL + "/" + ImgurApiParameters.API_VERSION;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @DisplayName("Тест на получение информации о картинке")
    @Test
    @Order(1)
    void testImage() {
        // https://api.imgur.com/3/image/{{imageHash}}
        String imageHash = "0OwU69P";
        String url = "image/" + imageHash;

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .expect()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data.id", is(imageHash))
                .when()
                .get(url);
    }

    @DisplayName("Тест загрузки изображения по ссылке на изображение")
    @Test
    @Order(2)
    void testPostImageFromUrl() {
        // https://api.imgur.com/3/upload
        String url = "upload";
        String albumHash = "yePa2eE";
        String imgUrl = "https://pbs.twimg.com/media/FDskSI9aQAAKlnf?format=jpg";
        String imgTitle = "The discomfort";
        String imgDescription = "cat and cactus";
        String imgName = "FDskSI9aQAAKlnf.jpg";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .formParam("image", imgUrl)
                .formParam("album", albumHash)
                .formParam("type", "url")
                .formParam("name", imgName)
                .formParam("title", imgTitle)
                .formParam("description", imgDescription)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data.name", is(imgName))
                .body("data.title", is(imgTitle))
                .body("data.description", is(imgDescription))
                .when()
                .post(url);

    }

    @DisplayName("Тест загрузки гифки по ссылке на гифку")
    @Test
    @Order(3)
    void testPostImageGifFromUrl() {
        // https://api.imgur.com/3/upload
        String url = "upload";
        String albumHash = "yePa2eE";
        String gifUrl = "https://c.tenor.com/4fSoa79Rx0YAAAAC/because-were-smart-were-smart.gif";
        String gifTitle = "Because";
        String gifDescription = "we are smart";
        String gifName = "because-were-smart-were-smart.gif";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .log().all()
                .formParam("image", gifUrl)
                .formParam("album", albumHash)
                .formParam("type", "url")
                .formParam("name", gifName)
                .formParam("title", gifTitle)
                .formParam("description", gifDescription)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data.name", is(gifName))
                .body("data.title", is(gifTitle))
                .body("data.description", is(gifDescription))
                .when()
                .post(url);
    }

    @DisplayName("Тест загрузки видео по ссылке на видео")
    @Test
    @Order(4)
    void testPostImageVideoFromUrl() {
        // https://api.imgur.com/3/upload
        String url = "upload";
        String albumHash = "yePa2eE";
        String videoUrl = ("https://cdn.discordapp.com/attachments/395197338060193794/891364336491167774/1632425186_looped_1632425183.mp4");
        String videoTitle = "Rick Astley";
        String videoDescription = "Never Gonna Give You Up";
        String videoName = "1632425186_looped_1632425183.mp4";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .log().all()
                .formParam("image", videoUrl)
                .formParam("album", albumHash)
                .formParam("type", "url")
                .formParam("name", videoName)
                .formParam("title", videoTitle)
                .formParam("description", videoDescription)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data.name", is(videoName))
                .body("data.title", is(videoTitle))
                .body("data.description", is(videoDescription))
                .when()
                .post(url);
    }

    @DisplayName("Тест обновления названия и описания изображения с авторизацией по Client-ID")
    @Test
    @Order(5)
    void testPostUpdateImageInformationUnAuthed() {
        // https://api.imgur.com/3/image/{{imageDeleteHash}}
        String albumHash = "yePa2eE";
        String urlForDeleteHash = "album/" + albumHash + "/images";
        String imageDeleteHash = given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .when()
                .get(urlForDeleteHash)
                .then().statusCode(200)
                .extract()
                .path("data[2].deletehash");
        String url = "image/" + imageDeleteHash;
        String imgTitle = "Cat";
        String imgDescription = "Cactus and cat";

        given().when()
                .log().all()
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .formParam("title", imgTitle)
                .formParam("description", imgDescription)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .post(url);
    }

    @DisplayName("Тест обновления названия и описания изображения с авторизацией с помощью accessToken")
    @Test
    @Order(6)
    void testUpdateImageInformationAuthed() {
        // https://api.imgur.com/3/image/{{imageHash}}
        String albumHash = "yePa2eE";
        String urlForImageHash = "album/" + albumHash + "/images";
        String imageHash = given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .when()
                .get(urlForImageHash)
                .then().statusCode(200)
                .extract()
                .path("data[2].id");
        String url = "image/" + imageHash;
        String imgTitle = "The discomfort";
        String imgDescription = "Cat and cactus";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .formParam("title", imgTitle)
                .formParam("description", imgDescription)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .post(url);
    }

    @DisplayName("Тест добавления изображения в избранное")
    @Test
    @Order(7)
    void testFavoriteAnImage() {
        // https://api.imgur.com/3/image/{{imageHash}}/favorite
        String albumHash = "yePa2eE";
        String urlForImageHash = "album/" + albumHash + "/images";
        String imageHash = given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .when()
                .get(urlForImageHash)
                .then().statusCode(200)
                .extract()
                .path("data[2].id");
        String url = "image/" + imageHash + "/favorite";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is("favorited"))
                .when()
                .post(url);
    }

    @DisplayName("Тест удаления изображения из избранного")
    @Test
    @Order(8)
    void testUnfavoriteAnImage() {
        // https://api.imgur.com/3/image/{{imageHash}}/favorite
        String albumHash = "yePa2eE";
        String urlForImageHash = "album/" + albumHash + "/images";
        String imageHash = given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .when()
                .get(urlForImageHash)
                .then().statusCode(200)
                .extract()
                .path("data[2].id");
        String url = "image/" + imageHash + "/favorite";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is("unfavorited"))
                .when()
                .post(url);
    }

    @DisplayName("Тест удаления изображения с авторизацией по Client-ID")
    @Test
    @Order(9)
    void testImageDeletionUnAuthed() {
        //https://api.imgur.com/3/image/{{imageDeleteHash}}
        String albumHash = "yePa2eE";
        String urlForDeleteHash = "album/" + albumHash + "/images";
        String imageDeleteHash = given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .when()
                .get(urlForDeleteHash)
                .then().statusCode(200)
                .extract()
                .path("data[2].deletehash");
        String url = "image/" + imageDeleteHash;

        given().when()
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .delete(url);
    }

    @DisplayName("Тест удаления гифки с авторизацией по accessToken")
    @Test
    @Order(10)
    void testImageDeletionAuthed() {
        // https://api.imgur.com/3/image/{{imageHash}}
        String albumHash = "yePa2eE";
        String urlForImageHash = "album/" + albumHash + "/images";
        String imageHash = given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .when()
                .get(urlForImageHash)
                .then().statusCode(200)
                .extract()
                .path("data[2].id");
        String url = "image/" + imageHash;

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .delete(url);
    }

    @DisplayName("Тест удаления видео с авторизацией по accessToken и с Client-ID")
    @Test
    @Order(11)
    void testImageVideoDeletionAuthed() {
        // https://api.imgur.com/3/image/{{imageHash}}
        String albumHash = "yePa2eE";
        String urlForImageHash = "album/" + albumHash + "/images";
        String imageHash = given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .when()
                .get(urlForImageHash)
                .then().statusCode(200)
                .extract()
                .path("data[2].id");
        String url = "image/" + imageHash;

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is(true))
                .when()
                .delete(url);
    }

    @DisplayName("Тест получения информации об альбоме")
    @Test
    @Order(12)
    void testGetAdditionalInformationAboutAnAlbum() {
        //https://api.imgur.com/3/album/{{albumHash}}
        String albumHash = "eTsBWH4";
        String url = "album/" + albumHash;

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data.account_url", is(ImgurApiParameters.USERNAME))
                .when()
                .get(url);
    }

    @DisplayName("Тест получения списка изображений в альбоме")
    @Test
    @Order(13)
    void testGetAlbumImages() {
        //https://api.imgur.com/3/album/{{albumHash}}/images
        String albumHash = "eTsBWH4";
        String url = "album/" + albumHash + "/images";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .get(url);
    }

    @DisplayName("Тест получения информации об изображении из альбома")
    @Test
    @Order(14)
    void testGetAlbumImage() {
        //https://api.imgur.com/3/album/{{albumHash}}/image/{{imageHash}}
        String albumHash = "eTsBWH4";
        String imageHash = "VnAHbx3";
        String url = "album/" + albumHash + "/image/" + imageHash;

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data.id", is(imageHash))
                .when()
                .get(url);
    }

    @DisplayName("Тест обновления информации об альбоме")
    @Test
    @Order(14)
    void testPutUpdateAlbum() {
        //https://api.imgur.com/3/album/{{albumHash}}
        String albumHash = "NAtkELv";
        String albumTitle = "Новый заголовок";
        String albumDescription = "Новое описание";
        String albumPrivacy = "hidden";
        String albumLayout = "grid";
        String url = "album/" + albumHash;

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .log().all()
                .formParam("title", albumTitle)
                .formParam("description", albumDescription)
                .formParam("privacy", albumPrivacy)
                .formParam("layout", albumLayout)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("data", is(true))
                .body("success", is(true))
                .when()
                .put(url);
    }

    @DisplayName("Тест помещения альбома в избранное")
    @Test
    @Order(15)
    void testFavoriteAlbum() {
        //https://api.imgur.com/3/album/{{albumHash}}/favorite
        String albumHash = "eTsBWH4";
        String url = "album/" + albumHash + "/favorite";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is("favorited"))
                .when()
                .post(url);
    }

    @DisplayName("Тест удаления альбома из избранного")
    @Test
    @Order(16)
    void testUnfavoriteAlbum() {
        //https://api.imgur.com/3/album/{{albumHash}}/favorite
        String albumHash = "eTsBWH4";
        String url = "album/" + albumHash + "/favorite";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .body("data", is("unfavorited"))
                .when()
                .post(url);
    }

    @DisplayName("Тест добавления изображений в альбом с Client-ID по deletehashes изображений")
    @Test
    @Order(17)
    void testAddImagesToAnAlbumUnAuthed() {
        //https://api.imgur.com/3/album/{{albumDeleteHash}}/add
        //без токена выдаёт ошибку "You must own all the image deletehashes to add them to album"
        String albumDeleteHash = "nLti4BePrtSmApw";
        String imageDeleteHash = "Eti4Dq60h9lZ7Am";
        String imageDeleteHash2 = "cAzjAoNtNDA3mzG";
        String url = "album/" + albumDeleteHash + "/add";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .header("Authorization", ImgurApiParameters.CLIENT_ID)
                .log().all()
                .formParam("deletehashes[]", imageDeleteHash)
                .formParam("deletehashes[]", imageDeleteHash2)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .post(url);
    }

    @DisplayName("Тест добавления изображений в альбом по id изображений")
    @Test
    @Order(18)
    void testAddImagesToAnAlbumAuthed() {
        // https://api.imgur.com/3/album/{{albumHash}}/add
        String albumHash = "NAtkELv";
        String imageHash = "pZBZ7sq";
        String imageHash2 = "VNfbFIV";
        String url = "album/" + albumHash + "/add";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .log().all()
                .formParam("ids[]", imageHash)
                .formParam("ids[]", imageHash2)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .post(url);
    }

    @DisplayName("Тест удаления изображений из альбома с авторизацией")
    @Test
    @Order(19)
    void testRemoveImagesFromAnAlbumAuthed() {
        //https://api.imgur.com/3/album/{{albumHash}}/remove_images
        String albumHash = "NAtkELv";
        String imageHash = "pZBZ7sq";
        String imageHash2 = "VNfbFIV";
        String url = "album/" + albumHash + "/remove_images";

        given().when()
                .auth()
                .oauth2(ImgurApiParameters.TOKEN)
                .log().all()
                .formParam("ids[]", imageHash)
                .formParam("ids[]", imageHash2)
                .expect()
                .log().all()
                .statusCode(is(200))
                .body("success", is(true))
                .when()
                .post(url);
    }
}
