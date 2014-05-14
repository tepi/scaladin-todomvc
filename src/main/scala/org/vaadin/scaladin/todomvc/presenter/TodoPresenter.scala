package org.vaadin.scaladin.todomvc.presenter

import java.util.UUID

import scala.collection.mutable.ListBuffer

import org.vaadin.scaladin.todomvc.model.Todo
import org.vaadin.scaladin.todomvc.persistence.PersistenceHandler
import org.vaadin.scaladin.todomvc.view.TodoView

class TodoPresenter(val view: TodoView) extends org.vaadin.scaladin.todomvc.view.TodoViewListener {

  private val persistence = new PersistenceHandler
  private var dbkey = ""

  private var showActive = false;
  private var showCompleted = false;

  view.init(this)

  // Data container
  val todos: ListBuffer[Todo] = new ListBuffer

  // Main view event handling
  def toggleAll(completed: Boolean) {
    todos.foreach(todo => {
      todo.completed = completed
      persistence.update(todo, dbkey)
      reset
    })
  }
  def createNew(caption: String) {
    if (!caption.isEmpty) {
      todos += new Todo(UUID.randomUUID().toString(), caption, false)
      persistence.add(todos.last, dbkey)
      view.clearNewTodo
      reset
    }
  }

  // Footer event handling
  def allClicked() {
    view.showAll
    showActive = false
    showCompleted = false
  }
  def activeClicked() {
    view.showActive
    showActive = true
    showCompleted = false
  }
  def completedClicked() {
    view.showCompleted
    showActive = false
    showCompleted = true
  }
  def clearCompletedClicked() {
    todos.filter(todo => todo.completed).foreach(todo => {
      todos -= todo
      persistence.delete(todo, dbkey)
      reset
    })
  }

  // Single Todo event handling
  def stateChanged(todo: Todo, completed: Boolean) {
    todo.completed = completed
    persistence.update(todo, dbkey)
    reset
  }
  def editModeRequested(todo: Todo) { view.edit(todo) }
  def editingEnded(todo: Todo, newCaption: Option[String]) {
    todo.caption = newCaption.get
    persistence.update(todo, dbkey)
    reset
  }
  def deleteClicked(todo: Todo) {
    todos -= todo
    persistence.delete(todo, dbkey)
    reset
  }

  def dbkey(key: String) {
    dbkey = key
    reset
  }

  def reset {
    // Clear everything
    todos.clear
    view.clear
    // Fetch (possible) data from DB and show it
    persistence.fetch(dbkey).foreach(todo => {
      todos += todo
      view.addTodo(todo)
    })
    // Set the correct view mode
    if (showActive) view.showActive
    else if (showCompleted) view.showCompleted
    else view.showAll
  }
}