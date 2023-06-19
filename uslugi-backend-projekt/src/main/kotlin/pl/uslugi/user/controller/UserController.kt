package pl.uslugi.user.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import pl.uslugi.user.dto.AuthenticationRequest
import pl.uslugi.user.dto.RegistrationRequest
import pl.uslugi.user.dto.SessionObject
import pl.uslugi.user.repository.ServiceRepository
import pl.uslugi.user.repository.RentalRepository
import pl.uslugi.user.repository.UserRepository
import pl.uslugi.user.service.ServiceService
import pl.uslugi.user.service.UserService
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController()
@CrossOrigin()
class UserController(
        private val authenticationManager: AuthenticationManager,
        private val userService: UserService,
        private var userRepository: UserRepository,
        private val serviceService: ServiceService,
        private val serviceRepository: ServiceRepository,
        private val rentalRepository: RentalRepository
) {

    val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/register")
    fun register(@RequestBody registrationRequest: RegistrationRequest): ResponseEntity<SessionObject> {
        // Sprawdź, czy użytkownik o podanej nazwie użytkownika już istnieje
        var sessionResponse = SessionObject(
            "null", "null", false, 1L
        );
        if (userRepository.existsByUsername(registrationRequest.username)) {
            sessionResponse = SessionObject(
                "Username already exists", "null", false, 1L
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(sessionResponse)
        }

        // Zarejestruj nowego użytkownika
        val newUser = userService.registerUser(registrationRequest)
        sessionResponse = SessionObject(
            "User was added", "null", false, 1L
        )
        return ResponseEntity.ok(sessionResponse)
    }

    @PostMapping("/login")
    fun login(@RequestBody authenticationRequest: AuthenticationRequest, servletRequest: HttpServletRequest, response: HttpServletResponse): ResponseEntity<SessionObject> {
        logger.info("Attempting login for user: ${authenticationRequest.email}")

        val authenticationToken = UsernamePasswordAuthenticationToken(authenticationRequest.email, authenticationRequest.password)
        val authentication = null;
        try{
            authenticationManager.authenticate(authenticationToken)
        } catch (exception: Exception){
            val sessionResponse = SessionObject(
                "null", "null", false, 1L
            );
            return ResponseEntity.status(401).body(sessionResponse);
        }

        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = userService.loadUserByUsername(authenticationRequest.email)
        val user = userService.getUserByUsername(userDetails.username)

        // Log user authentication details
        logger.info("User authenticated: ${userDetails.username}")

        // Tworzenie sesji użytkownika i ustawienie pliku cookie
        val session = servletRequest.session
        session.setAttribute("username", userDetails.username)

        val cookie = Cookie("SESSIONID", session.id)
        cookie.path = "/"
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        // Log session creation and cookie details
        logger.info("Session created with SESSIONID: ${session.id}")
        val sessionResponse = SessionObject(user.sessionId,user.userRole,user.canAddService,user
            .id);
        return ResponseEntity.ok(sessionResponse)
    }





}