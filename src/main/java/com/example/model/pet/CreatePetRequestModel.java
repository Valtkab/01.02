package com.example.model.pet;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter

public class CreatePetRequestModel {

        private int id;
        private CategoryModel category;
        private String name;
        private List<String> photoUrls;
        private List<TagModel> tags;
        private String status;


}
