package pl.uslugi.user.dto



data class RegistrationRequest(
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        var email: String,
        val canAdd: Boolean
)