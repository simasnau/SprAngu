package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.application.utils.SecurityUtils;
import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.tools.model.dto.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolDto;

import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.model.security.UserDetailed;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ToolsServiceWithDummyDataImpl implements ToolsService {

    private static final List<ToolDto> tools = new ArrayList<>();

    private final UserRepository userRepository;

    public ToolsServiceWithDummyDataImpl(UserRepository userRepository) {
        this.userRepository = userRepository;

        List<RegisteredUser> allUsers = userRepository.findAll();
        if (allUsers.isEmpty()) {
            return;
        }
        RegisteredUser firstUser = allUsers.get(0);

        ToolDto graztas = ToolDto.builder()
                .id(1L)
                .name("Kažkoks Grąžtas")
                .description("Eilinis grąžtas")
                .owner(firstUser)
                .dailyPrice(4)
                .hourlyPrice(1)
                .toolType(ToolTypeEnum.GRAZTAS)
                .visible(true)
                .build();
        tools.add(graztas);

        ToolDto pjuklas = ToolDto.builder()
                .id(2L)
                .name("Pjūklas 3")
                .description("Pūklas")
                .owner(firstUser)
                .dailyPrice(10)
                .hourlyPrice(2)
                .toolType(ToolTypeEnum.PJUKLAS)
                .visible(true)
                .build();
        tools.add(pjuklas);
    }

    @Override
    public List<ToolDto> getAllTools() {
        return tools.stream()
                .filter(ToolDto::isVisible)
                .collect(Collectors.toList());
    }


    @Override
    public Long create(ToolCreateDto toolCreateDto) {
        SecurityUtils.checkAccess(toolCreateDto.getOwnerId());
        ToolDto tool = ToolDto.builder()
                .hourlyPrice(toolCreateDto.getHourlyPrice())
                .dailyPrice(toolCreateDto.getDailyPrice())
                .name(toolCreateDto.getName())
                .description(toolCreateDto.getDescription())
                .owner(userRepository.getById(toolCreateDto.getOwnerId()))
                .toolType(toolCreateDto.getToolType())
                .imageContent(toolCreateDto.getImageContent())
                .build();

        tools.add(tool);
        return tool.getId();
    }

    @Override
    public ToolDto getById(Long id) {
        return tools.stream()
                .filter(toolDto -> toolDto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Such tool does not exist"));
    }

    @Override
    public List<ToolDto> searchTools(ToolsFilter toolsFilter) {
        return tools.stream()
                .filter(toolDto -> display(toolDto, toolsFilter))
                .collect(Collectors.toList());
    }

    private boolean display(ToolDto toolDto, ToolsFilter toolsFilter) {
        if (toolsFilter.getName() != null && !toolDto.getName().contains(toolsFilter.getName())) {
            return false;
        }

        return toolsFilter.getToolType() == null || toolDto.getToolType() == toolsFilter.getToolType();
    }

    @Override
    public List<ToolBasicDto> getAllUserToolsByUserId() {
        var user = (UserDetailed) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return tools.stream()
                .filter(toolDto -> toolDto.getOwner().getId().equals(user.getId()))
                .map(toolDto -> ToolBasicDto.builder()
                        .id(toolDto.getId())
                        .name(toolDto.getName())
                        .description(toolDto.getDescription())
                        .hourlyPrice(toolDto.getHourlyPrice())
                        .dailyPrice(toolDto.getDailyPrice())
                        .visible(toolDto.isVisible())
                        .image(toolDto.getImageContent() == null || toolDto.getImageContent().isEmpty() ? null : toolDto.getImageContent().get(0))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteTool(Long toolId) {
        var tool = getById(toolId);
        SecurityUtils.checkAccess(tool.getOwner().getId());
        return tools.remove(tool);
    }

    @Override
    public Boolean changeToolVisibility(Long toolId) {
        var tool = getById(toolId);
        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setVisible(!tool.isVisible());
        return tool.isVisible();
    }

    @Override
    public Long updateTool(ToolDto toolDto) {
        var tool = getById(toolDto.getId());
        SecurityUtils.checkAccess(tool.getOwner().getId());
        tool.setName(toolDto.getName());
        tool.setDescription(toolDto.getDescription());
        tool.setHourlyPrice(toolDto.getHourlyPrice());
        tool.setDailyPrice(toolDto.getDailyPrice());
        tool.setImageContent(toolDto.getImageContent());
        tool.setToolType(toolDto.getToolType());
        log.info("Updated tool: " + toolDto.getName());
        return tool.getId();
    }

    @Override
    public String getStrategy() {
        return "DUMMY_DATA";
    }
}
