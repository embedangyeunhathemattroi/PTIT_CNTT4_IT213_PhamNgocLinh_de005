package vn.rikkei.exam.parkingreservation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AssistantAskRequest(
        @NotBlank(message = "conversationId không được để trống") String conversationId,
        String userId,
        @NotBlank(message = "message không được để trống") String message
) { }
