package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.http.HttpStatusCode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ArticleTests {
    @Test
    fun testArticleFound() {
        val model = mock<Model> {
            on  { getArticle(42) } doReturn Article(42, "super titre", "text text text")
        }

        val articleController = ArticleControllerImpl(model)

        val result = articleController.startFM(42, articleId)
        assertTrue(result is FreeMarkerContent)
    }

    @Test
    fun testArticleNotFound() {
        val articleController = ArticleControllerImpl(FakeModel())

        val result = articleController.startFM(55, articleId)
        assertEquals(HttpStatusCode.NotFound, result)
    }
}