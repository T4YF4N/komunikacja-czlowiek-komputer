package pl.uslugi.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.uslugi.user.model.Rental
import pl.uslugi.user.model.User

@Repository
interface RentalRepository : JpaRepository<Rental, Long> {
    fun findByUser(user: User): List<Rental>
}