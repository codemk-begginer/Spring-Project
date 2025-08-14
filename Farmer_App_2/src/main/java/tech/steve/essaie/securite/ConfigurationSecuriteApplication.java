package tech.steve.essaie.securite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class ConfigurationSecuriteApplication {




    private final JwtFilter jwtFilter;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ConfigurationSecuriteApplication(JwtFilter jwtFilter, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtFilter = jwtFilter;

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return
                httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(
                                authorize -> authorize.requestMatchers(POST,"/connexion").permitAll()
                                                      .requestMatchers(POST,"/activation").permitAll()
                                                      .requestMatchers(POST,"/createUser").permitAll()
                                                      .requestMatchers(POST,"/createFerme").permitAll()
                                                      .requestMatchers(POST,"/changePassword").permitAll()
                                                      .requestMatchers(POST,"/newPassword").permitAll()
                                                      .requestMatchers(POST,"/refresh-token").permitAll()
                                                      .requestMatchers(PUT,"/updateUser/{id}").permitAll()
                                                      .requestMatchers(GET,"/findUserById/{id}").permitAll()
                                                      .requestMatchers(GET,"/readAllUser").permitAll()
                                                      .requestMatchers(POST,"/deleteUser/{id}").permitAll()
                                                      .requestMatchers(GET,"/findByActif").permitAll()
                                                      .requestMatchers(GET,"/findByFermeId/{id}").permitAll()
                                                      .requestMatchers(GET,"/findByEmail").permitAll()
                                                      .requestMatchers(GET,"/avis").hasAnyAuthority("ROLE_MANAGER","ROLE_ADMINISTRATEUR")


                                        .anyRequest().authenticated()
                        )
                        .sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                        )

                        .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                        .build();
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public AuthenticationProvider AuthenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new  DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }
}
