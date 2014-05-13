package org.vaadin.scaladin.todomvc.view

import org.vaadin.scaladin.todomvc.model.Todo

trait TodoViewListener {
  // Events from the main view
  def toggleAll(completed: Boolean)
  def createNew(caption: String)

  // Events from the footer
  def allClicked()
  def activeClicked()
  def completedClicked()
  def clearCompletedClicked()

  // Events from individual todos
  def stateChanged(todo: Todo, completed: Boolean)
  def editModeRequested(todo: Todo)
  def editingEnded(todo: Todo, newCaption: Option[String])
  def deleteClicked(todo: Todo)
}