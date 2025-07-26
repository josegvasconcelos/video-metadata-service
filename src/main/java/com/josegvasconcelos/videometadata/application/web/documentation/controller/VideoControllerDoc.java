package com.josegvasconcelos.videometadata.application.web.documentation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;

@Tag(
        name = "Video Controller",
        description = "Responsible for importing videos and querying video data"
)
@Validated
public interface VideoControllerDoc {

}
