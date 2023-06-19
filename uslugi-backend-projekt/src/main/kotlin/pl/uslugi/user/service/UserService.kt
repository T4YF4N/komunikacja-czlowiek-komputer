package pl.uslugi.user.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import pl.uslugi.user.dto.EditRequest
import pl.uslugi.user.model.User
import pl.uslugi.user.dto.RegistrationRequest
import pl.uslugi.user.repository.UserRepository
import java.util.*

@Service
class UserService(
        private val userRepository: UserRepository
) : org.springframework.security.core.userdetails.UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("User not found")

        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))

        val userDetails = org.springframework.security.core.userdetails.User(
                user.username,
                user.password,
                authorities
        )

        return userDetails
    }

    fun registerUser(registrationRequest: RegistrationRequest): User {
        val passwordEncoder = BCryptPasswordEncoder()
        val encodedPassword = passwordEncoder.encode(registrationRequest.password)

        val sessionId = generateSessionId() // Generate a unique session ID

        val user = User(
                username = registrationRequest.username,
                password = encodedPassword,
                email = registrationRequest.email,
                sessionId = sessionId,
                userRole = "user",
                canAddService = false,
                firstName = registrationRequest.firstName,
                lastName =  registrationRequest.lastName
        )

        return userRepository.save(user)
    }

    fun update(id: Long, editRequest: EditRequest): User {
        val user = userRepository.findById(id).get();
        user.username = editRequest.username;
        user.email = editRequest.email;
        user.canAddService = editRequest.canAdd;
        return userRepository.save(user);
    }

    fun registerAdminUser(registrationRequest: RegistrationRequest): User {
        val passwordEncoder = BCryptPasswordEncoder()
        val encodedPassword = passwordEncoder.encode(registrationRequest.password)

        val sessionId = generateSessionId() // Generate a unique session ID

        val user = User(
            username = registrationRequest.username,
            password = encodedPassword,
            email = registrationRequest.email,
            sessionId = sessionId,
            userRole = "admin",
            canAddService = false,
            firstName = "admin",
            lastName = "admin"
        )

        return userRepository.save(user)
    }

    fun generateSessionId(): String {
        return UUID.randomUUID().toString()
    }
    fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }



}