package org.vaadin.scaladin.todomvc.component

import vaadin.scala.CssLayout
import vaadin.scala.Label
import vaadin.scala.NativeButton
import org.vaadin.scaladin.todomvc.view.TodoViewListener

class Footer() extends CssLayout {
  var listener: TodoViewListener = null

  id = "footer"
  visible = false

  // Build UI components
  val todoCount = new Label {
    contentMode = Label.ContentMode.Html
    id = "todo-count"
    sizeUndefined
  }

  val filters = new CssLayout { id = "filters" }

  val all = new NativeButton {
    caption = "All"
    styleNames += "selected"
  }

  val active = new NativeButton { caption = "Active" }

  val completed = new NativeButton { caption = "Completed" }

  val clearCompleted = new NativeButton { id = "clear-completed" }

  // Build the layout
  filters.add(all)
  filters.add(active)
  filters.add(completed)
  add(todoCount)
  add(filters)
  add(clearCompleted)

  // Add listeners
  all.clickListeners += (event => listener.allClicked)
  active.clickListeners += (event => listener.activeClicked)
  completed.clickListeners += (event => listener.completedClicked)
  clearCompleted.clickListeners += (event => listener.clearCompletedClicked)

  // Methods for setting the left/completed counts
  def completedCount_=(completed: Int) { clearCompleted.caption = "Clear completed (" + completed + ")" }
  def leftCount_=(left: Int) { todoCount.value = "<b>" + left + "</b> items left" }

  // Methods for setting styles for the current view mode
  def allSelected() {
    all.styleNames += "selected"
    active.styleNames -= "selected"
    completed.styleNames -= "selected"
  }
  def activeSelected() {
    all.styleNames -= "selected"
    active.styleNames += "selected"
    completed.styleNames -= "selected"
  }
  def completedSelected() {
    all.styleNames -= "selected"
    active.styleNames -= "selected"
    completed.styleNames += "selected"
  }
}