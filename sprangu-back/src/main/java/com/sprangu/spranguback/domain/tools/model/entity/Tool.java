package com.sprangu.spranguback.domain.tools.model.entity;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "IMAGE_CONTENT", joinColumns = @JoinColumn(name = "TOOL_ID"))
    @Column(name = "CONTENT", columnDefinition="CLOB")
    private List<String> images = new ArrayList<>();

    @Column(name = "VISIBLE")
    private Boolean visible = true;

    @Column(name = "REMOVED")
    private Boolean removed;
}
