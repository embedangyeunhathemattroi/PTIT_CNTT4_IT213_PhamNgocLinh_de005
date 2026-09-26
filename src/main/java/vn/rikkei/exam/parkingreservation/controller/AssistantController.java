package vn.rikkei.exam.parkingreservation.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.rikkei.exam.parkingreservation.dto.request.AssistantAskRequest;
import vn.rikkei.exam.parkingreservation.dto.response.AssistantAskResponse;
import vn.rikkei.exam.parkingreservation.service.chat.ParkingReservationAssistantService;


@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
public class AssistantController {

    private final ParkingReservationAssistantService assistantService;

    @PostMapping("/ask")
    public ResponseEntity<AssistantAskResponse> ask(@Valid @RequestBody AssistantAskRequest request) {
        return ResponseEntity.ok(assistantService.ask(request));
    }
}
