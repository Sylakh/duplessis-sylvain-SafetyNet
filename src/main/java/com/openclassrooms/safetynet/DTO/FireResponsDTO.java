package com.openclassrooms.safetynet.DTO;

import java.util.List;

public record FireResponsDTO(String fire_station, List<FireDTO> liste_des_personnes) {

}
