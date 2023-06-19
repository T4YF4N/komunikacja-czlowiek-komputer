package pl.uslugi.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "rentals")
data class Rental(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User? = null,

        @ManyToOne
        @JoinColumn(name = "service_id")
        val service: Service,

        val rentalDate: String,

        var returnDate: LocalDate? = null
)