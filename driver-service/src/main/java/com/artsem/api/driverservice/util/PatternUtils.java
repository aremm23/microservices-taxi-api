package com.artsem.api.driverservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PatternUtils {
    public static final String PLATE_NUMBER_PATTERN = "^\\d{4}[A-Z]{2}-\\d{1}$";
}
