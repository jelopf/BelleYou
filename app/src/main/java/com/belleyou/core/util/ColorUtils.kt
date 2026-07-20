package com.belleyou.core.util

object ColorUtils {
    fun getColorHex(colorName: String): String {
        return when (colorName.uppercase()) {
            "BELYY", "PLOMBIR" -> "#F5F5F5"
            "CHERNYY" -> "#000000"
            "TEMNO_BEZHEVYY", "TEMNO" -> "#BDB76B"
            "BEZHEVYY" -> "#F5F5DC"
            "KORALLOVYY" -> "#FF7F50"
            "LAVANDOVYY" -> "#E6E6FA"
            "GOLUBOY" -> "#ADD8E6"
            "SERYY" -> "#808080"
            "BORD" -> "#800000"
            "MALINA" -> "#C71585"
            "SHOKOLAD" -> "#8B4513"
            "HAKI" -> "#808000"
            else -> "#CCCCCC" // Default gray
        }
    }

    fun extractColorName(article: String): String {
        val parts = article.split("_")
        return parts.lastOrNull() ?: ""
    }
}