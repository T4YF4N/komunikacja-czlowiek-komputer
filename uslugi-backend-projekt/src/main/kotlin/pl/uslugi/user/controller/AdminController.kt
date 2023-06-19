package pl.uslugi.user.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*
import pl.uslugi.user.dto.EditRequest
import pl.uslugi.user.dto.RegistrationRequest
import pl.uslugi.user.dto.SessionObject
import pl.uslugi.user.model.Service
import pl.uslugi.user.model.User
import pl.uslugi.user.repository.RentalRepository
import pl.uslugi.user.repository.ServiceRepository
import pl.uslugi.user.repository.UserRepository
import pl.uslugi.user.service.ServiceService
import pl.uslugi.user.service.UserService
import javax.annotation.security.PermitAll


@RestController()
@CrossOrigin()
class AdminController(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private var userRepository: UserRepository,
    private val serviceService: ServiceService,
    private val serviceRepository: ServiceRepository,
    private val rentalRepository: RentalRepository
) {

    @PostMapping("/admin")
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
        val newUser = userService.registerAdminUser(registrationRequest)
        sessionResponse = SessionObject(
            "User was added", "null", false, 1L
        )
        return ResponseEntity.ok(sessionResponse)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Long):
            ResponseEntity<SessionObject> {
        userRepository.delete(userRepository.getById(id));
        var sessionResponse = SessionObject(
            "User was deleted", "null", false, 1L
        )
        return ResponseEntity.ok(sessionResponse)
    }

    @GetMapping("/getall")
    @PermitAll
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers();
        return ResponseEntity.ok(users)
    }

    @GetMapping("/get/{id}")
    fun getAllUser(@PathVariable id: Long): ResponseEntity<User> {
        val user = userRepository.findById(id);
        return ResponseEntity.ok(user.get())
    }


    @GetMapping("/service/{serviceId}")
    fun getService(@PathVariable serviceId: Long): ResponseEntity<Service> {
        val service = serviceRepository.findById(serviceId).get();
        return ResponseEntity.ok(service)
    }

    @PostMapping("/update/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody editRequest: EditRequest):
            ResponseEntity<User> {
        val user = userService.update(id,editRequest);
        return ResponseEntity.ok(user)
    }





}