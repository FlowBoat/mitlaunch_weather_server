package io.github.flowboat.flowweather.webui.pages.root

import io.github.flowboat.flowweather.webui.pages.base.BasePage
import io.github.flowboat.flowweather.webui.pages.base.PageGenerator
import io.github.flowboat.flowweather.webui.pages.reportList.ReportList
import xyz.nulldev.kdom.api.Component
import xyz.nulldev.kdom.api.EmptyComponent

class PageFrame: Component() {
    private val pageStack = mutableListOf<LoadedPage>(
            LoadedPage(ReportList(), { ReportList() })
    )

    private val currentContent = field<BasePage>(pageStack.first().page)
    private val backBtnField = field<Component>(EmptyComponent)
    private val title = field(pageStack.first().page.name)

    fun setCurrentContent(newContent: PageGenerator) {
        val page = LoadedPage(newContent(), newContent)
        pageStack.add(page)

        backBtnField(backBtn)
        currentContent(page.page)
        title(page.page.name)
    }

    override suspend fun onCompile() {
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
                    backBtnField(EmptyComponent)

                currentContent(last.page)
                title(last.page.name)
            }
        }
        //language=html
        """
            <span kref="$btn" class="mdc-toolbar__icon--menu"><i class="material-icons">&#xE5C4;</i></span>
            """.toDom()
    }
}