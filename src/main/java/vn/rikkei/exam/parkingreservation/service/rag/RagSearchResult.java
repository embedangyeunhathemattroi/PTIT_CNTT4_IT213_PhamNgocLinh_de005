package vn.rikkei.exam.parkingreservation.service.rag;


import vn.rikkei.exam.parkingreservation.dto.response.SourceReference;

import java.util.List;

public record RagSearchResult(
        String context,
        List<SourceReference> sources
) {
    public boolean hasEvidence() {
        return context != null && !context.isBlank() && sources != null && !sources.isEmpty();
    }
}
