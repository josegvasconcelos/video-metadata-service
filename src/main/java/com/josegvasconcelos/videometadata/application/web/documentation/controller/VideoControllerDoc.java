package com.josegvasconcelos.videometadata.application.web.documentation.controller;

import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.application.web.dto.request.ImportVideoRequestDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.ErrorResponseDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.StatisticsResponseDTO;
import com.josegvasconcelos.videometadata.application.web.dto.response.VideoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(
        name = "Video Controller",
        description = "Responsible for importing videos and querying video data"
)
@Validated
public interface VideoControllerDoc {
    @Operation(
            summary = "Import video by URL",
            description = "Import a video by the URL provided in the request body",
            security    = {
                    @SecurityRequirement(name = "bearerAuth", scopes = { "ADMIN" })
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Video imported successfully",
                            content = @Content(
                                    schema = @Schema(implementation = VideoResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "URL out of format",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad Request",
                                                    value = """
                                                            {
                                                                "status": "BAD_REQUEST",
                                                                "message": "URL is out of format https://source.com/video-title",
                                                                "url": "/videos/import"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Forbidden",
                                                    value = """
                                                            {
                                                                "status": "FORBIDDEN",
                                                                "message": "Access Denied",
                                                                "url": "/videos/import"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    )
            }
    )
    ResponseEntity<VideoResponseDTO> importVideoMetadataByUrl(
            @Valid ImportVideoRequestDTO importVideoRequest
    );

    @Operation(
            summary = "Get video information by ID",
            description = "Get video information by it's unique ID (ULID)",
            security    = {
                    @SecurityRequirement(name = "bearerAuth", scopes = { "USER" })
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Video retrieved successfully",
                            content = @Content(
                                    schema = @Schema(implementation = VideoResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "ID out of format",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad Request",
                                                    value = """
                                                            {
                                                                "status": "BAD_REQUEST",
                                                                "message": "getVideoById.id: Path variable 'id' must be a valid 26-char ULID",
                                                                "url": "/videos/mismatching-format-id"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Forbidden",
                                                    value = """
                                                            {
                                                                "status": "FORBIDDEN",
                                                                "message": "Access Denied",
                                                                "url": "/videos/{id}"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Video not found",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Not Found",
                                                    value = """
                                                            {
                                                                "status": "NOT_FOUND",
                                                                "message": "Video with id 01K0YPCKP7QNXV8B6CYAXCC58F not found",
                                                                "url": "/videos/01K0YPCKP7QNXV8B6CYAXCC58F"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
            }
    )
    ResponseEntity<VideoResponseDTO> getVideoById(
            @PathVariable
            @Pattern(
                    regexp  = "^[0-7][0-9A-HJKMNP-TV-Z]{25}$",
                    message = "Path variable 'id' must be a valid 26-char ULID"
            )
            String id
    );

    @Operation(
            summary = "Get videos information",
            description = "Get videos information with filtering, sorting and pagination",
            security    = {
                    @SecurityRequirement(name = "bearerAuth", scopes = { "USER" })
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Videos retrieved successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            arraySchema = @Schema(implementation = VideoResponseDTO.class)
                                    ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Forbidden",
                                                    value = """
                                                            {
                                                                "status": "FORBIDDEN",
                                                                "message": "Access Denied",
                                                                "url": "/videos"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
            }
    )
    ResponseEntity<List<VideoResponseDTO>> getAllVideos(
            Pageable pageable,
            VideoFilterDTO filters
    );

    @Operation(
            summary = "Get videos statistics",
            description = "Get videos statistics grouped by source",
            security    = {
                    @SecurityRequirement(name = "bearerAuth", scopes = { "USER" })
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Videos statistics retrieved successfully",
                            content = @Content(
                                    schema = @Schema(implementation = VideoResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = {
                                            @ExampleObject(
                                                    name = "Forbidden",
                                                    value = """
                                                            {
                                                                "status": "FORBIDDEN",
                                                                "message": "Access Denied",
                                                                "url": "/videos/stats"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
            }
    )
    ResponseEntity<StatisticsResponseDTO> getStatistics();
}
