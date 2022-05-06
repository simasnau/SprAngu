package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.dto.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolDto;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ToolsService {

    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final ToolRentInfoService toolRentInfoService;

    public List<ToolDto> getAllTools() {
        return toolRepository.findAll()
                .stream()
                .filter(tool -> !tool.getRemoved())
                .map(tool -> ToolDto.of(tool, toolRentInfoService.getCurrentUser(tool.getId())))
                .collect(Collectors.toList());
    }

    public Long create(@NonNull ToolCreateDto toolCreateDto) {
        Tool tool = Tool.builder()
                .hourlyPrice(toolCreateDto.getHourlyPrice())
                .dailyPrice(toolCreateDto.getDailyPrice())
                .name(toolCreateDto.getName())
                .description(toolCreateDto.getDescription())
                .owner(userRepository.getById(toolCreateDto.getOwnerId()))
                .toolType(toolCreateDto.getToolType())
                .imageContent(toolCreateDto.getImageContent())
                .build();

        return toolRepository.save(tool).getId();
    }

    public ToolDto getById(@NonNull Long id) {
        return ToolDto.of(toolRepository.getById(id), toolRentInfoService.getCurrentUser(id));
    }

    public List<ToolDto> searchTools(@NonNull ToolsFilter toolsFilter) {
        return toolRepository.searchTools(toolsFilter)
                .stream()
                .map(tool -> ToolDto.of(tool, toolRentInfoService.getCurrentUser(tool.getId())))
                .collect(Collectors.toList());
    }

    public List<ToolBasicDto> getAllUserToolsById(Long userId) {
        return toolRepository.getAllUserToolsById(userId);
    }

    public Boolean deleteTool(Long toolId) {
        var tool = toolRepository.getById(toolId);
        tool.setRemoved(true);
        toolRepository.save(tool);
        return toolRentInfoService.getCurrentUser(toolId) == null;
    }

    public Boolean changeToolVisibility(Long toolId) {
        var tool = toolRepository.getById(toolId);
        tool.setVisible(!tool.isVisible());
        return toolRepository.save(tool).isVisible();
    }
}
