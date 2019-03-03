package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.*
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.SessionStorageMemory
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import kotlinx.html.*
import kotlinx.html.stream.createHTML

data class MySession(val user: String)

class App

fun Application.cmsApp(
     articleController: ArticleController,
     articleListController: ArticleListController
) {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(App::class.java.classLoader, "templates")
    }
    // This adds automatically Date and Server headers to each response, and would allow you to configure
    // additional headers served to each response.
    install(DefaultHeaders)
    // This uses use the logger to log every call (request/response)
    install(CallLogging)
    install(Sessions) {
        cookie<MySession>("CMS_SESSION", SessionStorageMemory())
    }
    install(Authentication) {
        form(name = "admin") {
            userParamName = "user"
            passwordParamName = "password"
            challenge = FormAuthChallenge.Unauthorized
            validate { credentials -> /*...*/ }
        }
    }

    routing {
        get("/") {
            val content = articleListController.startFM()
            call.respond(content)
        }

        get("/articles/{articleId}") {
            val articleId = call.parameters["articleId"]!!.toInt()
            val content = articleController.startFM(articleId)
            call.respond(content)
        }

        route("/login") {
            authentication {
                form {
                    validate { credentials ->
                        if (credentials.name == credentials.password) {
                            println(credentials)
                            UserIdPrincipal(credentials.name)
                        } else {
                            println("toto")
                            null
                        }
                    }
                }
            }

            handle {
                val principal = call.authentication.principal<UserIdPrincipal>()
                println(principal)
                if (principal != null) {
                    call.respondText("Hello, ${principal.name}")
                } else {
                    val html = createHTML().html {
                        body {
                            form(action = "/login", encType = FormEncType.applicationXWwwFormUrlEncoded, method = FormMethod.post) {
                                p {
                                    +"user:"
                                    textInput(name = "user") {
                                        value = principal?.name ?: ""
                                    }
                                }

                                p {
                                    +"password:"
                                    passwordInput(name = "pass")
                                }

                                p {
                                    submitInput() { value = "Login" }
                                }
                            }
                        }
                    }
                    call.respondText(html, ContentType.Text.Html)
                }
            }
        }
    }
}

fun main () {

    val model = MysqlModel("jdbc:mysql://localhost:3306/CMS", "root", "root")
    val articleListController = ArticleListControllerImpl(model)
    val articleController = ArticleControllerImpl(model)

    embeddedServer(Netty, 8090) {cmsApp(articleController, articleListController)}.start(true)
}