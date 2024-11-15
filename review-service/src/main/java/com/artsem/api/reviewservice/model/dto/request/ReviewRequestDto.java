package com.artsem.api.reviewservice.model.dto.request;

import com.artsem.api.reviewservice.model.ReviewType;
import com.artsem.api.reviewservice.util.ValidationKeys;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewRequestDto {
    @NotNull(message = ValidationKeys.REQUIRED_REVIEWER_ID)
    private Long reviewerId;

    @NotNull(message = ValidationKeys.REQUIRED_REVIEWED_ID)
    private Long reviewedId;

    @NotBlank(message = ValidationKeys.REQUIRED_RIDE_ID)
    private String rideId;

    @NotNull(message = ValidationKeys.REQUIRED_RATE)
    @Min(value = 1, message = ValidationKeys.INVALID_RATE)
    @Max(value = 5, message = ValidationKeys.INVALID_RATE)
    private Integer rate;

    @Size(max = 500, message = ValidationKeys.COMMENT_TOO_LONG)
    private String comment;

    @NotNull(message = ValidationKeys.REQUIRED_REVIEW_TYPE)
    private ReviewType reviewType;
}