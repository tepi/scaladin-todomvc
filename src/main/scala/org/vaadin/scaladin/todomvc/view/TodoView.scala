package org.vaadin.scaladin.todomvc.view

import org.vaadin.scaladin.todomvc.model.Todo

trait TodoView {
  // Sets the view listener (=presenter) to the view
  def init(l: TodoViewListener)

  // View mode change functions
  def showAll {}
  def showActive {}
  def showCompleted {}

  // Individual Todo related functions
  def addTodo(todo: Todo) {}
  def removeTodo(todo: Todo) {}
  def completed(todo: Todo, completed: Boolean) {}
  def edit(todo: Todo) {}
  def endEdit(todo: Todo) {}

  // Clears the new Todo text field
  def clearNewTodo {}
  
  // Clears all Todos
  def clear {}
}