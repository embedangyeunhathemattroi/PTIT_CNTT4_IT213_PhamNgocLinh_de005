package vn.rikkei.exam.parkingreservation.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotAvailabilityResult {
    private String resourceCode;
    private String resourceName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean available;
    private Integer minAvailableSlots;
    private String message;
    private List<DailyAvailability> dailyAvailability;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyAvailability {
        private LocalDate date;
        private Integer availableSlots;
        private boolean available;
    }
}
