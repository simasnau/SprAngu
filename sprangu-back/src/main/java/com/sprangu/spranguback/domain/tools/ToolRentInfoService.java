package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.application.utils.SecurityUtils;
import com.sprangu.spranguback.domain.tools.model.dto.CurrentRentInfo;
import com.sprangu.spranguback.domain.tools.model.dto.RentEndDto;
import com.sprangu.spranguback.domain.tools.model.dto.RentStartDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolRentInfoDto;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;
import com.sprangu.spranguback.domain.tools.repository.ToolRentInfoRepository;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import com.sprangu.spranguback.domain.user.UserService;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ToolRentInfoService {

    private static final Double PENALTY_RATE = 1.2;

    private final ToolRentInfoRepository toolRentInfoRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Boolean rent(@NonNull Long toolId, @NonNull RentStartDto rentStartDto) {
        Optional<ToolRentInfo> newestRentInfo = toolRentInfoRepository.getNewestRentInfo(toolId);

        if (newestRentInfo.isPresent() && newestRentInfo.get().getRealEndDate() == null) {
            throw new IllegalArgumentException("This tool is already rented");
        }

        Tool toolToRent = toolRepository.getById(toolId);

        ToolRentInfo toolRent = ToolRentInfo.builder()
                .rentedTool(toolToRent)
                .user(userRepository.getById(rentStartDto.getUserId()))
                .startDate(LocalDateTime.now())
                .originalEndDate(rentStartDto.getRentEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .hourlyPrice(toolToRent.getHourlyPrice())
                .dailyPrice(toolToRent.getDailyPrice())
                .build();
        toolRentInfoRepository.save(toolRent);
        return true;
    }

    @SneakyThrows
    public RentEndDto stopRent(@NonNull Long rentInfoId) {
        ToolRentInfo toolRentInfo = toolRentInfoRepository.getById(rentInfoId);

        SecurityUtils.checkAccess(toolRentInfo.getUser().getId());

        toolRentInfo.setRealEndDate(LocalDateTime.now());
        toolRentInfoRepository.save(toolRentInfo);

        return RentEndDto.builder()
                .totalPrice(calculateTotalPrice(toolRentInfo))
                .daysLate(ChronoUnit.DAYS.between(toolRentInfo.getOriginalEndDate(), toolRentInfo.getRealEndDate()))
                .build();
    }

    private Double calculateTotalPrice(ToolRentInfo toolRentInfo) {
        LocalDateTime startDate = toolRentInfo.getStartDate();
        LocalDateTime endDate = toolRentInfo.getRealEndDate();
        LocalDateTime originalEndDate = toolRentInfo.getOriginalEndDate();
        double multiplier = endDate.isAfter(originalEndDate) ? PENALTY_RATE : 1;

        long rentHours = ChronoUnit.HOURS.between(startDate, endDate) + 1;
        if (rentHours < 24) {
            return rentHours * toolRentInfo.getHourlyPrice() * multiplier;
        }

        long rentDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return rentDays * toolRentInfo.getDailyPrice() * multiplier;
    }

    public CurrentRentInfo getCurrentRentInfo(@NonNull Long toolId) {
        Optional<ToolRentInfo> newestRentInfo = toolRentInfoRepository.getNewestRentInfo(toolId);
        return newestRentInfo.map(this::mapToCurrentRentInfo).orElse(null);
    }

    private CurrentRentInfo mapToCurrentRentInfo(@NonNull ToolRentInfo toolRentInfo) {
        if (toolRentInfo.getRealEndDate() != null) {
            return null;
        }

        return CurrentRentInfo.builder()
                .rentId(toolRentInfo.getId())
                .currentUser(toolRentInfo.getUser())
                .rentEndDate(toolRentInfo.getOriginalEndDate())
                .build();
    }

    public List<ToolRentInfoDto> getToolRentInfoForUser(@NonNull Long userId) {
        return toolRentInfoRepository.getToolRentInfoForUser(userId);
    }
}
