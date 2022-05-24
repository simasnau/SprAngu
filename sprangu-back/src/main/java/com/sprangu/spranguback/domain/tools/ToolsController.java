package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.dto.RentEndDto;
import com.sprangu.spranguback.domain.tools.model.dto.RentStartDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolBasicDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolCreateDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolRentInfoDto;
import com.sprangu.spranguback.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolsController {

    private final ToolsService toolsService;
    private final UserService userService;
    private final ToolRentInfoService toolRentInfoService;

    @GetMapping("/{id}")
    public ToolDto getById(@PathVariable Long id) {
        return toolsService.getById(id);
    }

    @GetMapping("/{id}/edit")
    public ToolDto getByIdForEdit(@PathVariable Long id) {
        return toolsService.getByIdForEdit(id);
    }

    @GetMapping("/all")
    public List<ToolDto> getAllTools() {
        return toolsService.getAllTools();
    }

    @PostMapping()
    public Long addTool(@RequestBody ToolCreateDto tool) {
        return toolsService.create(tool);
    }

    @PostMapping("/search")
    public List<ToolDto> searchTools(@RequestBody ToolsFilter toolsFilter) {
        return toolsService.searchTools(toolsFilter);
    }

    @GetMapping("/my-tools")
    public List<ToolBasicDto> getAllUserToolsByUserId() {
        return toolsService.getAllUserToolsByUserId();
    }

    @DeleteMapping("/my-tools/{id}/delete")
    public Boolean deleteToolFromMyTools(@PathVariable("id") Long toolId) {
        return toolsService.deleteTool(toolId);
    }

    @PatchMapping("/my-tools/{id}/edit-visibility")
    public Boolean changeToolVisibility(@PathVariable("id") Long toolId) {
        return toolsService.changeToolVisibility(toolId);
    }

    @PostMapping("/{id}/rent")
    public Boolean rentTool(@PathVariable Long id, @RequestBody RentStartDto rentStartDto) {
        return toolRentInfoService.rent(id, rentStartDto);
    }

    @GetMapping("/rent/stop/{rentId}")
    public RentEndDto stopRent(@PathVariable Long rentId) {
        return toolRentInfoService.stopRent(rentId);
    }

    @GetMapping("/rented-tools/{userId}")
    public List<ToolRentInfoDto> getToolRentInfoForUser(@PathVariable Long userId) {
        return toolRentInfoService.getActiveToolRentInfoForUser(userId);
    }

    @GetMapping("/my-rented-tools")
    public List<ToolRentInfoDto> getMyRentedTools() {
        return toolRentInfoService.getActiveToolRentInfoForUser(userService.getLoggedUser().getId());
    }

    @PutMapping("/update")
    public void updateToolDescription(@RequestBody ToolDto toolDto) {
        toolsService.updateTool(toolDto);
    }

    @PutMapping("/create")
    public void createTool(@RequestBody ToolCreateDto toolDto) {
        toolsService.create(toolDto);
    }

    @GetMapping("{id}/full-images")
    public List<String> getFullResolutionToolImages(@PathVariable("id") Long toolId) {
        return toolsService.getFullResolutionToolImages(toolId);
    }

}
