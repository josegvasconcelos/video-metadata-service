package resource.gateway.impl;

import com.josegvasconcelos.videometadata.domain.entity.Video;
import com.josegvasconcelos.videometadata.resource.exception.WrongURLFormatException;
import com.josegvasconcelos.videometadata.resource.gateway.impl.MockVideoImportGatewayImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockVideoImportGatewayImplTest {

    private final MockVideoImportGatewayImpl gateway = new MockVideoImportGatewayImpl();;

    @Test
    void shouldImportVideoSuccessfully() {
        String url = "https://youtube.com/video-title-here";

        Video video = gateway.importVideoMetadataByUrl(url);

        String expectedDescription = "Video about " + video.getTitle() + " lasting " + video.getDurationInSeconds() + " seconds.";

        Long duration = video.getDurationInSeconds();

        LocalDate uploadDate = video.getUploadDate();
        int year  = uploadDate.getYear();
        int month = uploadDate.getMonthValue();
        int day   = uploadDate.getDayOfMonth();

        assertNotNull(video);
        assertEquals("YOUTUBE", video.getSource());
        assertEquals("Video Title Here", video.getTitle());
        assertEquals(url, video.getUrl());
        assertEquals(expectedDescription, video.getDescription());
        assertTrue(duration >= 1 && duration <= 1000);
        assertTrue(year  >= 2000 && year  <= 2024);
        assertTrue(month <= 11);
        assertTrue(day   <= 28);
    }

    @Test
    void shouldThrowWrongURLFormatExceptionWhenImportingWithMalformedUrl() {
        String malformed = "not-a-valid-url";

        assertThrows(WrongURLFormatException.class,
                () -> gateway.importVideoMetadataByUrl(malformed)
        );
    }
}
