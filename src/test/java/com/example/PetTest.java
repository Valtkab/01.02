package com.example;

import com.example.steps.PetSteps;
import com.example.tests.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;

@Epic("PetStore API")
@Feature("Pet API")
public class PetTest extends BaseTest {

    @Test
    @Story("Create Pet")
    @Severity(SeverityLevel.CRITICAL)
    public void createTest() {
        int petId = 5645;
        String petName = "Bod";
        String petStatus = "available";

        new PetSteps()
                .createPet(petId, petName, petStatus)
                .assertThatIdIs(petId)
                .assertThatNameIs(petName)
                .assertThatStatusIs(petStatus);
    }

    @Test
    @Story("Get Pet by ID")
    @Severity(SeverityLevel.NORMAL)
    public void getPetByIdTest() {
        int petId = 3333;
        String petName = "Fog";
        String petStatus = "available";

        new PetSteps()
                .createPet(petId, petName, petStatus)
                .getPetById(petId)
                .assertThatIdIs(petId)
                .assertThatNameIs(petName)
                .assertThatStatusIs(petStatus);
    }

    @Test
    @Story("Update Pet")
    @Severity(SeverityLevel.NORMAL)
    public void updatePetTest() {
        int petId = 9999;
        String petName = "Bod";
        String petStatus = "available";
        String updatedName = "BodUpdated";

        new PetSteps()
                .createPet(petId, petName, petStatus)
                .updatePet(petId, updatedName, petStatus)
                .assertThatNameIs(updatedName);
    }

    @Test
    @Story("Find Pets by Status")
    @Severity(SeverityLevel.MINOR)
    public void findByStatusTest() {
        new PetSteps()
                .findPetsByStatus("available")
                .assertThatResponseHasItems();
    }
}

