package com.ivan.patientservice.api;

import lombok.Data;

@Data
public class Meta {

    private int page;
    private int pageSize;
    private int totalPages;
    private long totalItems;

    public Meta(int page, int size, int totalPages, long totalItems) {
        this.page = page;
        this.pageSize = size;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

}
