package fr.xebia.xwhois.tool

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AsciiToolTest {

    val asciiToo = AsciiTool()

    @Test
    fun should_convert_e_accent_to_e() {
        // When
        val text = asciiToo.normalize("éèê")
        // Then
        assertThat(text).isEqualTo("eee")
    }

    @Test
    fun should_convert_a_accent_to_a() {
        // When
        val text = asciiToo.normalize("àâ")
        // Then
        assertThat(text).isEqualTo("aa")
    }

    @Test
    fun should_normalize_underscore() {
        // When
        val text = asciiToo.normalize("_")
        // Then
        assertThat(text).isEqualTo(" ")
    }

    @Test
    fun should_normalize_dash() {
        // When
        val text = asciiToo.normalize("-")
        // Then
        assertThat(text).isEqualTo(" ")
    }
}