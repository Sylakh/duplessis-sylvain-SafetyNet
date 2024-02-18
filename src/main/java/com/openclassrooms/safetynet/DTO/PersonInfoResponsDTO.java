package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record PersonInfoResponsDTO(PersonInfoDTO personFound, List<String> similarName) {

}
