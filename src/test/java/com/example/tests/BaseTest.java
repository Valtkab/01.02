package com.example.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Чтобы RestAssured логи попадали в Allure
        RestAssured.filters(new AllureRestAssured());
    }
}
