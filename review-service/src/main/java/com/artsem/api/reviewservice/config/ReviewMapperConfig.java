package com.artsem.api.reviewservice.config;

import com.artsem.api.reviewservice.model.Review;
import com.artsem.api.reviewservice.model.dto.request.ReviewRequestDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReviewMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        configureModelMapper(modelMapper);
        return modelMapper;
    }

    private void configureModelMapper(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        setupReviewRequestDtoMapping(modelMapper);
    }

    private void setupReviewRequestDtoMapping(ModelMapper modelMapper) {
        modelMapper.typeMap(ReviewRequestDto.class, Review.class)
                .addMappings(mapper -> {
                    mapper.skip(Review::setId);
                    mapper.map(ReviewRequestDto::getReviewedId, Review::setReviewedId);
                    mapper.map(ReviewRequestDto::getReviewerId, Review::setReviewerId);
                    mapper.map(ReviewRequestDto::getRideId, Review::setRideId);
                });
    }
}
