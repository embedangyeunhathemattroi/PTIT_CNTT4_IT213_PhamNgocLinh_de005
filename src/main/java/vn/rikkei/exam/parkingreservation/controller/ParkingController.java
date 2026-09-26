package vn.rikkei.exam.parkingreservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.rikkei.exam.parkingreservation.dto.request.CreateParkingRequest;
import vn.rikkei.exam.parkingreservation.dto.request.ParkingAvailabilityRequest;
import vn.rikkei.exam.parkingreservation.dto.result.CreateParkingSpotRequestResult;
import vn.rikkei.exam.parkingreservation.dto.result.ParkingSpotAvailabilityResult;
import vn.rikkei.exam.parkingreservation.service.ParkingReservationService;

@RestController
@RequestMapping("/api/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingReservationService parkingReservationService;

    @PostMapping("/availability")
    public ResponseEntity<ParkingSpotAvailabilityResult> availability(
            @Valid @RequestBody ParkingAvailabilityRequest request) {
        return ResponseEntity.ok(parkingReservationService.getParkingSpotAvailability(
                request.resourceType(),
                request.startDate(),
                request.endDate()
        ));
    }

    @PostMapping("/requests")
    public ResponseEntity<CreateParkingSpotRequestResult> createRequest(
            @Valid @RequestBody CreateParkingRequest request) {
        return ResponseEntity.ok(parkingReservationService.createParkingSpotRequest(
                request.userId(),
                request.resourceType(),
                request.startDate(),
                request.endDate(),
                request.participantCount(),
                request.purpose()
        ));
    }
}
