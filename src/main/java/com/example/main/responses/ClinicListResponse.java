package com.example.main.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ClinicListResponse {
    private List<ClinicResponse> clinics;
    private int totalPages;
}
