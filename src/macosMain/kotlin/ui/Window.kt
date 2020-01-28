package ui

import libui.ktx.*
import libui.ktx.draw.*
import ui.UIElement

class Data(
    var editable: String,
    var checkbox: Boolean
)

val data = List(15) {
    Data("Part", false)
}

class Window: UIElement {
    override fun render() {
        appWindow(
            title = "Files generator",
            width = 640,
            height = 640
        ) {
            hbox {
                padded = true

                button("Click me") {}

                vbox {
                    tableview(data) {
                        stretchy = false
                        column("Column 1") {
                            label { row -> "Row $row" }
                        }
                        column("Column 2") {
                            label { row -> "Row $row" }
                        }
                    }
                }

                vbox {
                    textarea {
                        stretchy = false
                    }
                }
            }
        }
    }
}
