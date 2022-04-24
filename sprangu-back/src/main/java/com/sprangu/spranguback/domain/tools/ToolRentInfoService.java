package com.sprangu.spranguback.domain.tools;

import com.sprangu.spranguback.domain.tools.model.dto.RentEndDto;
import com.sprangu.spranguback.domain.tools.model.dto.RentStartDto;
import com.sprangu.spranguback.domain.tools.model.dto.ToolRentInfoDto;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;
import com.sprangu.spranguback.domain.tools.repository.ToolRentInfoRepository;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ToolRentInfoService {

    private static final Double PENALTY_RATE = 0.2;

    private final ToolRentInfoRepository toolRentInfoRepository;
    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

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
                .originalEndDate(rentStartDto.getRentEndDate())
                .hourlyPrice(toolToRent.getHourlyPrice())
                .dailyPrice(toolToRent.getDailyPrice())
                .build();
        toolRentInfoRepository.save(toolRent);
        return true;
    }

    public RentEndDto stopRent(@NonNull Long rentInfoId) {
        ToolRentInfo toolRentInfo = toolRentInfoRepository.getById(rentInfoId);
        toolRentInfo.setRealEndDate(LocalDateTime.now());

        return RentEndDto.builder()
                .totalPrice(calculateTotalPrice(toolRentInfo))
                .originalEndDate(toolRentInfo.getOriginalEndDate())
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

    public RegisteredUser getCurrentUser(@NonNull Long toolId) {
        return toolRentInfoRepository.getNewestRentInfo(toolId).map(ToolRentInfo::getUser).orElse(null);
    }

    public List<ToolRentInfoDto> getToolRentInfoForUser(@NonNull Long userId) {
        return toolRentInfoRepository.getToolRentInfoForUser(userId);
    }
}
