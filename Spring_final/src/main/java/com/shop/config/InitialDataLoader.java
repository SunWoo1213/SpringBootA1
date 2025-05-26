package com.shop.config;

import com.shop.entity.User;
import com.shop.entity.Role;
import com.shop.entity.Product;
import com.shop.repository.UserRepository;
import com.shop.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class InitialDataLoader {

    @Bean
    public CommandLineRunner initializeData(UserRepository userRepository, 
                                         ProductRepository productRepository,
                                         PasswordEncoder passwordEncoder) {
        return args -> {
            // admin 계정이 없을 경우에만 생성
            if (!userRepository.existsByUsername("admin")) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setEmail("admin@shop.com");
                adminUser.setNickname("관리자");
                adminUser.setRole(Role.ADMIN);
                
                userRepository.save(adminUser);
            }

            // 상품 데이터가 없을 경우에만 샘플 데이터 생성
            if (productRepository.count() == 0) {
                // 상품 1
                Product product1 = new Product();
                product1.setName("최신형 스마트폰");
                product1.setDescription("고성능 프로세서와 멋진 카메라를 탑재한 최신형 스마트폰입니다.");
                product1.setPrice(1200000);
                product1.setImageUrl("https://via.placeholder.com/300x200");
                productRepository.save(product1);

                // 상품 2
                Product product2 = new Product();
                product2.setName("노트북");
                product2.setDescription("가볍고 성능 좋은 비즈니스용 노트북입니다.");
                product2.setPrice(1500000);
                product2.setImageUrl("https://via.placeholder.com/300x200");
                productRepository.save(product2);

                // 상품 3
                Product product3 = new Product();
                product3.setName("무선이어폰");
                product3.setDescription("고음질 무선 이어폰입니다.");
                product3.setPrice(300000);
                product3.setImageUrl("https://via.placeholder.com/300x200");
                productRepository.save(product3);
            }
        };
    }
} 