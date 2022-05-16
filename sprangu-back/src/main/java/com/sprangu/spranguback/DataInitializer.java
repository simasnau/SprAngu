package com.sprangu.spranguback;

import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.tools.model.entity.Tool;
import com.sprangu.spranguback.domain.tools.model.entity.ToolRentInfo;
import com.sprangu.spranguback.domain.tools.repository.ToolRentInfoRepository;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import com.sprangu.spranguback.domain.user.model.UserRole;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ToolRepository toolRepository;
    private final UserRepository userRepository;
    private final ToolRentInfoRepository toolRentInfoRepository;

    @Override
    public void run(String... args) {
        if (!Arrays.asList(args).contains("insertData")) {
            return;
        }

        RegisteredUser jonas = RegisteredUser.builder().name("Jonas").password("Jonas").role(UserRole.USER).build();
        userRepository.save(jonas);

        RegisteredUser petras = RegisteredUser.builder().name("Petras").password("Petras").role(UserRole.USER).build();
        userRepository.save(petras);

        RegisteredUser ona = RegisteredUser.builder().name("Ona").password("Ona").role(UserRole.USER).build();
        userRepository.save(ona);

        Tool graztas = Tool.builder()
                .name("Ultimate grąžtas 3000")
                .description("Pats geriausias grąžtas pasaulyje")
                .hourlyPrice(200)
                .dailyPrice(1000)
                .toolType(ToolTypeEnum.GRAZTAS)
                .owner(jonas)
                .visible(true)
                .build();

        toolRepository.save(graztas);

        ToolRentInfo graztasRent = ToolRentInfo.builder()
                .rentedTool(graztas)
                .user(petras)
                .hourlyPrice(graztas.getHourlyPrice())
                .dailyPrice(graztas.getDailyPrice())
                .startDate(LocalDateTime.now())
                .originalEndDate(LocalDateTime.now().plusDays(10))
                .build();
        toolRentInfoRepository.save(graztasRent);

        Tool graztas2 = Tool.builder()
                .name("Regular grąžtas")
                .description("Atlieka savo paskirtį")
                .hourlyPrice(5)
                .dailyPrice(20)
                .toolType(ToolTypeEnum.GRAZTAS)
                .owner(jonas)
                .visible(true)
                .build();

        toolRepository.save(graztas2);

        ToolRentInfo graztas2Rent = ToolRentInfo.builder()
                .rentedTool(graztas2)
                .user(ona)
                .hourlyPrice(graztas2.getHourlyPrice())
                .dailyPrice(graztas2.getDailyPrice())
                .startDate(LocalDateTime.now())
                .originalEndDate(LocalDateTime.now().plusDays(8))
                .build();
        toolRentInfoRepository.save(graztas2Rent);

        Tool pjuklas = Tool.builder()
                .name("Ultimate pjūklas 4000")
                .description("Pats geriausias pjūklas pasaulyje")
                .hourlyPrice(50)
                .dailyPrice(200)
                .toolType(ToolTypeEnum.PJUKLAS)
                .owner(petras)
                .visible(true)
                .build();

        toolRepository.save(pjuklas);
    }
}
