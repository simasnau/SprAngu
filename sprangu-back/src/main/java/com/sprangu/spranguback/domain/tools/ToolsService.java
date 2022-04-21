package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ToolsService {

    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool create(@NonNull ToolCreateDto toolCreateDto) {
        Tool tool = Tool.builder()
                .hourlyPrice(toolCreateDto.getHourlyPrice())
                .dailyPrice(toolCreateDto.getDailyPrice())
                .name(toolCreateDto.getName())
                .description(toolCreateDto.getDescription())
                .owner(userRepository.getById(toolCreateDto.getOwnerId()))
                .toolType(toolCreateDto.getToolType())
                .imageContent(toolCreateDto.getImageContent())
                .build();

        return toolRepository.save(tool);
    }

    public Tool getById(@NonNull Long id) {
        return toolRepository.getById(id);
    }

    public List<Tool> searchTools(@NonNull ToolsFilter toolsFilter) {return toolRepository.searchTools(toolsFilter);}
}
