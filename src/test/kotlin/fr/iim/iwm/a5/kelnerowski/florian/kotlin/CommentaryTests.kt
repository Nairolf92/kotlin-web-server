package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import org.junit.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CommentaryTests {
    @Test
    fun testCommentaryAdd() {
        val commentaryController = CommentaryControllerImpl(FakeModel())
        val commentaryToAdd = Commentary(null, 42, "commentaire article 42")
        val result = commentaryController.addCommentary(commentaryToAdd)
        assertNull(result)
    }

    @Test
    fun testCommentaryDelete() {
        val commentaryController = CommentaryControllerImpl(FakeModel())
        val userSession = UserSession("admin")

        val result = commentaryController.deleteCommentary(userSession,2)
        val map = mapOf("userSession" to userSession)
        assertTrue(result.template == "deleteArticle.ftl" && result.model == map)
    }
}