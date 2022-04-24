package com.sprangu.spranguback.domain.tools.model.entity;

import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Table(name = "TOOL_RENT_INFO")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ToolRentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TOOL_ID", nullable = false)
    private Tool rentedTool;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private RegisteredUser user;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime originalEndDate;

    @Column(name = "REAL_END_DATE")
    private LocalDateTime realEndDate;

    @Column(name = "HOURLY", nullable = false)
    private Integer hourlyPrice;

    @Column(name = "DAILY", nullable = false)
    private Integer dailyPrice;

}
