package com.freewan.lebeboo.campaign;

import com.freewan.lebeboo.category.Category;
import com.freewan.lebeboo.donation.Donation;
import com.freewan.lebeboo.organization.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {
    @Id
    @SequenceGenerator(name = "campaign_seq", sequenceName = "campaign_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campaign_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "campaign")
    private List<Donation> donations = new ArrayList<>();

    private String label;

    private Double amount;

    private LocalDate deadline;

    @Column(length = 100000000, columnDefinition = "text")
    private String description;

    private Date createdAt;

    private String image;

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @Transient
    public Double getCollectedAmount() {
        return donations.stream().map(Donation::getAmount).reduce(0D, Double::sum);
    }
}
