package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record PersonInfoResponsDTO(CommonDTO personFound, List<String> similarName) {

}
