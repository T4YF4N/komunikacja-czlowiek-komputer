package pl.uslugi.user.service


import pl.uslugi.user.model.Rental
import pl.uslugi.user.model.User
import pl.uslugi.user.repository.RentalRepository
import java.time.LocalDate

@org.springframework.stereotype.Service
class RentalService(
        private val rentalRepository: RentalRepository
) {


    fun returnBook(rental: Rental) {
        rental.returnDate = LocalDate.now()
        rentalRepository.save(rental)
    }

    fun getUserRentals(user: User): List<Rental> {
        return rentalRepository.findByUser(user)
    }
}