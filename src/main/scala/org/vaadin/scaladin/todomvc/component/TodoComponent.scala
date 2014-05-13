package org.vaadin.scaladin.todomvc.component

import vaadin.scala.CheckBox
import vaadin.scala.CssLayout
import vaadin.scala.Label
import vaadin.scala.NativeButton
import vaadin.scala.TextField
import org.vaadin.scaladin.todomvc.model.Todo
import org.vaadin.scaladin.todomvc.view.TodoViewListener

class TodoComponent(val todo: Todo, listener: TodoViewListener) extends CssLayout {

  var updating = false

  // Handle style names
  styleNames += "todo-row"

  // Build UI components
  val checkbox = new CheckBox { immediate = true }

  val captionText = new Label {
    value = todo.caption
    sizeUndefined
  }

  val delete = new NativeButton { styleName = "destroy" }

  val editor = new TextField { value = todo.caption }

  // Build the layout
  add(checkbox)
  add(captionText)
  add(delete)
  add(editor)

  // Add listener logic
  checkbox.valueChangeListeners += (event => if (!updating) listener.stateChanged(todo, checkbox.booleanValue))
  layoutClickListeners += (event => { if (event.doubleClick && captionText == event.clickedComponent) listener.editModeRequested(todo) })
  editor.blurListeners += (event => listener.editingEnded(todo, editor.value))
  delete.clickListeners += (event => listener.deleteClicked(todo))

  // State control methods
  def completed(): Boolean = todo.completed

  def completed_=(completed: Boolean) {
    updating = true
    if (completed) {
      styleNames += "completed"
      checkbox.value_=(true)
    } else {
      styleNames -= "completed"
      checkbox.value_=(false)
    }
    updating = false
  }

  def title_=(title: String) {
    captionText.value = title
    editor.value = title
  }

  def editMode_=(editMode: Boolean) {
    if (editMode) {
      styleNames += "editing"
      editor.selectAll
      editor.focus
    } else {
      styleNames -= "editing"
      title_=(todo.caption)
    }
  }
}