package vn.rikkei.exam.parkingreservation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity @Table(name = "resource_types") @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ResourceType { @Id @Column(name = "resource_code") private String resourceCode; private String displayName; private Integer maxParticipants; private Boolean active; }
