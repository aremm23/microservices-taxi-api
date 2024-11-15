package com.artsem.api.reviewservice.model.dto.response;

import com.artsem.api.reviewservice.model.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewResponseDto {
    private Long id;
    private Long reviewerId;
    private Long reviewedId;
    private String rideId;
    private Integer rate;
    private String comment;
    private ReviewType reviewType;
}