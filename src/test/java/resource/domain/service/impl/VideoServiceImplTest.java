package resource.domain.service.impl;

import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.domain.entity.Video;
import com.josegvasconcelos.videometadata.domain.exception.VideoNotFoundException;
import com.josegvasconcelos.videometadata.domain.repository.VideoRepository;
import com.josegvasconcelos.videometadata.domain.service.impl.VideoServiceImpl;
import com.josegvasconcelos.videometadata.resource.exception.WrongURLFormatException;
import com.josegvasconcelos.videometadata.resource.gateway.VideoImportGateway;
import de.huxhorn.sulky.ulid.ULID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VideoServiceImplTest {

    @Mock
    private VideoImportGateway videoImportGateway;

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoServiceImpl videoService;

    @Test
    void shouldSuccessfullyImportVideoMetadataByUrl() {
        var url = "https://youtube.com/some-video";

        var video = new Video(
                new ULID().nextULID(),
                "Some Video",
                "Description of Some Video",
                "YOUTUBE",
                url,
                123L,
                LocalDate.of(2025, 7, 24));

        when(videoImportGateway.importVideoMetadataByUrl(url)).thenReturn(video);
        when(videoRepository.save(video)).thenReturn(video);

        var resultVideo = videoService.importMetadataByUrl(url);

        // Assert
        assertSame(video, resultVideo);
        verify(videoImportGateway, times(1)).importVideoMetadataByUrl(url);
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    void shouldPropagateExceptionWhenVideoGatewayThrowsException() {
        var badUrl = "not-a-url";

        when(videoImportGateway.importVideoMetadataByUrl(badUrl))
                .thenThrow(new WrongURLFormatException("Wrong url format"));

        assertThrows(
                WrongURLFormatException.class,
                () -> videoService.importMetadataByUrl(badUrl),
                "Expected exception from gateway to propagate"
        );
        verify(videoImportGateway, times(1)).importVideoMetadataByUrl(badUrl);
    }

    @Test
    void shouldFindByIdSuccessfully() {
        var videoId = new ULID().nextULID();

        var video = new Video(
                videoId,
                "Some Title",
                "Description of Some Video",
                "YOUTUBE",
                "https://youtube.com/some-video",
                123L,
                LocalDate.of(2025, 7, 24)
        );
        when(videoRepository.findById(videoId)).thenReturn(Optional.of(video));

        var resultVideo = videoService.findVideoById(videoId);

        assertSame(video, resultVideo);
        verify(videoRepository, times(1)).findById(videoId);
    }

    @Test
    void shouldThrowVideoNotFoundExceptionWhenVideoIdNotFound() {
        var videoId = new ULID().nextULID();

        when(videoRepository.findById(videoId)).thenReturn(Optional.empty());

        assertThrows(
                VideoNotFoundException.class,
                () -> videoService.findVideoById(videoId)
        );
        verify(videoRepository, times(1)).findById(videoId);
    }

    @Test
    void shouldFindAllVideosByPageAndFilters() {
        var filters = new VideoFilterDTO(
                "youtube", null, null
        );
        var pageable = PageRequest.of(0, 5);

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

        when(videoRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(page);

        var pageResult = videoService.findAllVideos(pageable, filters);

        assertSame(page, pageResult);
        assertEquals(videos.size(), pageResult.getTotalElements());
        verify(videoRepository, times(1))
                .findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void shouldReturnAnEmptyPagesWhenThereAreNoVideos() {
        var filters = new VideoFilterDTO(
                "youtube", null, null
        );
        var pageable = PageRequest.of(1, 10);
        var emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(videoRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(emptyPage);

        var pageResult = videoService.findAllVideos(pageable, filters);

        assertTrue(pageResult.isEmpty());
        assertEquals(0, pageResult.getTotalElements());
        verify(videoRepository, times(1))
                .findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void shouldCalculateStatisticsAcrossPagesSuccessfully() {
        var video1 = new Video(
                new ULID().nextULID(),
                "Some Title 1",
                "Description of Some Video 1",
                "YOUTUBE",
                "https://youtube.com/some-video-1",
                10L,
                LocalDate.now()
        );
        var video2 = new Video(
                new ULID().nextULID(),
                "Some Title 2",
                "Description of Some Video 2",
                "VIMEO",
                "https://youtube.com/some-video-2",
                20L,
                LocalDate.now()
        );
        var video3 = new Video(
                new ULID().nextULID(),
                "Some Title 3",
                "Description of Some Video 3",
                "YOUTUBE",
                "https://youtube.com/some-video-3",
                30L,
                LocalDate.now()
        );
        var video4 = new Video(
                new ULID().nextULID(),
                "Some Title 4",
                "Description of Some Video 4",
                "VIMEO",
                "https://youtube.com/some-video-4",
                40L,
                LocalDate.now()
        );

        var page0 = PageRequest.of(0, 500);
        var page1 = PageRequest.of(1, 500);

        var chunk0 = new PageImpl<>(List.of(video1, video2), page0, 1000);
        var chunk1 = new PageImpl<>(List.of(video3, video4), page1, 1000);

        when(videoRepository.findAll(page0)).thenReturn(chunk0);
        when(videoRepository.findAll(page1)).thenReturn(chunk1);

        var stats = videoService.calculateStatistics();

        assertEquals(2, stats.sourcesStatistics().size());

        var youtubeStats = stats.sourcesStatistics()
                .stream()
                .filter(sourceStatistics -> sourceStatistics.source().equals("YOUTUBE"))
                .findFirst()
                .orElse(null);

        assertNotNull(youtubeStats);
        assertEquals(2L, youtubeStats.importedVideos());
        assertEquals(20.0F, youtubeStats.averageDuration());

        var vimeoStats = stats.sourcesStatistics()
                .stream()
                .filter(sourceStatistics -> sourceStatistics.source().equals("VIMEO"))
                .findFirst()
                .orElse(null);

        assertNotNull(vimeoStats);
        assertEquals(2L, vimeoStats.importedVideos());
        assertEquals(30.0F, vimeoStats.averageDuration());

        verify(videoRepository, times(1)).findAll(page0);
        verify(videoRepository, times(1)).findAll(page1);
    }
}
