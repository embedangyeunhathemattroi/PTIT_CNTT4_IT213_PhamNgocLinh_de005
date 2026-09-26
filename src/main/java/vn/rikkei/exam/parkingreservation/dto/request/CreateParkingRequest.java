package vn.rikkei.exam.parkingreservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateParkingRequest(
        @NotBlank(message = "userId không được để trống") String userId,
        @NotBlank(message = "resourceType không được để trống") String resourceType,
        @NotNull(message = "startDate không được để trống") LocalDate startDate,
        @NotNull(message = "endDate không được để trống") LocalDate endDate,
        @NotNull(message = "participantCount không được để trống")
        @Positive(message = "participantCount phải lớn hơn 0") Integer participantCount,
        @NotBlank(message = "purpose không được để trống")
        @Size(min = 10, max = 200, message = "purpose phải có độ dài từ 10 đến 200 ký tự") String purpose
) { }
