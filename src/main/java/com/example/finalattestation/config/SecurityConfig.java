package com.example.finalattestation.config;

import com.example.finalattestation.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{

    private final PersonDetailsService personDetailsService;

    @Bean
    public PasswordEncoder getPasswordEncode(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Конфигурируем работу Spring Security
        http
                .authorizeHttpRequests() //указываем что все страницы должны быть защищены аутентификацией
                //Указываем что на страницу /admin могут зайти только пользователи с ролью ADMIN(Можно писать без префикса ROLE_)
                .requestMatchers("/admin").hasRole("ADMIN")
                //Указываем, что страницы: /authentication,/registration,/error. Доступны всем пользователям
                .requestMatchers("/process_login","/logout","/authentication", "/registration","/error", "/resources/**","/static/**","/css/**","/js/**","/img/**").permitAll()
                //указываем что все остальные страницы доступны, как для USER, так и для ADMIN
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and() //указываем что дальше настраивается аутентификация и соединяем её с настройками доступа
                .formLogin().loginPage("/authentication") // казываем какой url запрос будет отправляться при заходе на защищеные страницы
                .loginProcessingUrl("/process_login") //указываем на какйо адресс будут отправляться данные с формы. Нам уже не нужно будет создавать метод в контроллере и обрабатывать данные с формы. Мы задали url, котоырй ипользуется по умолчанию для обрабоки формы аутентификации по средсвам Spring Security. Spring Security будет ждать объект с формы аутентификации и затем сверять логин и пароль с данными в БД
                .defaultSuccessUrl("/index",true )// Указываем на какой url необходимо направить после успешной аутентификации. Вторым аргументов указывается true чтобы перенаправление шло в любом случае после успешной аутентификации
                .failureUrl("/authentication?error")//Указываем куда необходимо перенаправить пользователя при проваленной аутентификации. А запросе будет передан объект error, который будет проверяться на форме и при наличии данного объекта в запросе выводится сообщение "Неправильный логин и пароль "
                .and()
                .logout().permitAll().logoutUrl("/logout").logoutSuccessUrl("/authentication"); //Производим настройку выхода из личного кабинета. Сначала указывается по какому url будет происходить выход, а потом куда именно будет направленна переадрисация после выхода
        return http.build();
    }
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    protected void configure (AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
       authenticationManagerBuilder.userDetailsService(personDetailsService)
               .passwordEncoder(getPasswordEncode());
    }
}
