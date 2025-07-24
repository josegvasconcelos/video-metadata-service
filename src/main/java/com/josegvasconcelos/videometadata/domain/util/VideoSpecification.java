package com.josegvasconcelos.videometadata.domain.util;

import com.josegvasconcelos.videometadata.application.web.dto.filter.VideoFilterDTO;
import com.josegvasconcelos.videometadata.domain.entity.Video;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VideoSpecification {
    public static Specification<Video> withFilters(VideoFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(filter.source())) {
                predicates.add(cb.equal(root.get("source"), filter.source().toUpperCase()));
            }

            if (filter.durationInSeconds() != null) {
                predicates.add(cb.equal(
                        root.get("durationInSeconds"),
                        filter.durationInSeconds()
                ));
            }

            if (filter.uploadDate() != null) {
                predicates.add(cb.equal(
                        root.get("uploadDate"),
                        filter.uploadDate()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
