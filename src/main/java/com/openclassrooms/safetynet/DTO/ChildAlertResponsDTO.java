package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record ChildAlertResponsDTO(List<ChildAlertDTO> listChild, List<ChildAlertDTO> listAdult) {

}
