package vn.rikkei.exam.parkingreservation.dto.response;
import java.util.List;

public record AssistantAskResponse(
        String answer,
        String conversationId,
        List<SourceReference> sources,
        List<String> toolsUsed
) { }
