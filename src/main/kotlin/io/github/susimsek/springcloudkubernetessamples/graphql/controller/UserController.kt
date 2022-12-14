package io.github.susimsek.springcloudkubernetessamples.graphql.controller

import io.github.susimsek.springcloudkubernetessamples.graphql.DEFAULT_PAGE_NO
import io.github.susimsek.springcloudkubernetessamples.graphql.DEFAULT_SIZE
import io.github.susimsek.springcloudkubernetessamples.graphql.MAX_SIZE
import io.github.susimsek.springcloudkubernetessamples.graphql.input.AddUserInput
import io.github.susimsek.springcloudkubernetessamples.graphql.input.UserFilter
import io.github.susimsek.springcloudkubernetessamples.graphql.input.UserOrder
import io.github.susimsek.springcloudkubernetessamples.graphql.type.UserPayload
import io.github.susimsek.springcloudkubernetessamples.service.UserService
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import javax.validation.Valid


@Controller
class UserController(private val userService: UserService) {
    @MutationMapping
    suspend fun createUser(@Argument @Valid input: AddUserInput): UserPayload {
        return userService.createUser(input)
    }

    @QueryMapping
    suspend fun users(
        @Argument page: Int?,
        @Argument size: Int?,
        @Argument filter: UserFilter?,
        @Argument orders: MutableList<UserOrder>?
    ): List<UserPayload> {
        val pageNo = page ?: DEFAULT_PAGE_NO
        val sizeNo = (size ?: DEFAULT_SIZE).coerceAtMost(MAX_SIZE)
        val sort = orders?.map(UserOrder::toOrder)?.let { Sort.by(it) } ?: Sort.unsorted()
        val pageRequest = PageRequest.of(pageNo, sizeNo, sort)
        return userService.getUsers(pageRequest, filter)
            .toList()
    }

    @QueryMapping
    suspend fun me(): UserPayload {
        return userService.getCurrentUser()
    }

    @SchemaMapping
    suspend fun name(user: UserPayload): String {
        return userService.getName(user)
    }
}
