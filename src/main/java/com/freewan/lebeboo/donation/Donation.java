package com.freewan.lebeboo.donation;

import com.freewan.lebeboo.campaign.Campaign;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    @Id
    @SequenceGenerator(name = "donation_seq", sequenceName = "donation_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "donation_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Campaign campaign;

    @Column(nullable = false)
    private String customerAccountId;

    private Double amount;

    private LocalDateTime donationMadeAt;

    @PrePersist
    public void prePersist() {
        donationMadeAt = LocalDateTime.now();
    }
}
