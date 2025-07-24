package com.josegvasconcelos.videometadata.resource.gateway.impl;

import com.josegvasconcelos.videometadata.domain.entity.Video;
import com.josegvasconcelos.videometadata.resource.exception.WrongURLFormatException;
import com.josegvasconcelos.videometadata.resource.gateway.VideoImportGateway;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MockVideoImportGatewayImpl implements VideoImportGateway {

    private final Pattern URL_PATTERN = Pattern.compile("^https?://([^.]+)\\.com/([^/?#]+)");

    @Override
    public Video importVideoMetadataByUrl(String url) {
        var matcher = URL_PATTERN.matcher(url);

        if (matcher.find()) {
            var source = matcher.group(1).toUpperCase();
            var title = kebabToTitle(matcher.group(2));
            var durationInSeconds = ThreadLocalRandom.current().nextLong(1, 1001);

            var description = "Video about " + title + " lasting " + durationInSeconds + " seconds.";

            return new Video(
                    null,
                    title,
                    description,
                    source,
                    url,
                    durationInSeconds
            );
        } else {
            throw new WrongURLFormatException("Wrong url format");
        }
    }

    private String kebabToTitle(String kebab) {
        return Arrays.stream(kebab.split("-"))
                .filter(w -> !w.isEmpty())
                .map(w -> w.substring(0,1).toUpperCase() +
                        w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
