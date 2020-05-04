package org.jetbrains.dokka.base.signatures

import org.jetbrains.dokka.model.AdditionalModifiers
import org.jetbrains.dokka.model.Documentable
import org.jetbrains.dokka.model.properties.WithExtraProperties
import org.jetbrains.dokka.pages.ContentNode

interface SignatureProvider {
    fun signature(documentable: Documentable): ContentNode

    fun <T : Documentable> WithExtraProperties<T>.additionModifiers(): String =
        this.extra[AdditionalModifiers]?.content?.joinToString("") { it.name.toLowerCase() + " " } ?: ""
}