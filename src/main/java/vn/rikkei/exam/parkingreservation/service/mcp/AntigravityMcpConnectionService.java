package vn.rikkei.exam.parkingreservation.service.mcp;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AntigravityMcpConnectionService {

    private static final Logger log = LoggerFactory.getLogger(AntigravityMcpConnectionService.class);

    private final RestClient.Builder restClientBuilder;

    @Value("${mcp.antigravity.enabled:false}")
    private boolean enabled;

    @Value("${mcp.antigravity.transport:http}")
    private String transport;

    @Value("${mcp.antigravity.endpoint:}")
    private String endpoint;

    @Value("${mcp.antigravity.token:}")
    private String token;

    @PostConstruct
    public void connect() {
        if (!enabled) {
            log.info("event=mcp_antigravity_skip reason=disabled");
            return;
        }
        if (endpoint == null || endpoint.isBlank() || token == null || token.isBlank()) {
            log.warn("event=mcp_antigravity_unavailable reason=missing_environment_config");
            return;
        }

        try {
            restClientBuilder.build()
                    .get()
                    .uri(normalizedEndpoint())
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .toBodilessEntity();

            log.info("event=mcp_antigravity_connected transport={} endpoint={}",
                    sanitizedTransport(), sanitizedEndpoint());
        } catch (Exception ex) {
            log.warn("event=mcp_antigravity_connection_failed transport={} endpoint={} message={}",
                    sanitizedTransport(), sanitizedEndpoint(), ex.getMessage());
        }
    }

    private String normalizedEndpoint() {
        return endpoint.trim();
    }

    private String sanitizedEndpoint() {
        return endpoint == null || endpoint.isBlank() ? "<empty>" : endpoint.trim();
    }

    private String sanitizedTransport() {
        return transport == null || transport.isBlank() ? "http" : transport.trim();
    }
}
