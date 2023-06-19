package pl.uslugi.user.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.session.web.http.HeaderHttpSessionIdResolver
import org.springframework.session.web.http.HttpSessionIdResolver
import pl.uslugi.user.repository.UserRepository
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired(required = false)
    private lateinit var userService: org.springframework.security.core.userdetails.UserDetailsService

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userService)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/admin").permitAll()
                .antMatchers("/users/*").permitAll()
                .antMatchers("/services/*").permitAll()
                .antMatchers("/service/{serviceId}").permitAll()
            .antMatchers("/service/edit/{serviceId}").permitAll()
                .antMatchers("/service/create/{sessionId}").permitAll()
                .antMatchers("/service/{serviceId}/rent/{sessionId}").permitAll()
                .antMatchers("/service/{serviceId}/return/{sessionId}").permitAll()
                .antMatchers("/books/{sessionId}").permitAll()
                .antMatchers("/get/{id}").permitAll()
                .antMatchers("/delete/{id}").permitAll()
                .antMatchers("/users/{sessionId}/books/{bookId}/rent").permitAll()
                .antMatchers("/users/{sessionId}/books/{bookId}/return").permitAll()
                .antMatchers("/users/{userId}/rentals").permitAll()
                .antMatchers("/update/{id}").permitAll()
                .antMatchers("/init").permitAll()
                .antMatchers(HttpMethod.POST, "/books/create").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers(HttpMethod.GET, "/books").permitAll() // Wymagaj uwierzytelnienia dla /books
                .antMatchers(HttpMethod.GET, "/getall").permitAll() // Wymagaj uwierzytelnienia dla /books
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .securityContext() // Nowe: Ustaw SecurityContextRepository na HttpSessionSecurityContextRepository
                .securityContextRepository(HttpSessionSecurityContextRepository())
                .and()
                .httpBasic() // Dodaj konfigurację uwierzytelniania HTTP Basic
                .and()
                .exceptionHandling()
                .authenticationEntryPoint { request, response, authException ->
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                }
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/login") // Dodanie wyjątku dla /login w configure(web: WebSecurity)
        web.ignoring().antMatchers("/register")
        web.ignoring().antMatchers("/services")
        web.ignoring().antMatchers("/admin")
        web.ignoring().antMatchers("/delete/{id}")
        web.ignoring().antMatchers("/service/create/{sessionId}")
        web.ignoring().antMatchers("/update/{id}")
        web.ignoring().antMatchers("/get/{id}")
        web.ignoring().antMatchers("/service/{serviceId}")
        web.ignoring().antMatchers("/service/edit/{serviceId}")
    }



    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    override fun userDetailsServiceBean(): UserDetailsService {
        return UserDetailsService { username ->
            val user = userRepository.findByUsername(username)
                    ?: throw UsernameNotFoundException("User not found")

            // Convert your User entity to UserDetails implementation
            org.springframework.security.core.userdetails.User(
                    user.username,
                    user.password,
                    listOf(SimpleGrantedAuthority("ROLE_USER")) // Dodaj użytkownikowi rolę
            )
        }
    }

    @Bean
    fun httpSessionIdResolver(): HttpSessionIdResolver {
        return HeaderHttpSessionIdResolver.xAuthToken()
    }

}
