
package ca.ubc.med.ws.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "getPersonList", namespace = "http://service.ws.med.ubc.ca/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getPersonList", namespace = "http://service.ws.med.ubc.ca/")
public class GetPersonList {

    @XmlElement(name = "identifier", namespace = "")
    private String identifier;

    /**
     * 
     * @return
     *     returns String
     */
    public String getIdentifier() {
        return this.identifier;
    }

    /**
     * 
     * @param identifier
     *     the value for the identifier property
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
