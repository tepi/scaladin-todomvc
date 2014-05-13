package org.vaadin.scaladin.todomvc

import java.util.UUID
import org.vaadin.scaladin.todomvc.presenter.TodoPresenter
import org.vaadin.scaladin.todomvc.view.TodoViewImpl
import vaadin.scala.CssLayout
import vaadin.scala.Label
import vaadin.scala.UI
import vaadin.scala.internal.UriFragmentChangedListener
import vaadin.scala.server.Page
import vaadin.scala.PushMode
import vaadin.scala.server.ScaladinRequest

class TodoMVCUI extends UI(theme = "todomvc-css", pushMode = PushMode.Automatic) {
  val view = new TodoViewImpl
  val presenter = new TodoPresenter(view)

//  override def init(request: ScaladinRequest) {
//	  pushConfiguration.pushMode = PushMode.Automatic
//  }
  
  // Build the UI
  sizeUndefined
  content = new CssLayout {
    add(view)
    add(new Label {
      value = "<p>Double-click to edit a todo</p>" +
        "<p>Written by <a href=\"https://github.com/tepi\">Teppo Kurki</a>, " +
        "theme by <a href=\"https://github.com/jounik\">Jouni Koivuviita</a></p>"
      contentMode = Label.ContentMode.Html
      id = "info"
    })
  }

  // Handle fragment on initial rendering
  val initialFragment = page.uriFragment.getOrElse(UUID.randomUUID().toString())
  presenter.dbkey(initialFragment)
  page.setUriFragment(initialFragment, false)

  // Handle fragment changes
  page.p.addUriFragmentChangedListener(
    new UriFragmentChangedListener((event: Page.UriFragmentChangedEvent) => {
      page.setUriFragment(event.fragment.getOrElse(UUID.randomUUID().toString()), false)
      presenter.dbkey(page.uriFragment.get)
    }))
}