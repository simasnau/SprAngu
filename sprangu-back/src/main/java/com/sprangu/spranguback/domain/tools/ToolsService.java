package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.model.ToolShortView;
import com.sprangu.spranguback.domain.tools.model.ToolView;
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

    public List<ToolView> getAllTools() {
        return toolRepository.findAll().stream().map(ToolView::of).collect(Collectors.toList());
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

    public ToolView getById(@NonNull Long id) {
        return ToolView.of(toolRepository.getById(id));
    }

    public List<ToolView> searchTools(@NonNull ToolsFilter toolsFilter) {
        return toolRepository.searchTools(toolsFilter)
                .stream()
                .map(ToolView::of)
                .collect(Collectors.toList());
    }

    public List<ToolShortView> getAllUserToolsById(Long userId) {
        return toolRepository.getAllUserToolsById(userId);
    }

    public Boolean deleteTool(Long toolId) {
        // todo security and add check if none of users have borrowed tool if needed
        try {
            toolRepository.deleteById(toolId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Boolean changeToolVisibility(Long toolId) {
        var tool = toolRepository.getById(toolId);
        tool.setVisible(!tool.isVisible());
        return toolRepository.save(tool).isVisible();
    }
}
