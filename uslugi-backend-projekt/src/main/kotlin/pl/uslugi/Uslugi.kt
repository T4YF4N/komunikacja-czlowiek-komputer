package pl.uslugi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["pl.uslugi.user"])
class Uslugi

fun main(args: Array<String>) {
    runApplication<Uslugi>(*args)
}


