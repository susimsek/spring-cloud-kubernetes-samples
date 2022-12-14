package io.github.susimsek.springcloudkubernetessamples.graphql.input

import io.github.susimsek.springcloudkubernetessamples.graphql.enumerated.OrderType
import io.github.susimsek.springcloudkubernetessamples.graphql.enumerated.PostOrderField
import org.springframework.data.domain.Sort

data class PostOrder(
    var field: PostOrderField? = null,
    var order: OrderType? = null
) {
    fun toOrder(): Sort.Order {
        val direction: Sort.Direction =
            if (order == null) Sort.DEFAULT_DIRECTION
            else if (order === OrderType.ASC) Sort.Direction.ASC else Sort.Direction.DESC
        return Sort.Order(direction, field!!.name)
    }
}
