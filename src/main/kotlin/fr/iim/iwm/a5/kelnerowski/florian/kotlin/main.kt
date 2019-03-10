package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
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
import java.io.File

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
        cookie<UserSession>(
            "CMS_SESSION",
            directorySessionStorage(File(".sessions"), cached = true)
        ) {
            cookie.path = "/" // Specify cookie's path '/' so it can be used in the whole site
        }
    }

    routing {
        get("/") {
            val userSession = call.sessions.get<UserSession>() // Gets a session of this type or null if not available
            val content = articleListController.startFM(userSession)
            call.respond(content)
        }

        get("/articles/{idArticle}") {
            val userSession = call.sessions.get<UserSession>() // Gets a session of this type or null if not available
            val idArticle = call.parameters["idArticle"]!!.toInt()
            val content = articleController.startFM(userSession ,idArticle)
            call.respond(content)
        }

        post("/addArticleCommentary") {
            val post = call.receiveOrNull() ?: Parameters.Empty
            val idArticle = post["idArticle"]!!.toInt()
            val textArticle = post["commentary"]!!
            val commentary = Commentary(null, idArticle, textArticle)
            commentaryController.addCommentary(commentary)
            call.respondRedirect("/articles/$idArticle")
        }

        get("/commentary/delete/{idCommentary}") {
            val userSession = call.sessions.get<UserSession>() // Gets a session of this type or null if not available
            val idCommentary = call.parameters["idCommentary"]!!.toInt()
            val content = commentaryController.deleteCommentary(userSession ,idCommentary)
            call.respond(content)
        }
        
        get("/login") {
            val userSession = call.sessions.get<UserSession>() // Gets a session of this type or null if not available
            val message = call.parameters["message"] ?: ""
            val map = mapOf("userSession" to userSession, "message" to message)
            call.respond(FreeMarkerContent("login.ftl", map))
        }

        post("/login") {
            val userSession = call.sessions.get<UserSession>() // Gets a session of this type or null if not available
            val post = call.receiveOrNull() ?: Parameters.Empty
            val username = post["username"]
            val password = post["password"]

            if (username != null && username.isNotBlank() && username == password) {
                call.sessions.set(UserSession(username))
                call.respondRedirect("/")
            } else {
                val map = mapOf("userSession" to userSession, "wrongLogin" to "wrongLogin")
                call.respond(FreeMarkerContent("login.ftl", map))
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