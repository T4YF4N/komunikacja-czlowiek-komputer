package pl.uslugi.user.service

import pl.uslugi.user.dto.ServiceRequest
import pl.uslugi.user.repository.ServiceRepository
import pl.uslugi.user.repository.UserRepository

@org.springframework.stereotype.Service
class ServiceService(
        private val serviceRepository: ServiceRepository,
        private val userRepository: UserRepository
) {
    fun getAllBooks(): List<pl.uslugi.user.model.Service> {
        return serviceRepository.findAll()
    }


    fun getFiltredAllBooks(selector: String): List<pl.uslugi.user.model.Service> {
        return serviceRepository.findByName(selector)
    }

    fun createService(serviceRequest: ServiceRequest, sessionId: String): pl.uslugi.user.model.Service {
        var user = userRepository.findBySessionId(sessionId)
        val service = pl.uslugi.user.model.Service(
                name = serviceRequest.name,
                user = user,
                price = serviceRequest.price,
                description =  serviceRequest.description,
                lastRentedSessionId = "null"
        )
        return serviceRepository.save(service)
    }

    fun updateService(serviceRequest: ServiceRequest, serviceId: Long): pl.uslugi.user.model.Service{
        val service = serviceRepository.findById(serviceId).get()
        service.name = serviceRequest.name
        service.price = serviceRequest.price
        service.description = serviceRequest.description
        return serviceRepository.save(service)
    }

    fun initBooks() {
//        val service1 = pl.uslugi.user.model.Service(
//                id = 1L,
//                name = "Obcy",
//                price = 100,
//                isAbleToUse = true,
//                user = null
//        )
//        val service2 = pl.uslugi.user.model.Service(
//                id = 2L,
//                name = "Obcy2",
//                price = 100,
//                isAbleToUse = true,
//                user = null
//        )
//        val service3 = pl.uslugi.user.model.Service(
//                id = 3L,
//                name = "Obcy3",
//                price = 100,
//                isAbleToUse = true,
//                user = null
//        )
//        val service4 = pl.uslugi.user.model.Service(
//                id = 4L,
//                name = "Obcy4",
//                price = 100,
//                isAbleToUse = true,
//                user = null
//        )
//        val service5 = pl.uslugi.user.model.Service(
//                id = 5L,
//                name = "Obcy5",
//                price = 100,
//                isAbleToUse = true,
//                user = null
//        )
//        val service6 = pl.uslugi.user.model.Service(
//                id = 6L,
//                name = "Obcy6",
//                price = 100,
//                isAbleToUse = true,
//                user = null
//        )
//        serviceRepository.save(service1)
//        serviceRepository.save(service2)
//        serviceRepository.save(service3)
//        serviceRepository.save(service4)
//        serviceRepository.save(service5)
//        serviceRepository.save(service6)
    }

}