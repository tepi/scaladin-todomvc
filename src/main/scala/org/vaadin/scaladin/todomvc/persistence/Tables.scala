package org.vaadin.scaladin.todomvc.persistence

import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.{ ProvenShape, ForeignKeyQuery }

class Todos(tag: Tag)
  extends Table[(String, String, String, Boolean)](tag, "TODOS") {
  def id: Column[String] = column[String]("ID", O.AutoInc, O.PrimaryKey)
  def listuuid: Column[String] = column[String]("UUID")
  def todotext: Column[String] = column[String]("TODO")
  def completed: Column[Boolean] = column[Boolean]("COMPLETED")
  def * : ProvenShape[(String, String, String, Boolean)] = (id, listuuid, todotext, completed)
}