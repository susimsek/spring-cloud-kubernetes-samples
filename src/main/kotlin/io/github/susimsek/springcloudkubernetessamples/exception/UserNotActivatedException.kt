package io.github.susimsek.springcloudkubernetessamples.exception

import org.springframework.security.core.AuthenticationException

class UserNotActivatedException(message: String, t: Throwable? = null) : AuthenticationException(message, t) {
    companion object {
        private const val serialVersionUID = 1L
    }
}
