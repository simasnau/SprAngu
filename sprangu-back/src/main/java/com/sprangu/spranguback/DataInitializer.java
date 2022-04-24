package com.sprangu.spranguback;

import com.sprangu.spranguback.domain.tools.model.Tool;
import com.sprangu.spranguback.domain.tools.model.ToolTypeEnum;
import com.sprangu.spranguback.domain.tools.repository.ToolRepository;
import com.sprangu.spranguback.domain.user.model.entity.RegisteredUser;
import com.sprangu.spranguback.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ToolRepository toolRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (!Arrays.asList(args).contains("insertData")) {
            return;
        }

        RegisteredUser jonas = RegisteredUser.builder().name("Jonas").password("Jonas").build();
        userRepository.save(jonas);

        RegisteredUser petras = RegisteredUser.builder().name("Petras").password("Petras").build();
        userRepository.save(petras);

        RegisteredUser ona = RegisteredUser.builder().name("Ona").password("Ona").build();
        userRepository.save(ona);

        Tool graztas = Tool.builder()
                .name("Ultimate grąžtas 3000")
                .description("Pats geriausias grąžtas pasaulyje")
                .hourlyPrice(200)
                .dailyPrice(1000)
                .toolType(ToolTypeEnum.GRAZTAS)
                .owner(jonas)
                .visible(false)
                .build();

        graztas.setCurrentUser(petras);
        toolRepository.save(graztas);

        Tool graztas2 = Tool.builder()
                .name("Regular grąžtas")
                .description("Atlieka savo paskirtį")
                .hourlyPrice(5)
                .dailyPrice(20)
                .toolType(ToolTypeEnum.GRAZTAS)
                .owner(jonas)
                .build();

        graztas2.setCurrentUser(ona);
        toolRepository.save(graztas2);

        Tool pjuklas = Tool.builder()
                .name("Ultimate pjūklas 4000")
                .description("Pats geriausias pjūklas pasaulyje")
                .hourlyPrice(50)
                .dailyPrice(200)
                .toolType(ToolTypeEnum.PJUKLAS)
                .owner(petras)
                .build();

        pjuklas.setCurrentUser(ona);
        toolRepository.save(pjuklas);
    }
}
