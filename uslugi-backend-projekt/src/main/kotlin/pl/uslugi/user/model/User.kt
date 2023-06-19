package pl.uslugi.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        var username: String,

        @Column(nullable = false)
        var firstName: String,

        @Column(nullable = false)
        var lastName: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        val password: String,

        @Column(nullable = false)
        val sessionId: String, // Add the sessionId field

        @Column(nullable = false)
        val userRole: String,

        @Column(nullable = false)
        var canAddService: Boolean,

        @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
        @JsonIgnore
        val rentals: MutableList<Rental> = mutableListOf()
) {
    fun addRental(rental: Rental) {
        rentals.add(rental)
        rental.user = this
    }

    fun removeRental(rental: Rental) {
        rentals.remove(rental)
        rental.user = null
    }
}