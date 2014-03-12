package ca.ubc.med.middleware.data;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DataObject")
public class DataObject {
	
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
