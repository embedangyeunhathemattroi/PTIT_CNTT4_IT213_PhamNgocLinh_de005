package vn.rikkei.exam.parkingreservation.dto.response;
public record IngestResponse(
        String source,
        int totalChunks,
        int writtenChunks,
        int skippedChunks,
        String message
) { }
