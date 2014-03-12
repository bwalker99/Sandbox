
package ca.ubc.med.ws.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ca.ubc.med.ws.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DataObject_QNAME = new QName("http://service.ws.med.ubc.ca/", "DataObject");
    private final static QName _GetPersonListResponse_QNAME = new QName("http://service.ws.med.ubc.ca/", "getPersonListResponse");
    private final static QName _Person_QNAME = new QName("http://service.ws.med.ubc.ca/", "Person");
    private final static QName _GetPerson_QNAME = new QName("http://service.ws.med.ubc.ca/", "getPerson");
    private final static QName _GetPersonResponse_QNAME = new QName("http://service.ws.med.ubc.ca/", "getPersonResponse");
    private final static QName _GetPersonList_QNAME = new QName("http://service.ws.med.ubc.ca/", "getPersonList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ca.ubc.med.ws.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPersonList }
     * 
     */
    public GetPersonList createGetPersonList() {
        return new GetPersonList();
    }

    /**
     * Create an instance of {@link GetPerson }
     * 
     */
    public GetPerson createGetPerson() {
        return new GetPerson();
    }

    /**
     * Create an instance of {@link GetPersonListResponse }
     * 
     */
    public GetPersonListResponse createGetPersonListResponse() {
        return new GetPersonListResponse();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link GetPersonResponse }
     * 
     */
    public GetPersonResponse createGetPersonResponse() {
        return new GetPersonResponse();
    }

    /**
     * Create an instance of {@link DataObject }
     * 
     */
    public DataObject createDataObject() {
        return new DataObject();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.med.ubc.ca/", name = "DataObject")
    public JAXBElement<DataObject> createDataObject(DataObject value) {
        return new JAXBElement<DataObject>(_DataObject_QNAME, DataObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPersonListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.med.ubc.ca/", name = "getPersonListResponse")
    public JAXBElement<GetPersonListResponse> createGetPersonListResponse(GetPersonListResponse value) {
        return new JAXBElement<GetPersonListResponse>(_GetPersonListResponse_QNAME, GetPersonListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.med.ubc.ca/", name = "Person")
    public JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(_Person_QNAME, Person.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPerson }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.med.ubc.ca/", name = "getPerson")
    public JAXBElement<GetPerson> createGetPerson(GetPerson value) {
        return new JAXBElement<GetPerson>(_GetPerson_QNAME, GetPerson.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPersonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.med.ubc.ca/", name = "getPersonResponse")
    public JAXBElement<GetPersonResponse> createGetPersonResponse(GetPersonResponse value) {
        return new JAXBElement<GetPersonResponse>(_GetPersonResponse_QNAME, GetPersonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPersonList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.ws.med.ubc.ca/", name = "getPersonList")
    public JAXBElement<GetPersonList> createGetPersonList(GetPersonList value) {
        return new JAXBElement<GetPersonList>(_GetPersonList_QNAME, GetPersonList.class, null, value);
    }

}
