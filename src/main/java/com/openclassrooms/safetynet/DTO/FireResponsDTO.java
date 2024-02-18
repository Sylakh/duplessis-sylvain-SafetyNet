package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record FireResponsDTO(String fireStation, List<FireDTO> persons) {

}
