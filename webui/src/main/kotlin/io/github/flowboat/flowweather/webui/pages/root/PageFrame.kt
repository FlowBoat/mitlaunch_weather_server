package io.github.flowboat.flowweather.webui.pages.root

import io.github.flowboat.flowweather.webui.pages.base.BasePage
import io.github.flowboat.flowweather.webui.pages.base.PageGenerator
import io.github.flowboat.flowweather.webui.pages.reportList.ReportList
import xyz.nulldev.kdom.api.Component
import xyz.nulldev.kdom.api.EmptyComponent

class PageFrame: Component() {
    private val pageStack = mutableListOf<BasePage>(
            ReportList(this)
    )

    private val currentContent = field(pageStack.first())
    private val backBtnField = field<Component>(EmptyComponent())
    private val title = field(pageStack.first().name)

    fun setCurrentContent(newContent: BasePage) {
        pageStack.add(newContent)

        backBtnField(backBtn)
        currentContent(newContent)
        title(newContent.name)
    }

    //language=html
    override fun dom() = """
<div>
    <header class="mdc-toolbar mdc-toolbar--fixed">
        <div class="mdc-toolbar__row">
            <section class="mdc-toolbar__section mdc-toolbar__section--align-start">
                $backBtnField
                <span class="mdc-toolbar__title catalog-title">$title</span>
            </section>
            <section class="mdc-toolbar__section mdc-toolbar__section--align-end" role="toolbar">
            </section>
        </div>
    </header>
    <main class="mdc-toolbar-fixed-adjust">$currentContent</main>
</div>
        """.toDom()

    val backBtn = Component.from {
        val btn = htmlElement()
        onAttach = {
            btn().onclick = {
                //Pop last from stack
                pageStack.removeAt(pageStack.lastIndex)
                //Get last
                val last = pageStack.last()

                if(pageStack.size <= 1)
                    backBtnField(EmptyComponent())

                currentContent(last)
                title(last.name)
            }
        }
        //language=html
        """
<span kref="$btn" class="mdc-toolbar__icon--menu"><i class="material-icons">&#xE5C4;</i></span>
            """.toDom()
    }
}