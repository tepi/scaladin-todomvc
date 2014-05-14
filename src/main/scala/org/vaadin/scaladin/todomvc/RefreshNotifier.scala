package org.vaadin.scaladin.todomvc

object RefreshNotifier {
	val listeners = collection.mutable.Map[TodoMVCUI, String]()
    def register(ui: TodoMVCUI, sessionid: String) { listeners(ui) = sessionid; println("Registering UI id: " + ui.uiId) }
    def unRegister(ui: TodoMVCUI) { listeners -= ui }
    def notify(sessionid: String) { listeners.filter(e => e._2 == sessionid).foreach({ _._1.refresh }) }
}