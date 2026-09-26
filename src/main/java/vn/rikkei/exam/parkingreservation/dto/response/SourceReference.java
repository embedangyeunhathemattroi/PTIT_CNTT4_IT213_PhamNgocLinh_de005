package vn.rikkei.exam.parkingreservation.dto.response;

public record SourceReference(
        String source,
        String section,
        String citation,
        Double score
) { }
