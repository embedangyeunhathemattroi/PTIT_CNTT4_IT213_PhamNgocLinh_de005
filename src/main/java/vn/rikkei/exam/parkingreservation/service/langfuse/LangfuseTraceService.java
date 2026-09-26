package vn.rikkei.exam.parkingreservation.service.langfuse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import vn.rikkei.exam.parkingreservation.dto.response.SourceReference;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LangfuseTraceService {

    private static final Logger log = LoggerFactory.getLogger(LangfuseTraceService.class);

    private final RestClient.Builder restClientBuilder;

    @Value("${langfuse.public-key:}")
    private String publicKey;

    @Value("${langfuse.secret-key:}")
    private String secretKey;

    @Value("${langfuse.host:http://localhost:3000}")
    private String host;

    public void traceChat(String conversationId,
                          String answer,
                          List<SourceReference> sources,
                          List<String> toolsUsed) {
        if (!isConfigured()) {
            log.debug("event=langfuse_skip reason=not_configured conversationId={}", conversationId);
            return;
        }

        try {
            String traceId = UUID.randomUUID().toString();
            String now = Instant.now().toString();
            Map<String, Object> traceBody = Map.of(
                    "batch", List.of(Map.of(
                            "id", UUID.randomUUID().toString(),
                            "type", "trace-create",
                            "timestamp", now,
                            "body", Map.of(
                                    "id", traceId,
                                    "name", "parking-reservation-assistant-chat",
                                    "sessionId", conversationId,
                                    "output", safeOutput(answer),
                                    "metadata", Map.of(
                                            "conversationId", conversationId,
                                            "examCode", "DE-005",
                                            "toolsUsed", toolsUsed == null ? List.of() : toolsUsed,
                                            "sources", sources == null ? List.of() : sources
                                    )
                            )
                    ))
            );

            restClientBuilder.build()
                    .post()
                    .uri(normalizedHost() + "/api/public/ingestion")
                    .header("Authorization", basicAuth())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(traceBody)
                    .retrieve()
                    .toBodilessEntity();

            log.info("event=langfuse_trace_sent conversationId={} traceId={} toolsUsed={}",
                    conversationId, traceId, toolsUsed);
        } catch (Exception ex) {
            log.warn("event=langfuse_trace_failed conversationId={} message={}",
                    conversationId, ex.getMessage());
        }
    }

    private boolean isConfigured() {
        return publicKey != null && !publicKey.isBlank()
                && secretKey != null && !secretKey.isBlank()
                && host != null && !host.isBlank();
    }

    private String basicAuth() {
        String token = publicKey + ":" + secretKey;
        return "Basic " + Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

    private String normalizedHost() {
        return host.endsWith("/") ? host.substring(0, host.length() - 1) : host;
    }

    private String safeOutput(String answer) {
        if (answer == null) {
            return "";
        }
        return answer.length() <= 2000 ? answer : answer.substring(0, 2000);
    }
}
