package nulp.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails dispatcher = User.builder()
                .username("dispatcher")
                .password(encoder.encode("disp123"))
                .roles("DISPATCHER")
                .build();

        return new InMemoryUserDetailsManager(admin, dispatcher);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // ADMIN керує рейсами
                        .requestMatchers(HttpMethod.POST,   "/api/flights/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/api/flights/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/flights/**").hasRole("ADMIN")
                        // DISPATCHER формує бригади
                        .requestMatchers("/api/flights/*/crew/**").hasRole("DISPATCHER")
                        // читання рейсів доступне обом ролям
                        .requestMatchers(HttpMethod.GET, "/api/flights/**").hasAnyRole("ADMIN","DISPATCHER")
                        // все інше — відхилити
                        .anyRequest().denyAll()
                )
                // HTTP Basic з поточним Customizer API
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
