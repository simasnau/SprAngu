package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolsService {

    private final ToolRepository toolRepository;

    public List<Tool> getAllTools() {
        return toolRepository.findAll();
    }

    public Tool addTool(@NonNull Tool tool) {
        return toolRepository.save(tool);
    }

    public Tool getById(@NonNull Long id) {
        return toolRepository.getById(id);
    }
}
