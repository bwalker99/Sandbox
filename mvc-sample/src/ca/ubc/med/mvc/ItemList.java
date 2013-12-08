package ca.ubc.med.mvc;
/*
 * A generic List class. Typically holds a list of 'Items'.
 * Items could be a list of Groups, Subjects... any list of objects.
 *
 */
import java.util.*;

public class ItemList {
private Vector list = new Vector();

public ItemList() {}
public ItemList(Vector V) { 
	list = V;
}

  public void setList(Vector v) { 
    list = v;
   }
  public Vector getList() {
    return list;
  } 
}
