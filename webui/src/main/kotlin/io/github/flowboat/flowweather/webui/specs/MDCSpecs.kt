package io.github.flowboat.flowweather.webui.specs

import xyz.nulldev.kdom.api.Component
import xyz.nulldev.kdom.api.CustomElementSpec
import xyz.nulldev.kdom.api.util.dataValue
import xyz.nulldev.kdom.api.util.emit

val MDCSliderSpec = CustomElementSpec("mdc-slider", {
    Component.from {
        val slider by lazy {
            js("(function(e){return new mdc.slider.MDCSlider(e)})")(it.root())
        }

        onAttach = {
            slider.listen("MDCSlider:input", {
                it.root().dataValue = slider.value
                it.root().emit("input")
            })

            it.attributes.values.forEach {
                it.addUpdateListener {
                    slider.layout()
                }
            }
        }

        //language=html
        """
            <div class="mdc-slider mdc-slider--discrete mdc-slider--display-markers" tabindex="0" role="slider"
                aria-valuemin="${it.attributes["min"]}" aria-valuemax="${it.attributes["max"]}" aria-valuenow="${it.attributes["initial"]}"
                aria-label="Select Value"
                style="${it.attributes["style"]}">
                <div class="mdc-slider__track-container">
                    <div class="mdc-slider__track"></div>
                    <div class="mdc-slider__track-marker-container"></div>
                </div>
                <div class="mdc-slider__thumb-container">
                    <div class="mdc-slider__pin">
                        <span class="mdc-slider__pin-value-marker"></span>
                    </div>
                    <svg class="mdc-slider__thumb" width="21" height="21">
                        <circle cx="10.5" cy="10.5" r="7.875"></circle>
                    </svg>
                    <div class="mdc-slider__focus-ring"></div>
                </div>
            </div>
""".toDom()
    }
})

val MDCSpecs = listOf(
        MDCSliderSpec
)
