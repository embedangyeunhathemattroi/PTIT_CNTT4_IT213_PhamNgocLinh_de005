package vn.rikkei.exam.parkingreservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ParkingAvailabilityRequest(
        @NotBlank(message = "resourceType không được để trống") String resourceType,
        @NotNull(message = "startDate không được để trống") LocalDate startDate,
        @NotNull(message = "endDate không được để trống") LocalDate endDate
) { }
