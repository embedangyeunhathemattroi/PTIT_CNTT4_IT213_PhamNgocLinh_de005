package vn.rikkei.exam.parkingreservation.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateParkingSpotRequestResult {
    private String requestId;
    private String userId;
    private String resourceCode;
    private String resourceName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer participantCount;
    private String purpose;
    private String status;
    private String message;
}
