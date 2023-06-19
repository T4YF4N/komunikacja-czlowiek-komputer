package pl.uslugi.user.dto

data class AuthenticationRequest(
        val email: String,
        val password: String
)
