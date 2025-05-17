package org.example.sqssnslocal.hello


import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@RestController
@RequestMapping("/")
class HelloController(private val helloService: HelloService) {

    @GetMapping("/hello")
    fun sendHelloMessage(
    ): String {
        helloService.hello1()
        return "Hello message sent!"
    }
}
