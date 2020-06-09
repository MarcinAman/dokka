package org.jetbrains.dokka.base.transformers.pages.merger

import org.jetbrains.dokka.model.properties.PropertyContainer
import org.jetbrains.dokka.pages.*

object SameClassNamePageMergerStrategy : PageMergerStrategy {
    override fun tryMerge(pages: List<PageNode>, path: List<String>): List<PageNode> {
        val asClasslike = pages.filterIsInstance<ClasslikePageNode>()
        val sourceSets = asClasslike.flatMap { it.content.sourceSets }.toSet()
        return listOf(ClasslikePageNode(
            name = asClasslike.first().name,
            children = emptyList(),
            content = PlatformHintedContent(
                inner = ContentGroup(
                    dci = DCI(asClasslike.first().dri, ContentKind.Main),
                    children = asClasslike.map {
                        ContentGroup(
                            dci = DCI(it.dri, ContentKind.Classlikes),
                            children = listOf(it.content),
                            sourceSets = it.content.sourceSets,
                            style = it.content.style,
                            extra = it.content.extra
                        )
                    },
                    style = emptySet(),
                    extra = PropertyContainer.empty(),
                    sourceSets = sourceSets,
                ),
                sourceSets = sourceSets,
            ),
            documentable = null,
            dri = asClasslike.first().dri,
            embeddedResources = emptyList(),
        ))
    }
}