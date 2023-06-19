package pl.uslugi.user.controller

import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*
import pl.uslugi.user.dto.RentalRequest
import pl.uslugi.user.dto.ServiceRequest
import pl.uslugi.user.dto.SessionObject
import pl.uslugi.user.model.Rental
import pl.uslugi.user.model.Service
import pl.uslugi.user.repository.RentalRepository
import pl.uslugi.user.repository.ServiceRepository
import pl.uslugi.user.repository.UserRepository
import pl.uslugi.user.service.ServiceService
import pl.uslugi.user.service.UserService
import java.time.LocalDate
import javax.annotation.security.PermitAll


@RestController()
@CrossOrigin()
class ServiceController(
    private var userRepository: UserRepository,
    private val serviceService: ServiceService,
    private val serviceRepository: ServiceRepository,
    private val rentalRepository: RentalRepository
) {

    @PostMapping("/service/create/{sessionId}")
    fun createBook(@PathVariable sessionId: String, @RequestBody serviceRequest: ServiceRequest): ResponseEntity<SessionObject> {
        val newBook = serviceService.createService(serviceRequest,sessionId)
        var sessionResponse = SessionObject(
            "null", "null", false, 1L
        );
        return ResponseEntity.ok(sessionResponse)
    }

    @PostMapping("/service/edit/{serviceId}")
    fun editService(@PathVariable serviceId: Long, @RequestBody serviceRequest: ServiceRequest):
            ResponseEntity<Service> {
        val service = serviceService.updateService(serviceRequest,serviceId)
        var sessionResponse = SessionObject(
            "null", "null", false, 1L
        );
        return ResponseEntity.ok(service)
    }

    @DeleteMapping("/service/{serviceId}")
    fun deleteService(@PathVariable serviceId: Long):
            ResponseEntity<SessionObject> {
        serviceRepository.delete(serviceRepository.findById(serviceId).get())
        var sessionResponse = SessionObject(
            "null", "null", false, 1L
        );
        return ResponseEntity.ok(sessionResponse)
    }

    @GetMapping("/services")
    fun getAllBooks(): ResponseEntity<List<Service>> {
        val books = serviceService.getAllBooks()
        return ResponseEntity.ok(books)
    }

    @GetMapping("/services/{selector}")
    fun getAllSelector( @PathVariable selector: String): ResponseEntity<List<Service>> {
        var books = serviceService.getFiltredAllBooks(selector)
        if(selector.equals("Wszystkie")){
            books = serviceService.getAllBooks()
        }
        return ResponseEntity.ok(books)
    }

    @PostMapping("/service/{serviceId}/rent/{sessionId}")
    @PermitAll
    fun rentService(@PathVariable sessionId: String, @PathVariable serviceId: Long,
                    @RequestBody rentalRequest : RentalRequest
    ):
            ResponseEntity<SessionObject> {
        println(sessionId)
        val user = userRepository.findBySessionId(sessionId)


        val service = serviceRepository.findById(serviceId)
            .orElseThrow { NoSuchElementException("Service not found") }

        val rental = Rental(user = user, service = service, rentalDate = rentalRequest.date)
        user.addRental(rental)

        service.lastRentedSessionId=sessionId;

        userRepository.save(user)

        serviceRepository.save(service)

        var sessionResponse = SessionObject(
            "null", "null", false, user
                .id
        );
        return ResponseEntity.ok(sessionResponse)
    }

    @DeleteMapping("/service/{serviceId}/return/{sessionId}")
    @PermitAll
    fun returnBook(@PathVariable sessionId: String, @PathVariable serviceId: Long): ResponseEntity<SessionObject> {
        val user = userRepository.findBySessionId(sessionId)

        val rental = user.rentals.find { it.service.id == serviceId }
            ?: throw NoSuchElementException("Rental not found")


        val service = serviceRepository.findById(serviceId)
            .orElseThrow { NoSuchElementException("Service not found") }

        service.lastRentedSessionId = "null";
        rental.returnDate = LocalDate.now()
        rentalRepository.save(rental)
        userRepository.save(user)
        serviceRepository.save(service)

        var sessionResponse = SessionObject(
            "null", "null", false, user
                .id
        );
        return ResponseEntity.ok(sessionResponse)
    }

}