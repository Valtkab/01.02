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
                .statusCode(200).extract().response().as(CreatePetRequestModel.class);
        System.out.println();

        Assert.assertEquals(responseModel.getId(), requestModel.getId());
    }

    @Test
    public void createResponseTest(){
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
                .contentType(ContentType.JSON)
                .body (responseModel1)
                .log().all()
                .get("https://petstore.swagger.io/v2/pet/2")
                .then()
                .statusCode(200).extract().response().as(CreatePetResponseModel.class);
        Assert.assertEquals(responseModel2.getId(), responseModel1.getId());
    }
}