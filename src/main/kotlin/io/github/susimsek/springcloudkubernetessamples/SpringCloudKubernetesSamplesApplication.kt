package io.github.susimsek.springcloudkubernetessamples

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
class SpringGraphqlSamplesApplication

fun main(args: Array<String>) {
    runApplication<SpringGraphqlSamplesApplication>(*args)
}
