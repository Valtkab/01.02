import com.example.model.pet.CategoryModel;
import com.example.model.pet.CreatePetRequestModel;
import com.example.model.pet.CreatePetResponseModel;
import com.example.model.pet.TagModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PetTest {

    @Test
    public void createTest() {
        CreatePetRequestModel requestModel = new CreatePetRequestModel();
        requestModel.setId(5645);
        requestModel.setName("Bod");
        requestModel.setStatus("available");
        requestModel.setPhotoUrls(List.of("adsadasdas"));

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1);
        categoryModel.setName("dog");
        requestModel.setCategory(categoryModel);

        TagModel tagModel = new TagModel();
        tagModel.setId(1);
        tagModel.setName("corgi");
        requestModel.setTags(List.of(tagModel));

        CreatePetRequestModel responseModel = given()
                .when()
                .contentType(ContentType.JSON)
                .body(requestModel)
                .log().all()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(CreatePetRequestModel.class);

        Assert.assertEquals(responseModel.getId(), requestModel.getId());
    }

    @Test
    public void createResponseTest() {
        CreatePetResponseModel responseModel1 = new CreatePetResponseModel();
        responseModel1.setId(2);
        responseModel1.setName("fog");
        responseModel1.setStatus("available");
        responseModel1.setPhotoUrls(List.of("asdsadad"));

        CategoryModel ctgModel = new CategoryModel();
        ctgModel.setId(1);
        ctgModel.setName("dog");
        responseModel1.setCategory(ctgModel);

        TagModel tgModel = new TagModel();
        tgModel.setId(2);
        tgModel.setName("corgi");
        responseModel1.setTags(List.of(tgModel));

        CreatePetResponseModel responseModel2 = given()
                .when()
                .log().all()
                .get("https://petstore.swagger.io/v2/pet/2")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(CreatePetResponseModel.class);

        Assert.assertEquals(responseModel2.getId(), responseModel1.getId());
    }

    @Test
    public void updatePetTest() {
        CreatePetRequestModel updateModel = new CreatePetRequestModel();
        updateModel.setId(5645);
        updateModel.setName("BodUpdated");
        updateModel.setStatus("available");
        updateModel.setPhotoUrls(List.of("new-photo"));

        CategoryModel updatedCategory = new CategoryModel();
        updatedCategory.setId(10);
        updatedCategory.setName("wolf");
        updateModel.setCategory(updatedCategory);

        TagModel updatedTag = new TagModel();
        updatedTag.setId(10);
        updatedTag.setName("husky");
        updateModel.setTags(List.of(updatedTag));

        CreatePetResponseModel responseModel = given()
                .when()
                .contentType(ContentType.JSON)
                .body(updateModel)
                .log().all()
                .put("https://petstore.swagger.io/v2/pet")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(CreatePetResponseModel.class);

        Assert.assertEquals(responseModel.getName(), updateModel.getName());
    }

    @Test
    public void findByStatusTest() {
        CreatePetResponseModel[] response = given()
                .when()
                .log().all()
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(CreatePetResponseModel[].class);

        Assert.assertTrue(response.length > 0);
    }

    @Test
    public void findByTagsTest() {
        CreatePetResponseModel[] response = given()
                .when()
                .log().all()
                .get("https://petstore.swagger.io/v2/pet/findByTags?tags=corgi")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(CreatePetResponseModel[].class);

        Assert.assertTrue(response.length > 0);
    }

    @Test
    public void updatePetViaFormTest() {

        Response response = given()
                .when()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "FormUpdated")
                .formParam("status", "sold")
                .log().all()
                .post("https://petstore.swagger.io/v2/pet/5645")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        Assert.assertTrue(response.asString().contains("5645"));
    }
}
