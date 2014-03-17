
package ca.ubc.med.ws.demo.service.jaxws;

import java.util.Vector;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getPersonListResponse", namespace = "http://service.demo.ws.med.ubc.ca/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPersonListResponse", namespace = "http://service.demo.ws.med.ubc.ca/")
public class GetPersonListResponse {

    @XmlElement(name = "return", namespace = "")
    private Vector<ca.ubc.med.ws.middleware.data.Person> _return;

    /**
     * 
     * @return
     *     returns Vector<Person>
     */
    public Vector<ca.ubc.med.ws.middleware.data.Person> getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Vector<ca.ubc.med.ws.middleware.data.Person> _return) {
        this._return = _return;
    }

}
