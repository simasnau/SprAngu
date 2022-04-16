package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolBasicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolsController {

    private final ToolsService toolsService;

    @GetMapping("/{id}")
    public Tool getById(@PathVariable Long id) {
        return toolsService.getById(id);
    }

    @GetMapping("/all")
    public List<Tool> getAllTools() {
        return toolsService.getAllTools();
    }

    @PostMapping()
    public Tool addTool(@RequestBody Tool tool) {
        return toolsService.addTool(tool);
    }

    @PostMapping("/search")
    public List<Tool> searchTools(@RequestBody ToolsFilter toolsFilter) {return toolsService.searchTools(toolsFilter);}

}
