package org.vaadin.scaladin.todomvc.view

import scala.collection.mutable.ListBuffer
import org.vaadin.scaladin.todomvc.component.Footer
import org.vaadin.scaladin.todomvc.component.TodoComponent
import org.vaadin.scaladin.todomvc.model.Todo
import com.vaadin.event.ShortcutAction.KeyCode
import vaadin.scala.CheckBox
import vaadin.scala.CssLayout
import vaadin.scala.Label
import vaadin.scala.TextField
import org.vaadin.scaladin.EnterShortcutListener

class TodoViewImpl() extends CssLayout with TodoView {

  // View listener
  var listener: TodoViewListener = null

  // TodoComponent container
  val todos: ListBuffer[TodoComponent] = new ListBuffer

  var newTodoFocused = false
  var updatingToggle = false

  sizeUndefined
  id = "todoapp"

  // Build UI components
  val header = new Label {
    value = "<h1>TODOs</h1>"
    contentMode = Label.ContentMode.Html
    id = "header"
  }

  val newTodo = new TextField {
    id = "new-todo"
    prompt = "What needs to be done?"
    immediate = true
  }

  val main = new CssLayout { id = "main" }

  val toggleAll = new CheckBox { caption = "Mark all as complete"; id = "toggle-all" }

  val footer = new Footer()

  // Build layout
  main.add(toggleAll)
  add(header)
  add(newTodo)
  add(main)
  add(footer)

  // Add listeners
  toggleAll.valueChangeListeners += (event => if (!updatingToggle) listener.toggleAll(toggleAll.booleanValue))
  newTodo.blurListeners += (event => newTodoFocused = false)
  newTodo.focusListeners += (event => newTodoFocused = true)
  newTodo.p.addShortcutListener(new EnterShortcutListener(null, KeyCode.ENTER) {
    override def handleAction(sender: Any, target: Any) {
      if (newTodoFocused) { listener.createNew(newTodo.value.get) } else { ui.p.focus() }
    }
  })

  // Initialize the view listeners
  override def init(l: TodoViewListener) {
    listener = l
    footer.listener = l;
  }

  // View mode change functions
  override def clearNewTodo { newTodo.value_=("") }
  override def showAll { todos.foreach(_.visible = true); footer.allSelected }
  override def showActive { todos.foreach(todo => todo.visible_=(!todo.completed)); footer.activeSelected }
  override def showCompleted { todos.foreach(todo => todo.visible_=(todo.completed)); footer.completedSelected }

  // Individual Todo handling
  override def edit(todo: Todo) { todos.filter(todoC => todoC.todo == todo).foreach(_.editMode_=(true)) }
  override def endEdit(todo: Todo) { todos.filter(todoC => todoC.todo == todo).foreach(todoC => todoC.editMode_=(false)) }
  override def addTodo(todo: Todo) {
    todos += new TodoComponent(todo, listener) { completed_=(todo.completed) }
    main.add(todos.last)
    updateCounts
  }
  override def removeTodo(todo: Todo) {
    todos.filter(todoC => todoC.todo == todo).foreach(todoC => { todos -= todoC; main.removeComponent(todoC) })
    updateCounts
  }
  override def completed(todo: Todo, completed: Boolean) {
    todos.filter(todoC => todoC.todo == todo).foreach(_.completed_=(completed))
    updateCounts
  }

  // Clears/resets the whole view
  override def clear() {
    todos.clear
    main.removeAllComponents
    main.add(toggleAll)
    updateCounts
  }

  // Update the Todo counts
  private def updateCounts() {
    val completed = todos.filter(todo => todo.completed).size
    footer.visible_=(todos.size > 0)
    footer.leftCount_=(todos.size - completed)
    footer.completedCount_=(completed)
    updatingToggle = true
    toggleAll.value_=(completed > 0 && completed == todos.size)
    updatingToggle = false
  }
}