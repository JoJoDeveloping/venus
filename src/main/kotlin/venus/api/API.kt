package venus.api

/* ktlint-disable no-wildcard-imports */
import org.w3c.dom.*
import venus.Driver
import venus.IRenderer
import kotlin.browser.document
import kotlin.dom.addClass
import kotlin.dom.hasClass

/* ktlint-enable no-wildcard-imports */

@JsName("API") object API {
    // The key (String) is the ID of the FunctionsList
    val functionsLists: MutableMap<String, FunctionsList> = HashMap()

    @JsName("addList") fun addList(s: String) {
        functionsLists[s] = FunctionsList(s)
    }

    @JsName("evalList") fun evalList(s: String, args: Any?): Boolean {
        return functionsLists[s]?.evalFunctions(args) ?: false
    }

    /**
     * All functions should take in a single argument which is a javascript list of arguments.
     * This list of arguments will differ depending on which function it uses.
     */
    @JsName("addListener") fun addListener(locationID: String, pkgid: String, fnid: String, function: Function<Any?>) {
        if (functionsLists.containsKey(locationID)) {
            val f = functionsLists.get(locationID)
            f?.add("$pkgid.$fnid", function)
            console.log("Added function to '$locationID'")
        } else {
            console.error("Could not add function to '$locationID' because the location ID does not exist!")
        }
    }

    @JsName("removeListener") fun removeListener(locationID: String, pkgid: String, fnid: String) {
        if (functionsLists.containsKey(locationID)) {
            val f = functionsLists.get(locationID)
            f?.remove("$pkgid.$fnid")
            console.log("Remove function to '$locationID'")
        } else {
            console.error("Could not remove function to '$locationID' because the location ID does not exist!")
        }
    }

    @JsName("addMainTab") fun addMainTab(name: String): Element? {
        if (IRenderer.getRenderer().mainTabs.contains(name)) {
            console.error("Tabs MUST have unique names!")
            return null
        }
        val t = document.createElement("li")
        t.id = "$name-tab"

        val link = document.createElement("a")
        t.appendChild(link)
        link.setAttribute("onclick", "driver.openGenericMainTab(\"$name\")")

        val font = document.createElement("font")
        link.appendChild(font)
        val tmp = name
        font.innerHTML = tmp.capitalize()

        val tabs_list = document.getElementById("venus-main-tabs") ?: return null
        tabs_list.appendChild(t)

        val s = document.createElement("section")

        s.addClass("section")
        s.id = "$name-tab-view"
        s.setAttribute("style", "display:none;")
        document.body!!.appendChild(s)

        IRenderer.getRenderer().mainTabs.add(name)
        s.innerHTML = "TEST"
        return s
    }

    @JsName("addMainTabAndShow") fun addMainTab(name: String, show: Boolean): Element? {
        val s = addMainTab(name)
        showMainTab(name)
        return s
    }

    @JsName("showMainTab") fun showMainTab(name: String): Boolean {
        val tab = (document.getElementById("$name-tab") ?: return false) as HTMLLIElement
        tab.style.display = ""
        return true
    }

    @JsName("hideMainTab") fun hideMainTab(name: String): Boolean {
        val tab = (document.getElementById("$name-tab") ?: return false) as HTMLLIElement
        tab.style.display = "none"
        if (tab.hasClass("is-active")) {
            Driver.openVenus()
        }
        return true
    }

    @JsName("removeMainTab") fun removeMainTab(name: String): Boolean {
        val t = (document.getElementById("$name-tab") ?: return false) as HTMLLIElement
        val v = (document.getElementById("$name-tab-view") ?: return false)
        if (t.hasClass("is-active")) {
            hideMainTab(name)
        }
        IRenderer.getRenderer().mainTabs.remove(name)
        t.remove()
        v.remove()
        return true
    }
}