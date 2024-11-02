package com.artsem.api.driverservice.model.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ListResponseDto<T> {
    private int size;
    private List<T> list;
}
