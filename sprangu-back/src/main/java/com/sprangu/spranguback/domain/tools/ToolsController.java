package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.model.ToolShortView;
import com.sprangu.spranguback.domain.tools.model.ToolView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ToolView getById(@PathVariable Long id) {
        return toolsService.getById(id);
    }

    @GetMapping("/all")
    public List<ToolView> getAllTools() {
        return toolsService.getAllTools();
    }

    @PostMapping()
    public Long addTool(@RequestBody ToolCreateDto tool) {
        return toolsService.create(tool);
    }

    @PostMapping("/search")
    public List<ToolView> searchTools(@RequestBody ToolsFilter toolsFilter) {
        return toolsService.searchTools(toolsFilter);
    }

    @GetMapping("/my-tools/{id}")
    public List<ToolShortView> getAllUserToolsById(@PathVariable("id") Long userId) {
        return toolsService.getAllUserToolsById(userId);
    }

    @DeleteMapping("/my-tools/{id}/delete")
    public Boolean deleteToolFromMyTools(@PathVariable("id") Long toolId) {
        return this.toolsService.deleteTool(toolId);
    }

    @PatchMapping("/my-tools/{id}/edit-visibility")
    public Boolean changeToolVisibility(@PathVariable("id") Long toolId) {
        return this.toolsService.changeToolVisibility(toolId);
    }

}
