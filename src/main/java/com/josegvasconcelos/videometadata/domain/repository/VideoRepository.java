package com.josegvasconcelos.videometadata.domain.repository;

import com.josegvasconcelos.videometadata.domain.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, String>, JpaSpecificationExecutor<Video> {
}
