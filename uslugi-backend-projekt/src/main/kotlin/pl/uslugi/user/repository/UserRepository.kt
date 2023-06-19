package pl.uslugi.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.uslugi.user.model.User


@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User
    fun findByEmail(email : String): User
    fun existsByUsername(username: String): Boolean
    fun findBySessionId(sessionId: String): User
}