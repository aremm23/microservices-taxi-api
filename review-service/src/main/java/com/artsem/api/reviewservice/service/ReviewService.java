package com.artsem.api.reviewservice.service;

import com.artsem.api.reviewservice.filter.ReviewFilter;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import com.artsem.api.reviewservice.model.dto.response.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    Page<ReviewResponseDto> getList(ReviewFilter filter, Pageable pageable);

    ReviewResponseDto getById(Long id);

    ReviewResponseDto create(ReviewRequestDto review);

    void delete(Long id);

    void deleteMany(List<Long> ids);

}
