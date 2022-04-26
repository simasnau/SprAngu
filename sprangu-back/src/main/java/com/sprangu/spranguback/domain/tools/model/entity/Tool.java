package com.sprangu.spranguback.domain.tools.model.entity;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TOOL")
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private RegisteredUser owner;

    @Column(name = "HOURLY")
    private Integer hourlyPrice;

    @Column(name = "DAILY")
    private Integer dailyPrice;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ToolTypeEnum toolType;

    @Lob
    @Column(name = "IMAGE_CONTENT")
    private String imageContent;

    @Column(name = "VISIBLE")
    private boolean visible = true;
}
