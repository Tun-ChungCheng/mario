package main;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FocusManager extends ComponentAdapter {
    @Override
    public void componentShown(ComponentEvent e) {
        Component source = (Component) e.getSource();
        source.requestFocusInWindow();
    }
}
