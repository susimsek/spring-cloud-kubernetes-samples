package io.github.susimsek.springcloudkubernetessamples.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import graphql.scalars.ExtendedScalars
import graphql.schema.GraphQLScalarType
import io.github.susimsek.springcloudkubernetessamples.graphql.scalar.GraphQlDateTimeProperties
import io.github.susimsek.springcloudkubernetessamples.graphql.scalar.ScalarUtil
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GraphQlDateTimeProperties::class)
class GraphqlDateTimeConfig {

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            builder.featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
            builder.modules(JavaTimeModule())
        }
    }

    @Bean
    fun graphQlOffsetDateTimeScalar(configurationProperties: GraphQlDateTimeProperties): GraphQLScalarType {
        return ScalarUtil.offsetDateTimeScalar(
            configurationProperties.offsetDateTime.scalarName,
            configurationProperties.offsetDateTime.format
        )
    }

    @Bean
    fun graphQlLocalDateScalar(configurationProperties: GraphQlDateTimeProperties): GraphQLScalarType {
        return ScalarUtil.localDateScalar(
            configurationProperties.localDate.scalarName,
            configurationProperties.localDate.format
        )
    }

    @Bean
    fun graphQlLocalDateTimeScalar(configurationProperties: GraphQlDateTimeProperties): GraphQLScalarType {
        return ScalarUtil.localDateTimeScalar(
            configurationProperties.localDateTime.scalarName,
            configurationProperties.localDateTime.format
        )
    }

    @Bean
    fun graphQLObjectScalar(): GraphQLScalarType {
        return ExtendedScalars.Object
    }

    @Bean
    fun graphQLBigDecimalScalar(): GraphQLScalarType {
        return ExtendedScalars.GraphQLBigDecimal
    }

    @Bean
    fun graphQLPositiveIntScalar(): GraphQLScalarType {
        return ExtendedScalars.PositiveInt
    }

    @Bean
    fun graphQLUuidScalar(): GraphQLScalarType {
        return ExtendedScalars.UUID
    }

    @Bean
    @SuppressWarnings("LongParameterList")
    fun graphqlDateTimeConfigurer(
        graphQlOffsetDateTimeScalar: GraphQLScalarType,
        graphQlLocalDateTimeScalar: GraphQLScalarType,
        graphQlLocalDateScalar: GraphQLScalarType,
        graphQLBigDecimalScalar: GraphQLScalarType,
        graphQLPositiveIntScalar: GraphQLScalarType,
        graphQLUuidScalar: GraphQLScalarType,
        graphQLObjectScalar: GraphQLScalarType
    ): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { builder ->
            builder.scalar(graphQlOffsetDateTimeScalar)
            builder.scalar(graphQlLocalDateTimeScalar)
            builder.scalar(graphQlLocalDateScalar)
            builder.scalar(graphQLBigDecimalScalar)
            builder.scalar(graphQLBigDecimalScalar)
            builder.scalar(graphQLPositiveIntScalar)
            builder.scalar(graphQLUuidScalar)
            builder.scalar(graphQLObjectScalar)
        }
    }
}
