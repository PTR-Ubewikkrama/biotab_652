package com.test_biotab.test_biotab_server.service.impl;

import com.test_biotab.test_biotab_server.domain.*;
import com.test_biotab.test_biotab_server.dto.UserDto;
import com.test_biotab.test_biotab_server.entity.User;
import com.test_biotab.test_biotab_server.jwt.JwtTokenProvider;
import com.test_biotab.test_biotab_server.repository.UserRepositoryCustom;
import com.test_biotab.test_biotab_server.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepositoryCustom userRepository;
    private final JwtTokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Mono<User> findByEmail(String email) {
        return Mono.just(email)
                .map(userRepository::findByEmail)
                .onErrorResume(e -> Mono.empty());
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<AuthResponse>>> login(LoginRequest loginRequest) {
        return Mono.just(loginRequest)
                .flatMap(request -> findByEmail(request.getEmail())
                        .doOnNext(user -> log.info("User found: {}", user))
                        .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                        .flatMap(user -> createToken(user, request.getPassword())
                                .map(token -> ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                                        .status("S1000")
                                        .statusDescription("Login successful")
                                        .data(AuthResponse.builder()
                                                .token(token)
                                                .username(user.getUsername())
                                                .group(user.getUserGroup())
                                                .roles(user.getAuthorities().stream().map(Object::toString).toList())
                                                .status(user.getStatus())
                                                .build())
                                        .build()))
                                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(ApiResponse.<AuthResponse>builder()
                                        .status("E1000")
                                        .statusDescription("Invalid credentials")
                                        .build())))
                        )
                        .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(ApiResponse.<AuthResponse>builder()
                                .status("E1001")
                                .statusDescription("Username or password is incorrect")
                                .build()))))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.<AuthResponse>builder()
                        .status("E1000")
                        .statusDescription("Invalid credentials")
                        .build())));
    }

    private Mono<String> createToken(User user, String password) {
        return this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), password, user.getAuthorities()))
                .map(this.tokenProvider::createToken);
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> register(RegisterRequest registerRequest, UserDetails principal) {
        return Mono.just(registerRequest)
                .doOnNext(request -> log.info("Register request: {}", request))
                .flatMap(request -> findByEmail(request.getEmail())
                        .flatMap(user -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                                .status("E1002")
                                .statusDescription("User already exists")
                                .build()))
                        )
                        .switchIfEmpty(Mono.just(getUser(registerRequest, principal))
                                .map(userRepository::save)
                                .map(user -> ResponseEntity.ok(ApiResponse.<Void>builder()
                                        .status("S1000")
                                        .statusDescription("User registered successfully")
                                        .build()))
                        )
                )
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                        .status("E1003")
                        .statusDescription("Error registering user")
                        .build())));
    }

    @Override
    public Mono<? extends ResponseEntity<ApiResponse<GetUsersResponse>>> getUsers(GetByPatternRequest request, UserDetails userDetails, String pageNo) {
        return Mono.just(request)
                .map(req -> {
                    log.info("Getting Users with pattern: {} by user: {}", request.getFilterValue(), userDetails.getUsername());
                    if (pageNo != null && pageNo.equals("all")) {
                        return userRepository.findByCustomQuery(getCustomQuery(request, userDetails));
                    } else {
                        return userRepository.findByCustomQuery(getCustomQuery(request, userDetails), Integer.parseInt(pageNo) - 1);
                    }
                })
                .flatMap(users -> Mono.just(userRepository.countByCustomQuery(getCustomCountQuery(request, userDetails)))
                        .map(total -> ApiResponse.<GetUsersResponse>builder()
                                .status("S1000")
                                .statusDescription("Request successful")
                                .data(GetUsersResponse.builder()
                                        .users(getUserDtos(users))
                                        .total(total)
                                        .build())
                                .build())
                )
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "E1004", "Failed to get Users")));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<Void>>> resetPassword(ResetPasswordRequest resetPasswordRequest, UserDetails userDetails) {
        return Mono.just(resetPasswordRequest)
                .flatMap(request -> findByEmail(request.getEmail())
                        .map(user -> {
                            user.setPassword(passwordEncoder.encode(request.getPassword()));
                            user.setUpdatedAt(LocalDateTime.now());
                            user.setStatus("ACTIVE");
                            return user;
                        })
                        .map(userRepository::save)
                        .map(user -> ResponseEntity.ok(ApiResponse.<Void>builder()
                                .status("S1000")
                                .statusDescription("Password reset successfully")
                                .build())
                )
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                        .status("E1005")
                        .statusDescription("User not found")
                        .build())))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                        .status("E1006")
                        .statusDescription("Error resetting password")
                        .build()))));
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<AuthResponse>>> refresh(UserDetails userDetails) {
        return Mono.just(userDetails)
                .flatMap(user ->
                        findByEmail(user.getUsername())
                                .flatMap(u -> Mono.just(tokenProvider.refreshToken(userDetails))
                                        .map(token -> ResponseEntity.ok(ApiResponse.<AuthResponse>builder()
                                                .status("S1000")
                                                .statusDescription("Token refreshed")
                                                .data(AuthResponse.builder()
                                                        .token(token)
                                                        .username(u.getUsername())
                                                        .group(u.getUserGroup())
                                                        .roles(u.getAuthorities().stream().map(Object::toString).toList())
                                                        .build())
                                                .build()))
                                        .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(ApiResponse.<AuthResponse>builder()
                                                .status("E1000")
                                                .statusDescription("Invalid credentials")
                                                .build()))
                                        )
                                )
                )
                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body(ApiResponse.<AuthResponse>builder()
                        .status("E1001")
                        .statusDescription("Username or password is incorrect")
                        .build())))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.<AuthResponse>builder()
                        .status("E1000")
                        .statusDescription("Invalid credentials")
                        .build())));
    }

    private List<UserDto> getUserDtos(List<User> users) {
        return users.stream()
                .map(user -> UserDto.builder()
                        .id(user.getId().intValue())
                        .email(user.getEmail())
                        .name(user.getName())
                        .group(user.getUserGroup())
                        .status(user.getStatus())
                        .createdBy(user.getCreatedBy())
                        .createdAt(user.getCreatedAt().toString())
                        .updatedAt(user.getUpdatedAt().toString())
                        .build())
                .toList();
    }

    private User getUser(RegisterRequest registerRequest, UserDetails principal) {

        return User.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .userGroup(registerRequest.getUserType())
                .password(passwordEncoder.encode("password@test"))
                .status("INITIAL")
                .createdBy(principal.getUsername())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private TypedQuery<User> getCustomQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT rm FROM User rm ");

        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<User> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder)
                .append(" ORDER BY rm.createdAt DESC").toString(), User.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private TypedQuery<Long> getCustomCountQuery(GetByPatternRequest request, UserDetails userDetails) {
        StringBuilder queryBuilder = new StringBuilder("SELECT COUNT(rm) FROM User rm ");
        List<String> filterParts = new ArrayList<>();

        calculateFilterParts(request, filterParts, userDetails);

        TypedQuery<Long> query = entityManager.createQuery(getQueryByFilterPartsAndBaseQuery(filterParts, queryBuilder).toString(), Long.class);

        return exchangeDateFilterInQuery(query, request);
    }

    private static void calculateFilterParts(GetByPatternRequest request, List<String> filterParts, UserDetails userDetails) {
        if (request.getFilterType() != null && !request.getFilterValue().isEmpty()) {
            switch (request.getFilterType()) {
                case "NAME" -> filterParts.add("name LIKE '%" + request.getFilterValue() + "%'");
                case "EMAIL" -> filterParts.add("email LIKE '%" + request.getFilterValue() + "%'");
                case "CREATED_BY" -> filterParts.add("createdBy LIKE '%" + request.getFilterValue() + "%'");
                case "STATUS" -> filterParts.add("status LIKE '%" + request.getFilterValue() + "%'");
            }
        } else if (request.getFilterValue() != null && !request.getFilterValue().isEmpty()) {
            filterParts.add("name LIKE '%" + request.getFilterValue() + "%'");
        }

        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADD_ADMINS"))) {
            filterParts.add("createdBy = '" + userDetails.getUsername() + "'");
        }

        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            filterParts.add("rm.createdAt < :endDate AND rm.createdAt > :startDate");
        }
    }

    private <T> TypedQuery<T> exchangeDateFilterInQuery(TypedQuery<T> query, GetByPatternRequest request) {
        if (request.getFromDate() != null && !request.getFromDate().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            LocalDateTime lastDateStart = LocalDateTime.of(LocalDateTime.parse(request.getFromDate(), formatter).toLocalDate(), LocalTime.MIDNIGHT);
            LocalDateTime lastDateEnd = LocalDateTime.of(LocalDateTime.parse(request.getFromDate(), formatter).toLocalDate(), LocalTime.MAX);

            return query.setParameter("startDate", lastDateStart)
                    .setParameter("endDate", lastDateEnd);
        }
        return query;
    }

    private StringBuilder getQueryByFilterPartsAndBaseQuery(List<String> filterParts, StringBuilder query) {
        if (!filterParts.isEmpty()) {
            query.append(" WHERE ");
            for (int i = 0; i < filterParts.size(); i++) {
                query.append(filterParts.get(i));
                if (i < filterParts.size() - 1) {
                    query.append(" AND ");
                }
            }
        }

        return query;
    }
}
