package vklonin.tests;

import com.google.gson.Gson;
import configs.Specs;
import configs.models.LoginResponse;
import configs.models.UserCreate;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static configs.Specs.request;

public class RestAssuredSimpleTests {
    @Test
    void getOneUser(){

        Specs.request
                .when()
                    .get("/users/2")
                .then()
                    .body("data.first_name", is("Janet"));
    }
    @Test
    void negativeGetOneUser(){

        Specs.request
                .when()
                    .get("/users/200")
                .then()
                    .statusCode(404)
                    .assertThat();
    }
    @Test
    void postCreateNewUser(){
        String name = "Spartacus";

        Map userCreate =  new LinkedHashMap();
        userCreate.put("name", name);
        userCreate.put("job", "rebel leader");
        String userCreateJson = new Gson().toJson(userCreate);

        UserCreate response = Specs.request
                .when()
                    .body(userCreateJson)
                    .post("users")
                .then()
                    .statusCode(201)
                    .extract().as(UserCreate.class);

        assertThat(response.getNameCreate()).isEqualTo(name);
    }
    @Test
    void positiveLogin(){
        Map credentials =  new LinkedHashMap();
        credentials.put("email", "eve.holt@reqres.in");
        credentials.put("password", "pistol");
        String credentialsJson = new Gson().toJson(credentials);

        LoginResponse response = Specs.request
                .when()
                    .body(credentialsJson)
                    .post("login")
                .then()
                    .spec(Specs.response200)
                    .extract().as(LoginResponse.class);
        assertThat(response.getTokenToLogin().equals("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void negativeLogin(){
        Specs.request
                .when()
                    .body("")
                    .post("login")
                .then()
                    .spec(Specs.response400)
                    .assertThat();
    }
    @Test
    void findUserGroovy(){
        request
                .when()
                    .get("users?page=2")
                .then()
                    .log().body()
                    .body("data.findAll{it.id > 0}.first_name",hasItem("Lindsay"));
    }
}
