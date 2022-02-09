package com.github.marciovmartins

import org.springframework.data.repository.CrudRepository

@Suppress("unused")
interface GameDayRepository : CrudRepository<GameDay, Long>