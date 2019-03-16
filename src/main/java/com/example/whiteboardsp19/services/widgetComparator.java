package com.example.whiteboardsp19.services;

import com.example.whiteboardsp19.model.Widget;

import java.util.Comparator;

public class widgetComparator implements Comparator<Widget> {

  public int compare(Widget a, Widget b) {
    if (a.getOrderNumber() == null && b.getOrderNumber() == null) {
      return 0;
    } else if (a.getOrderNumber() == null) {
      return -1;
    } else if (b.getOrderNumber() == null) {
      return 1;
    } else {
      return a.getOrderNumber() - b.getOrderNumber();
    }

  }

}
