package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ModelTests {
    val model = MysqlModel("jdbc:h2:mem:cms;MODE=MYSQL", null, null)

    @Before
    fun initDB() {
        model.connectionPool.use { connection ->
            connection.prepareStatement("""
                DROP TABLE IF EXISTS commentary;
                CREATE TABLE commentary (
                  id int(11) NOT NULL AUTO_INCREMENT,
                  idArticle INT(11),
                  textArticle varchar(255) NOT NULL,
                  PRIMARY KEY (id)
                );
                INSERT INTO commentary VALUES
                  (null, 1, 'Commentaire premier article'),
                  (null, 2, 'Commentaire 2ème article');
                DROP TABLE IF EXISTS articles;
                CREATE TABLE articles (
                  id int(11) NOT NULL AUTO_INCREMENT,
                  title varchar(255) NOT NULL,
                  text text NOT NULL,
                  PRIMARY KEY (id)
                );
                INSERT INTO articles VALUES
                  (1, 'Premier article', 'Lorem ipsum le premier article'),
                  (2, 'Deuxième article', 'Lorem ipsum le 2ème article')"""
            ).use { stmt ->
                stmt.execute()
            }
        }
    }

    @Test
    fun testsArticleInDb() {
        val article = model.getArticle(1)

        assertNotNull(article)
        assertEquals(1, article.id)
        assertEquals("Premier article", article.title)
        assertTrue(article.text!!.startsWith("Lorem ipsum"))
    }

    @Test
    fun testsArticleNotInDb() {
        val article = model.getArticle(3)

        assertNull(article)
    }
}