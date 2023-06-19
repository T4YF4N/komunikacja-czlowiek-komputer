package pl.uslugi.user.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "service")
data class Service(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false)
        var name: String,

        @Column(nullable = false)
        var description: String,

        @Column(nullable = false)
        var lastRentedSessionId: String,

        @ManyToOne
        @JoinColumn(name = "user_id")
        val user: User,

        @Column(nullable = false)
        var price: Int,

)