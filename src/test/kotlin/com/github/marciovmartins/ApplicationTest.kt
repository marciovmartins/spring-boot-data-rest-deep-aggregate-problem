package com.github.marciovmartins

import java.net.URI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.client.Traverson
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTest {
    @LocalServerPort
    var port = 0

    lateinit var webTestClient: WebTestClient

    lateinit var traverson: Traverson

    @BeforeEach
    fun setUpBase() {
        val baseurl = "http://localhost:$port"
        webTestClient = WebTestClient.bindToServer()
            .baseUrl(baseurl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
            .build()
        traverson = Traverson(URI.create(baseurl), MediaTypes.HAL_JSON)
    }

    @Test
    fun `create and retrieve a game day`() {
        val gameDayToCreate = """
            {
              "date": "2022-02-08",
              "quote": null,
              "author": null,
              "description": null,
              "matches": [
                {
                  "order": 1,
                  "matchPlayers": [
                    {
                      "team": "A",
                      "nickname": "Ajax",
                      "goalsInFavor": 5,
                      "goalsAgainst": 1,
                      "yellowCards": 1,
                      "blueCards": 2,
                      "redCards": 1
                    },
                    {
                      "team": "B",
                      "nickname": "Zatanna",
                      "goalsInFavor": 2,
                      "goalsAgainst": 1,
                      "yellowCards": 0,
                      "blueCards": 2,
                      "redCards": 0
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val gameDayLocationUrl = webTestClient.post()
            .uri(traverson.follow("gameDays").asLink().href)
            .bodyValue(gameDayToCreate)
            .exchange().expectStatus().isCreated
            .returnResult(Unit::class.java)
            .responseHeaders.location.toString()

        webTestClient.get()
            .uri(gameDayLocationUrl)
            .exchange().expectStatus().isOk

    }

    @Test
    fun `update and retrieve a game day with one match`() {
        val gameDayToCreate = """
            {
              "date": "2022-02-08",
              "quote": null,
              "author": null,
              "description": null,
              "matches": [
                {
                  "order": 1,
                  "matchPlayers": [
                    {
                      "team": "A",
                      "nickname": "Ajax",
                      "goalsInFavor": 5,
                      "goalsAgainst": 1,
                      "yellowCards": 1,
                      "blueCards": 2,
                      "redCards": 1
                    },
                    {
                      "team": "B",
                      "nickname": "Zatanna",
                      "goalsInFavor": 2,
                      "goalsAgainst": 1,
                      "yellowCards": 0,
                      "blueCards": 2,
                      "redCards": 0
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val gameDayToUpdate = """
            {
              "date": "2022-02-08",
              "quote": null,
              "author": null,
              "description": null,
              "matches": [
                {
                  "order": 1,
                  "matchPlayers": [
                    {
                      "team": "A",
                      "nickname": "Ajax",
                      "goalsInFavor": 4,
                      "goalsAgainst": 1,
                      "yellowCards": 1,
                      "blueCards": 2,
                      "redCards": 1
                    },
                    {
                      "team": "B",
                      "nickname": "Zatanna",
                      "goalsInFavor": 2,
                      "goalsAgainst": 1,
                      "yellowCards": 0,
                      "blueCards": 2,
                      "redCards": 0
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val gameDayLocationUrl = webTestClient.post()
            .uri(traverson.follow("gameDays").asLink().href)
            .bodyValue(gameDayToCreate)
            .exchange().expectStatus().isCreated
            .returnResult(Unit::class.java)
            .responseHeaders.location.toString()

        webTestClient.put()
            .uri(gameDayLocationUrl)
            .bodyValue(gameDayToUpdate)
            .exchange().expectStatus().isOk
        webTestClient.get()
            .uri(gameDayLocationUrl)
            .exchange().expectStatus().isOk

    }

    @Test
    fun `update and retrieve a game day with two matches`() {
        val gameDayToCreate = """
            {
              "date": "2022-02-08",
              "quote": null,
              "author": null,
              "description": null,
              "matches": [
                {
                  "order": 1,
                  "matchPlayers": [
                    {
                      "team": "A",
                      "nickname": "Ajax",
                      "goalsInFavor": 5,
                      "goalsAgainst": 1,
                      "yellowCards": 1,
                      "blueCards": 2,
                      "redCards": 1
                    },
                    {
                      "team": "B",
                      "nickname": "Zatanna",
                      "goalsInFavor": 2,
                      "goalsAgainst": 1,
                      "yellowCards": 0,
                      "blueCards": 2,
                      "redCards": 0
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val gameDayToUpdate = """
            {
              "date": "2022-02-08",
              "quote": null,
              "author": null,
              "description": null,
              "matches": [
                {
                  "order": 1,
                  "matchPlayers": [
                    {
                      "team": "A",
                      "nickname": "Ajax",
                      "goalsInFavor": 5,
                      "goalsAgainst": 1,
                      "yellowCards": 1,
                      "blueCards": 2,
                      "redCards": 1
                    },
                    {
                      "team": "B",
                      "nickname": "Zatanna",
                      "goalsInFavor": 2,
                      "goalsAgainst": 1,
                      "yellowCards": 0,
                      "blueCards": 2,
                      "redCards": 0
                    }
                  ]
                },
                {
                  "order": 2,
                  "matchPlayers": [
                    {
                      "team": "A",
                      "nickname": "Red Bat Skull",
                      "goalsInFavor": 2,
                      "goalsAgainst": 0,
                      "yellowCards": 1,
                      "blueCards": 1,
                      "redCards": 1
                    },
                    {
                      "team": "B",
                      "nickname": "The Pyro Strange",
                      "goalsInFavor": 3,
                      "goalsAgainst": 1,
                      "yellowCards": 2,
                      "blueCards": 1,
                      "redCards": 1
                    }
                  ]
                }
              ]
            }
        """.trimIndent()

        val gameDayLocationUrl = webTestClient.post()
            .uri(traverson.follow("gameDays").asLink().href)
            .bodyValue(gameDayToCreate)
            .exchange().expectStatus().isCreated
            .returnResult(Unit::class.java)
            .responseHeaders.location.toString()

        webTestClient.put()
            .uri(gameDayLocationUrl)
            .bodyValue(gameDayToUpdate)
            .exchange().expectStatus().isOk
        webTestClient.get()
            .uri(gameDayLocationUrl)
            .exchange().expectStatus().isOk

    }

}