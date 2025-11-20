package com.example.ETCSystem.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public static <T> PagedResponse<T> of(
            List<T> content, int page, int size, long totalElements, int totalPages) {
        return new PagedResponse<>(content, page, size, totalElements, totalPages);
    }
}