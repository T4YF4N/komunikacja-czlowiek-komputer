package pl.uslugi.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pl.uslugi.user.model.Service

@Repository
interface ServiceRepository : JpaRepository<Service, Long>{

    fun findByName(name: String): List<Service>
}