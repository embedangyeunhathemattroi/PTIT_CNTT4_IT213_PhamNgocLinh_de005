package vn.rikkei.exam.parkingreservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
@Entity @Table(name = "reservation_requests") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservationRequest { @Id private String requestId; @ManyToOne @JoinColumn(name = "user_id") private AppUser requester; @ManyToOne @JoinColumn(name = "resource_code") private ResourceType resourceType; private LocalDate startDate; private LocalDate endDate; private Integer participantCount; private String purpose; @Enumerated(EnumType.STRING) private ReservationStatus status; private String decisionNote; private Instant createdAt; private Instant updatedAt; }
