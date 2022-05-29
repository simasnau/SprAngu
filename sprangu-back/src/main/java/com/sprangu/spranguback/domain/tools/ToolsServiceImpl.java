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
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ToolsServiceImpl implements ToolsService {

    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final ToolRentInfoService toolRentInfoService;
    private final UserService userService;

    public List<ToolDto> getAllTools() {
        return toolRepository.findAll()
                .stream()
                .filter(tool -> !Boolean.TRUE.equals(tool.getRemoved()) && tool.isVisible())
                .map(tool -> ToolDto.of(tool, toolRentInfoService.getCurrentRentInfo(tool.getId()), false))
                .collect(Collectors.toList());
    }

    public Long create(@NonNull ToolBasicDto toolCreateDto) {
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
        return ToolDto.of(toolRepository.getById(id), toolRentInfoService.getCurrentRentInfo(id), false);
    }

    public List<ToolDto> searchTools(@NonNull ToolsFilter toolsFilter) {
        return toolRepository.searchTools(toolsFilter)
                .stream()
                .map(tool -> ToolDto.of(tool, toolRentInfoService.getCurrentRentInfo(tool.getId()), false))
                .collect(Collectors.toList());
    }

    public List<ToolBasicDto> getAllUserToolsByUserId() {
        return toolRepository.getAllUserToolsByUserId(userService.getLoggedUser().getId())
                .stream()
                .map(tool -> ToolBasicDto.of(tool, userService.getLoggedUser().getId(), false))
                .collect(Collectors.toList());
    }

    public Boolean deleteTool(Long toolId) {
        if (toolRentInfoService.getCurrentRentInfo(toolId) != null) {
            return false;
        }

        var tool = toolRepository.getById(toolId);
        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setRemoved(true);
        toolRepository.save(tool);
        return true;
    }

    public Boolean changeToolVisibility(Long toolId) {
        var tool = toolRepository.getById(toolId);
        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setVisible(!tool.isVisible());
        toolRepository.save(tool);
        return tool.isVisible();
    }

    @ResponseBody
    @Transactional
    public Long updateTool(ToolBasicDto toolDto) {
        var tooId = toolDto.getId();
        var tool = toolRepository.getById(tooId);

        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setName(toolDto.getName());
        tool.setDescription(toolDto.getDescription());
        tool.setHourlyPrice(toolDto.getHourlyPrice());
        tool.setDailyPrice(toolDto.getDailyPrice());
        tool.setImages(toolDto.getImageContent());
        tool.setToolType(toolDto.getToolType());

        if (!Objects.equals(tool.getVersion(), toolDto.getVersion())) {
            throw new OptimisticLockException();
        }

        toolRepository.flush();
        return toolRepository.save(tool).getId();
    }

    @Override
    public String getStrategy() {
        return "H2_DATABASE";
    }

    @Override
    public List<String> getFullResolutionToolImages(Long toolId) {
        var tool = this.toolRepository.getById(toolId);
        Hibernate.initialize(tool.getImages());
        return tool.getImages();
    }

    @Override
    public ToolBasicDto getByIdForEdit(Long id) {
        return ToolBasicDto.of(toolRepository.getById(id), userService.getLoggedUser().getId(), true);
    }
}
