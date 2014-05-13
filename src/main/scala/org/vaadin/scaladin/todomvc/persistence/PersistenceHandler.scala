package org.vaadin.scaladin.todomvc.persistence

import scala.slick.driver.H2Driver.simple.Database
import scala.slick.driver.H2Driver.simple.TableQuery
import scala.slick.driver.H2Driver.simple.columnExtensionMethods
import scala.slick.driver.H2Driver.simple.queryToAppliedQueryInvoker
import scala.slick.driver.H2Driver.simple.queryToDeleteInvoker
import scala.slick.driver.H2Driver.simple.queryToInsertInvoker
import scala.slick.driver.H2Driver.simple.queryToUpdateInvoker
import scala.slick.driver.H2Driver.simple.stringColumnType
import scala.slick.driver.H2Driver.simple.valueToConstColumn
import scala.slick.jdbc.StaticQuery

import org.vaadin.scaladin.todomvc.model.Todo

class PersistenceHandler {
  val db = Database.forURL("jdbc:h2:file:scaladin-todomvc.db", driver = "org.h2.Driver")

  val todos: TableQuery[Todos] = TableQuery[Todos]

  // Initializes the Todo table in DB if needed
  db.withSession { implicit session =>
    StaticQuery.updateNA("CREATE TABLE IF NOT EXISTS " +
      "TODOS(ID VARCHAR(255) PRIMARY KEY, " +
      "UUID VARCHAR(255), " +
      "TODO VARCHAR(255), " +
      "COMPLETED BOOLEAN);").execute
  }

  // Returns all Todos for the given session id
  def fetch(sessionid: String): List[Todo] = {
    db.withSession { implicit session =>
      for { r <- todos.filter(_.listuuid === sessionid).list } yield { new Todo(r._1, r._3, r._4) }
    }
  }

  // Adds a new Todo
  def add(todo: Todo, sessionid: String) {
    db.withSession { implicit session => todos += (todo.id, sessionid, todo.caption, todo.completed) }
  }

  // Removes a Todo
  def delete(todo: Todo, sessionid: String) {
    db.withSession { implicit session => todos.filter(_.listuuid === sessionid).filter(_.id === todo.id).delete }
  }

  // Updates a Todo
  def update(todo: Todo, sessionid: String) {
    db.withSession { implicit session =>
      todos.filter(_.listuuid === sessionid).filter(_.id === todo.id).map(r => (r.todotext, r.completed)).update((todo.caption, todo.completed))
    }
  }
}