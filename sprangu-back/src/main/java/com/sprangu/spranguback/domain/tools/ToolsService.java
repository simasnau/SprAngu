package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.application.utils.SecurityUtils;
import com.sprangu.spranguback.domain.tools.model.dto.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolDto;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import com.sprangu.spranguback.domain.user.UserService;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.OptimisticLockException;
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
    private final UserService userService;

    public List<ToolDto> getAllTools() {
        return toolRepository.findAll()
                .stream()
                .filter(tool -> !Boolean.TRUE.equals(tool.getRemoved()) && tool.getVisible())
                .map(tool -> ToolDto.of(tool, toolRentInfoService.getCurrentUser(tool.getId())))
                .collect(Collectors.toList());
    }

    public Long create(@NonNull ToolCreateDto toolCreateDto) {
        SecurityUtils.checkAccess(toolCreateDto.getOwnerId());
        Tool tool = Tool.builder()
                .hourlyPrice(toolCreateDto.getHourlyPrice())
                .dailyPrice(toolCreateDto.getDailyPrice())
                .name(toolCreateDto.getName())
                .description(toolCreateDto.getDescription())
                .owner(userRepository.getById(toolCreateDto.getOwnerId()))
                .toolType(toolCreateDto.getToolType())
                .images(toolCreateDto.getImageContent())
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

    public List<ToolBasicDto> getAllUserToolsByUserId() {
        return toolRepository.getAllUserToolsByUserId(userService.getLoggedUser().getId())
                .stream()
                .map(ToolBasicDto::of)
                .collect(Collectors.toList());
    }

    public Boolean deleteTool(Long toolId) {
        var tool = toolRepository.getById(toolId);
        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setRemoved(true);
        toolRepository.save(tool);
        return toolRentInfoService.getCurrentUser(toolId) == null;
    }

    public Boolean changeToolVisibility(Long toolId) {
        var tool = toolRepository.getById(toolId);
        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setVisible(!tool.getVisible());
        return toolRepository.save(tool).getVisible();
    }

    @ResponseBody
    @Transactional
    public ToolDto updateTool(ToolDto toolDto) {
        var tooId = toolDto.getId();
        var tool = toolRepository.getById(tooId);
//        tool.setVersion(tool.getVersion() + 1);

        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setName(toolDto.getName());
        tool.setDescription(toolDto.getDescription());
        tool.setHourlyPrice(toolDto.getHourlyPrice());
        tool.setDailyPrice(toolDto.getDailyPrice());
        tool.setImages(toolDto.getImageContent());
        tool.setToolType(toolDto.getToolType());

        System.out.println("Original Version:" + tool.getVersion());
        System.out.println("New Version:" + toolDto.getVersion());

        tool.setVersion(toolDto.getVersion());

        toolRepository.saveAndFlush(tool);
//        toolRepository.save(tool);
//        toolRepository.flush();

        return ToolDto.of(toolRepository.getById(tooId), toolRentInfoService.getCurrentUser(tooId));
    }
}
