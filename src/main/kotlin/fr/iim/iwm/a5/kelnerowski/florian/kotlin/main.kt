package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.sessions.*
import kotlinx.html.*
import kotlinx.html.stream.createHTML

class App

fun Application.cmsApp(
    articleController: ArticleController,
    articleListController: ArticleListController,
    commentaryController: CommentaryControllerImpl
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
        cookie<UserSession>("CMS_SESSION", SessionStorageMemory())
    }

    routing {
        get("/") {
            val userSession = call.sessions.get<UserSession>() // Gets a session of this type or null if not available
            val content = articleListController.startFM(userSession = userSession)
            call.respond(content)
        }

        get("/articles/{articleId}") {
            val userSession = call.sessions.get<UserSession>() // Gets a session of this type or null if not available
            val articleId = call.parameters["articleId"]!!.toInt()
            val content = articleController.startFM(userSession, articleId)
            call.respond(content)
        }

        post("/addArticleCommentary") {
            val post = call.receiveOrNull() ?: Parameters.Empty
            println(post)
            val idArticle = post["idArticle"]!!.toInt()
            val textArticle = post["commentary"]!!
            val commentary = Commentary(null, idArticle, textArticle)
            commentaryController.checkCommentary(commentary)
            call.respondRedirect("/articles/$idArticle")
        }

        get("/login") {
            val html = createHTML().html {
                body {
                    val message = call.parameters["message"] ?: ""
                    if (message.isNotEmpty()) {
                        div {
                            +message
                        }
                    }
                    p {
                        +"Note: Utiliser le meme utilisateur et mot de passe pour cette démo"
                    }
                    form(
                        action = "/login",
                        encType = FormEncType.applicationXWwwFormUrlEncoded,
                        method = FormMethod.post
                    ) {
                        div {
                            +"Pseudo:"
                            textInput(name = "username") { }
                        }
                        div {
                            +"Mot de passe:"
                            passwordInput(name = "password") { }
                        }
                        div {
                            submitInput()
                        }
                    }
                }
            }
            call.respondText(html, ContentType.Text.Html)
        }
        post("/login") {
            val post = call.receiveOrNull() ?: Parameters.Empty
            val username = post["username"]
            val password = post["password"]

            if (username != null && username.isNotBlank() && username == "admin" && password == "admin") {
                call.sessions.set(UserSession(username))
                call.respondRedirect("/")
            } else {
                val html = createHTML().html {
                    body {
                        +"Identifiants invalides. "
                        a(href = "/login") { +"Réesayer?" }
                    }
                }
                call.respondText(html, ContentType.Text.Html)
            }
        }

        get("/logout") {
            call.sessions.clear<UserSession>() // Clears the session of this type
            call.respondRedirect("/")
        }
    }
}

fun main () {

    val model = MysqlModel("jdbc:mysql://localhost:3306/CMS", "root", "root")
    val articleListController = ArticleListControllerImpl(model)
    val articleController = ArticleControllerImpl(model)
    val commentaryController = CommentaryControllerImpl(model)

    embeddedServer(Netty, 8090) {cmsApp(articleController, articleListController, commentaryController)}.start(true)
}