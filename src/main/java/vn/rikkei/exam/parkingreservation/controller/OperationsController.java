package vn.rikkei.exam.parkingreservation.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.rikkei.exam.parkingreservation.dto.request.ApproveRequestRequest;
import vn.rikkei.exam.parkingreservation.dto.response.OperationResponse;
import vn.rikkei.exam.parkingreservation.service.ReservationOperationService;

@RestController
@RequestMapping("/api/operations")
@RequiredArgsConstructor
public class OperationsController {

    private final ReservationOperationService operationService;

    @PostMapping("/approve-request")
    public ResponseEntity<OperationResponse> approveRequest(@Valid @RequestBody ApproveRequestRequest request) {
        return ResponseEntity.ok(operationService.process(
                request.requestId(), request.decision(), request.note()));
    }
}
