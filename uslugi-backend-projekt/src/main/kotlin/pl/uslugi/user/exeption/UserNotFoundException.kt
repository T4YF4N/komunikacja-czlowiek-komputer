package pl.uslugi.user.exeption

import org.springframework.http.HttpStatus

class UserNotFoundException(val statusCode: HttpStatus, val reason: String) : Exception()