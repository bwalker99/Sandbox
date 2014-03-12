
package ca.ubc.med.ws.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getPersonResponse", namespace = "http://service.ws.med.ubc.ca/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPersonResponse", namespace = "http://service.ws.med.ubc.ca/")
public class GetPersonResponse {

    @XmlElement(name = "return", namespace = "")
    private ca.ubc.med.middleware.data.Person _return;

    /**
     * 
     * @return
     *     returns Person
     */
    public ca.ubc.med.middleware.data.Person getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(ca.ubc.med.middleware.data.Person _return) {
        this._return = _return;
    }

}
