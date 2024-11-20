package com.ecwid.apiclient.v3.util

import java.math.RoundingMode
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.random.Random

internal fun randomEmail() = "test+${Random.nextInt()}@example.com"

internal fun randomUrl() = "example${Random.nextInt()}.com"

internal fun randomIp() = "${randomByte()}.${randomByte()}.${randomByte()}.${randomByte()}"

internal fun randomBoolean() = Random.nextBoolean()

internal fun randomByte() = Random.nextBytes(1)[0] + 128

internal fun randomId() = randomInt(10000, 1000000)

internal fun randomLongId() = randomLong(10000L, 1000000L)

internal fun <T> randomIndex(collection: Collection<T>) = randomInt(0, collection.size - 1)

internal fun randomPrice() = Random
	.nextDouble(20.0, 1000.0)
	.roundDouble()

internal fun randomWeight() = Random
	.nextDouble(1.0, 100.0)
	.roundDouble()

internal fun randomDimension(): Double = Random
	.nextDouble(1.0, 100.0)
	.roundDouble()

internal fun randomVolume() = Random
	.nextDouble(1.0, 100.0)
	.roundDouble()

internal fun randomModifier() = Random
	.nextDouble(-20.0, 20.0)
	.roundDouble()

internal fun randomDate(): Date {
	val instant = Instant.now()
		.minusMillis(Random.nextInt().toLong())
		.truncatedTo(ChronoUnit.SECONDS)
	return Date.from(instant)
}

internal fun randomDateFrom(date: Date): Date {
	val instant = date.toInstant()
		.plusSeconds(randomInt(1, 100000).toLong())
		.truncatedTo(ChronoUnit.SECONDS)
	return Date.from(instant)
}

internal fun randomAlphanumeric(size: Int): String {
	val characters = ('a'..'z') + ('A'..'Z') + ('0'..'9')
	return (1..size)
		.map { Random.nextInt(0, characters.size) }
		.map(characters::get)
		.joinToString("")
}

internal inline fun <reified E : Enum<E>> randomEnumValue(vararg exclude: E): E {
	return E::class.java.enumConstants
		.toSortedSet()
		.minus(exclude)
		.shuffled()
		.first()
}

internal fun <V> randomOf(vararg options: V): V {
	return options
		.toList()
		.shuffled()
		.first()
}

internal fun randomFileName(prefix: String, extension: String) = "$prefix-${Random.nextInt()}.$extension"

internal fun randomInt(min: Int, max: Int) = Random.nextInt(min, max)

internal fun randomLong(min: Long, max: Long) = Random.nextLong(min, max)

internal fun randomDouble(min: Double, max: Double) = Random.nextDouble(min, max)

private fun Double.roundDouble(): Double {
	return toBigDecimal()
		.setScale(2, RoundingMode.UP)
		.toDouble()
}
