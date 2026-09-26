package vn.rikkei.exam.parkingreservation.dto.response;

public record OperationResponse(
        String requestId,
        String status,
        String message
) { }
