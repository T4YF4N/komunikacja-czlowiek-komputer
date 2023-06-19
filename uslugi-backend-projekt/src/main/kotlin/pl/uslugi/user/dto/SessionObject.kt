package pl.uslugi.user.dto

data class SessionObject(
        val sessionId: String,
        val userRole: String,
        val canAddService: Boolean,
        val id: Long?
)