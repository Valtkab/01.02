package com.example.steps;

import com.example.model.pet.CategoryModel;
import com.example.model.pet.CreatePetRequestModel;
import com.example.model.pet.CreatePetResponseModel;
import com.example.model.pet.TagModel;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class PetSteps {

    private Response response;

    @Step("Создание питомца с ID {id}, именем {name}, статусом {status}")
    public PetSteps createPet(int id, String name, String status) {
        CreatePetRequestModel requestModel = new CreatePetRequestModel();
        requestModel.setId(id);
        requestModel.setName(name);
        requestModel.setStatus(status);
        requestModel.setPhotoUrls(List.of("adsadasdas"));

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1);
        categoryModel.setName("dog");
        requestModel.setCategory(categoryModel);

        TagModel tagModel = new TagModel();
        tagModel.setId(1);
        tagModel.setName("corgi");
        requestModel.setTags(List.of(tagModel));

        response = given()
                .contentType(ContentType.JSON)
                .body(requestModel)
                .log().all()
                .post("/pet");

        response.then().statusCode(200);
        return this;
    }

    @Step("Проверка ID в ответе равен {expectedId}")
    public PetSteps assertThatIdIs(int expectedId) {
        CreatePetResponseModel responseModel = response.as(CreatePetResponseModel.class);
        assertEquals(responseModel.getId(), expectedId, "ID питомца в ответе не совпадает с ожидаемым");
        return this;
    }

    @Step("Проверка имени в ответе равен {expectedName}")
    public PetSteps assertThatNameIs(String expectedName) {
        CreatePetResponseModel responseModel = response.as(CreatePetResponseModel.class);
        assertEquals(responseModel.getName(), expectedName, "Имя питомца в ответе не совпадает с ожидаемым");
        return this;
    }

    @Step("Проверка статуса в ответе равен {expectedStatus}")
    public PetSteps assertThatStatusIs(String expectedStatus) {
        CreatePetResponseModel responseModel = response.as(CreatePetResponseModel.class);
        assertEquals(responseModel.getStatus(), expectedStatus, "Статус питомца в ответе не совпадает с ожидаемым");
        return this;
    }

    @Step("Получение питомца по ID {petId}")
    public PetSteps getPetById(int petId) {
        response = given()
                .log().all()
                .get("/pet/" + petId);

        response.then().statusCode(200);
        return this;
    }

    @Step("Обновление питомца с ID {id}, именем {name}, статусом {status}")
    public PetSteps updatePet(int id, String name, String status) {
        CreatePetRequestModel requestModel = new CreatePetRequestModel();
        requestModel.setId(id);
        requestModel.setName(name);
        requestModel.setStatus(status);
        requestModel.setPhotoUrls(List.of("new-photo"));

        CategoryModel updatedCategory = new CategoryModel();
        updatedCategory.setId(10);
        updatedCategory.setName("wolf");
        requestModel.setCategory(updatedCategory);

        TagModel updatedTag = new TagModel();
        updatedTag.setId(10);
        updatedTag.setName("husky");
        requestModel.setTags(List.of(updatedTag));

        response = given()
                .contentType(ContentType.JSON)
                .body(requestModel)
                .log().all()
                .put("/pet");

        response.then().statusCode(200);
        return this;
    }

    // 🔥 Вот этот метод ты просил добавить!
    @Step("Поиск питомцев по статусу {status}")
    public PetSteps findPetsByStatus(String status) {
        response = given()
                .log().all()
                .get("/pet/findByStatus?status=" + status);

        response.then().statusCode(200);
        return this;
    }

    @Step("Проверка, что в ответе есть хотя бы один питомец")
    public PetSteps assertThatResponseHasItems() {
        CreatePetResponseModel[] responseArray = response.as(CreatePetResponseModel[].class);
        assertEquals(responseArray.length > 0, true, "В ответе нет питомцев");
        return this;
    }
}
