package com.josegvasconcelos.videometadata.application.web.dto.response;

import com.josegvasconcelos.videometadata.application.web.documentation.dto.response.LoginResponseDoc;

public record LoginResponseDTO(String token) implements LoginResponseDoc {
}
