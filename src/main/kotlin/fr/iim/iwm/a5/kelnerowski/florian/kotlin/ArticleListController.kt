package fr.iim.iwm.a5.kelnerowski.florian.kotlin

import io.ktor.freemarker.FreeMarkerContent

interface ArticleListController {
        fun startFM(userSession: UserSession?): FreeMarkerContent
}