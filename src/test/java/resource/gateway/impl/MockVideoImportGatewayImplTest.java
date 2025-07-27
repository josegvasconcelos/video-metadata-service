package resource.gateway.impl;

import com.josegvasconcelos.videometadata.resource.exception.WrongURLFormatException;
import com.josegvasconcelos.videometadata.resource.gateway.impl.MockVideoImportGatewayImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockVideoImportGatewayImplTest {

    private final MockVideoImportGatewayImpl gateway = new MockVideoImportGatewayImpl();

    @Test
    void shouldImportVideoSuccessfully() {
        var url = "https://youtube.com/video-title-here";

        var video = gateway.importVideoMetadataByUrl(url);

        var expectedDescription = "Video about " + video.getTitle() + " lasting " + video.getDurationInSeconds() + " seconds.";

        var duration = video.getDurationInSeconds();

        var uploadDate = video.getUploadDate();
        var year  = uploadDate.getYear();
        var month = uploadDate.getMonthValue();
        var day   = uploadDate.getDayOfMonth();

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
        var malformed = "not-a-valid-url";

        assertThrows(WrongURLFormatException.class,
                () -> gateway.importVideoMetadataByUrl(malformed)
        );
    }
}
