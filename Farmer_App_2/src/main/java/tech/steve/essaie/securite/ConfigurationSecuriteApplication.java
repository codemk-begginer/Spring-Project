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

import static org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.disable;
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

                                                      .requestMatchers(POST,"/createFerme").permitAll()
                                                      .requestMatchers(GET,"/read").permitAll()
                                                      .requestMatchers(PUT,"/update/{id}").permitAll()
                                                      .requestMatchers(POST,"/delete/{id}").permitAll()
                                                      .requestMatchers(GET,"/findById/{id}").permitAll()

                                        //===================================== User ======================================//
                                                      .requestMatchers(POST,"/activation").permitAll()
                                                      .requestMatchers(POST,"/createUser").permitAll()
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

                                        //===================================== Animal ======================================//

                                                      .requestMatchers(POST,"/createAnimal").permitAll()
                                                      .requestMatchers(GET,"/findBySexe").permitAll()
                                                      .requestMatchers(GET,"/findByPere").permitAll()
                                                      .requestMatchers(GET,"/findByMere").permitAll()
                                                      .requestMatchers(GET,"/findAllByFerme/{fermeId}").permitAll()
                                                      .requestMatchers(GET,"/findAnimalById/{id}").permitAll()

                                        //===================================== Stock ======================================//

                                                      .requestMatchers(POST,"/createStock").permitAll()
                                                      .requestMatchers(PUT,"/updateStock/{id}").permitAll()
                                                      .requestMatchers(POST,"/deleteStock/{id}").permitAll()
                                                      .requestMatchers(GET,"/findStockById/{id}").permitAll()
                                                      .requestMatchers(GET,"/findAll").permitAll()
                                                      .requestMatchers(GET,"/findByProduit").permitAll()
                                                      .requestMatchers(GET,"/findByCategorie").permitAll()
                                                      .requestMatchers(GET,"/findByDateBetween").permitAll()
                                                      .requestMatchers(GET,"/getStockActuel").permitAll()
                                                      .requestMatchers(GET,"/isEnDessousSeuil").permitAll()
                                                      .requestMatchers(GET,"/getAlertesSeuil").permitAll()

                                        //===================================== Transaction ======================================//

                                                      .requestMatchers(POST,"/createTransaction").permitAll()
                                                      .requestMatchers(GET,"/findByAnimalId/{id}").permitAll()
                                                      .requestMatchers(PUT,"/updateTransaction/{id}").permitAll()
                                                      .requestMatchers(POST,"/deleteTransaction/{id}").permitAll()
                                                      .requestMatchers(GET,"/findAllTransaction").permitAll()
                                                      .requestMatchers(GET,"/findTransactionById/{id}").permitAll()
                                                      .requestMatchers(GET,"/findByAnimalId/{id}").permitAll()
                                                      .requestMatchers(GET,"/findTransactionByType").permitAll()
                                                      .requestMatchers(GET,"/findByDateRange").permitAll()
                                                      .requestMatchers(GET,"/getTotalTransactionByType").permitAll()
                                                      .requestMatchers(GET,"/getTotalTransactionForAnimal").permitAll()
                                                      .requestMatchers(GET,"/getTotalGlobal").permitAll()
                                                      .requestMatchers(GET,"/getHistoriqueTransaction/{id}").permitAll()
                                                      .requestMatchers(GET,"/findByIntervenant").permitAll()
                                                      .requestMatchers(GET,"/exportTransactionToExcel").permitAll()
                                                      .requestMatchers(GET,"/exportTransactionToPdf").permitAll()

                                        //===================================== Swagger ======================================//

                                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

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
