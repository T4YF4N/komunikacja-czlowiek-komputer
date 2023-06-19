package pl.uslugi.user.dto



data class EditRequest(
        val username: String,
        val password: String,
        var email: String,
        val canAdd: Boolean
)