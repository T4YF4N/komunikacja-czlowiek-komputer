package pl.uslugi.user.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.uslugi.user.model.Rental
import pl.uslugi.user.repository.RentalRepository
import pl.uslugi.user.repository.ServiceRepository
import pl.uslugi.user.repository.UserRepository
import pl.uslugi.user.service.ServiceService
import pl.uslugi.user.service.UserService
import javax.annotation.security.PermitAll
import javax.servlet.http.HttpServletRequest

@RestController()
@CrossOrigin()
class RentalController(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private var userRepository: UserRepository,
    private val serviceService: ServiceService,
    private val serviceRepository: ServiceRepository,
    private val rentalRepository: RentalRepository
) {


    @GetMapping("/users/{sessionId}/rentals")
    @PermitAll
    fun getUserRentals(@PathVariable sessionId: String, request: HttpServletRequest): ResponseEntity<List<Rental>> {

        // Wyszukaj użytkownika na podstawie identyfikatora sesji
        val user = userRepository.findBySessionId(sessionId)
            ?: throw NoSuchElementException("User not found")

        // Pobierz wypożyczenia użytkownika
        val rentals = user.rentals

        return ResponseEntity.ok(rentals)
    }

    @GetMapping("/users/rentals")
    @PermitAll
    fun getAllRentals(request: HttpServletRequest):
            ResponseEntity<List<Rental>> {
        val rentals = rentalRepository.findAll()
        return ResponseEntity.ok(rentals)
    }
}