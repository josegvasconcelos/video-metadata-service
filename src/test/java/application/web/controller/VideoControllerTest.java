package application.web.controller;

import com.josegvasconcelos.videometadata.application.web.controller.VideoController;
import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.application.web.dto.request.ImportVideoRequestDTO;
import com.josegvasconcelos.videometadata.domain.entity.Video;
import com.josegvasconcelos.videometadata.domain.exception.VideoNotFoundException;
import com.josegvasconcelos.videometadata.domain.model.SourceStatistics;
import com.josegvasconcelos.videometadata.domain.model.Statistics;
import com.josegvasconcelos.videometadata.domain.service.VideoService;
import com.josegvasconcelos.videometadata.resource.exception.WrongURLFormatException;
import de.huxhorn.sulky.ulid.ULID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VideoControllerTest {

    @Mock
    private VideoService videoService;

    @InjectMocks
    private VideoController controller;

    @Test
    void shouldImportVideoSuccessfully() {
        var url = "https://youtube.com/some-video";

        var request = new ImportVideoRequestDTO(url);

        var video = new Video(
                new ULID().nextULID(),
                "Some Video",
                "Description of Some Video",
                "YOUTUBE",
                url,
                123L,
                LocalDate.of(2025, 7, 24));

        when(videoService.importMetadataByUrl(url)).thenReturn(video);

        var response = controller.importVideoMetadataByUrl(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        var responseBody = response.getBody();

        assertEquals(video.getId(), responseBody.id());
        assertEquals(video.getTitle(), responseBody.title());
        assertEquals(video.getDescription(), responseBody.description());
        assertEquals(video.getSource(), responseBody.source());
        assertEquals(video.getUrl(), responseBody.url());
        assertEquals(video.getDurationInSeconds(), responseBody.durationInSeconds());
        assertEquals(video.getUploadDate(), responseBody.uploadDate());

        verify(videoService, times(1)).importMetadataByUrl(url);
    }

    @Test
    void shouldPropagateExceptionWhenFailsToImportVideo() {
        var badUrl = "invalid-url";

        var request = new ImportVideoRequestDTO(badUrl);

        when(videoService.importMetadataByUrl(badUrl))
                .thenThrow(new WrongURLFormatException("Wrong url format"));

        assertThrows(
                WrongURLFormatException.class,
                () -> controller.importVideoMetadataByUrl(request)
        );
        verify(videoService, times(1)).importMetadataByUrl(badUrl);
    }

    @Test
    void shouldGetVideoByIdSuccessfully() {
        var videoId = new ULID().nextULID();

        var video = new Video(
                videoId,
                "Some Video",
                "Description of Some Video",
                "YOUTUBE",
                "https://youtube.com/some-video",
                123L,
                LocalDate.of(2025, 7, 24));

        when(videoService.findVideoById(videoId)).thenReturn(video);

        var response = controller.getVideoById(videoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var responseBody = response.getBody();

        assertEquals(video.getId(), responseBody.id());
        assertEquals(video.getTitle(), responseBody.title());
        assertEquals(video.getDescription(), responseBody.description());
        assertEquals(video.getSource(), responseBody.source());
        assertEquals(video.getUrl(), responseBody.url());
        assertEquals(video.getDurationInSeconds(), responseBody.durationInSeconds());
        assertEquals(video.getUploadDate(), responseBody.uploadDate());

        verify(videoService, times(1)).findVideoById(videoId);
    }

    @Test
    void shouldPropagateVideoNotFoundExceptionWhenFailsToGetVideoById() {
        var videoId = "nonexistent";
        when(videoService.findVideoById(videoId))
                .thenThrow(new VideoNotFoundException("Video with id " + videoId + " not found"));

        assertThrows(
                VideoNotFoundException.class,
                () -> controller.getVideoById(videoId)
        );

        verify(videoService, times(1)).findVideoById(videoId);
    }

    @Test
    void shouldGetVideosWithPaginationAndFilteringSuccessfully() {
        var filters = new VideoFilterDTO(
                "YOUTUBE",
                null,
                null
        );
        var pageable = PageRequest.of(0, 20, Sort.by("id").ascending());

        var videos = List.of(
                new Video(
                        new ULID().nextULID(),
                        "Some Title 1",
                        "Description of Some Video 1",
                        "YOUTUBE",
                        "https://youtube.com/some-video-1",
                        120L,
                        LocalDate.now()
                ),
                new Video(
                        new ULID().nextULID(),
                        "Some Title 2",
                        "Description of Some Video 2",
                        "YOUTUBE",
                        "https://youtube.com/some-video-2",
                        240L,
                        LocalDate.now()
                )
        );

        var page = new PageImpl<>(videos, pageable, 2);

        when(videoService.findAllVideos(pageable, filters)).thenReturn(page);

        var response = controller.getAllVideos(pageable, filters);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var responseBody = response.getBody();

        assertEquals(2, responseBody.size());
        assertEquals("Some Title 1", responseBody.get(0).title());
        assertEquals("Some Title 2", responseBody.get(1).title());

        var pgCaptor = ArgumentCaptor.forClass(Pageable.class);
        var flCaptor = ArgumentCaptor.forClass(VideoFilterDTO.class);

        verify(videoService, times(1)).findAllVideos(pgCaptor.capture(), flCaptor.capture());
        assertEquals(pageable, pgCaptor.getValue());
        assertEquals(filters, flCaptor.getValue());
    }

    @Test
    void shouldReturnAnEmptyListWhenPageAndFiltersHaveNoVideos() {
        var filters = new VideoFilterDTO(
                "YOUTUBE",
                null,
                null
        );
        var pageable = PageRequest.of(0, 20, Sort.by("id").ascending());
        var emptyPage = new PageImpl<Video>(List.of(), pageable, 0);

        when(videoService.findAllVideos(pageable, filters)).thenReturn(emptyPage);

        var response = controller.getAllVideos(pageable, filters);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var responseBody = response.getBody();

        assertTrue(responseBody.isEmpty());
        verify(videoService, times(1)).findAllVideos(pageable, filters);
    }

    @Test
    void shouldGetStatisticsForAllSourcesSuccessfully() {
        var youtubeStatistics = new SourceStatistics("YOUTUBE", 2L, 15.0);
        var vimeoStatistics = new SourceStatistics("VIMEO",   1L, 30.0);

        var statistics = new Statistics(List.of(youtubeStatistics, vimeoStatistics));

        when(videoService.calculateStatistics()).thenReturn(statistics);

        var response = controller.getStatistics();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var responseBody = response.getBody();

        var responseSourceStatistics = responseBody.sourcesStatistics();
        assertEquals(2, responseSourceStatistics.size());

        var youtubeResponseSourceStatistics = responseSourceStatistics.get(0);
        assertEquals("YOUTUBE", youtubeResponseSourceStatistics.source());
        assertEquals(2L,        youtubeResponseSourceStatistics.importedVideos());
        assertEquals(15.0,      youtubeResponseSourceStatistics.averageDuration());

        var vimeoResponseSourceStatistics = responseSourceStatistics.get(1);
        assertEquals("VIMEO", vimeoResponseSourceStatistics.source());
        assertEquals(1L,      vimeoResponseSourceStatistics.importedVideos());
        assertEquals(30.0,    vimeoResponseSourceStatistics.averageDuration());

        verify(videoService, times(1)).calculateStatistics();
    }
}
