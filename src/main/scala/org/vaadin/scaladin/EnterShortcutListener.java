package org.vaadin.scaladin;

import com.vaadin.event.ShortcutListener;

@SuppressWarnings("serial")
public abstract class EnterShortcutListener extends ShortcutListener {
	public EnterShortcutListener(String caption, int keyCode) {
		super(caption, keyCode, null);
	}
}
