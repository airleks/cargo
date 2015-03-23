
package ru.cargoonline.common.generated;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for xmluser complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="xmluser">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="age">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="120"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="address" type="{http://cargoonline.ru/schema/common/generated}address"/>
 *         &lt;element name="birth" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="flags">
 *           &lt;simpleType>
 *             &lt;list>
 *               &lt;simpleType>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                   &lt;enumeration value="active"/>
 *                   &lt;enumeration value="deleted"/>
 *                   &lt;enumeration value="superuser"/>
 *                 &lt;/restriction>
 *               &lt;/simpleType>
 *             &lt;/list>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "xmluser", propOrder = {
    "code",
    "username",
    "firstname",
    "lastname",
    "age",
    "address",
    "birth",
    "flags"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
public class Xmluser {

    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected String code;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected String username;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected String firstname;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected String lastname;
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected int age;
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected Address address;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected XMLGregorianCalendar birth;
    @XmlList
    @XmlElement(required = true)
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    protected List<String> flags;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the firstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the value of the firstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public void setFirstname(String value) {
        this.firstname = value;
    }

    /**
     * Gets the value of the lastname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the value of the lastname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public void setLastname(String value) {
        this.lastname = value;
    }

    /**
     * Gets the value of the age property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public int getAge() {
        return age;
    }

    /**
     * Sets the value of the age property.
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public void setAge(int value) {
        this.age = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the birth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public XMLGregorianCalendar getBirth() {
        return birth;
    }

    /**
     * Sets the value of the birth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public void setBirth(XMLGregorianCalendar value) {
        this.birth = value;
    }

    /**
     * Gets the value of the flags property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the flags property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFlags().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2015-03-18T07:47:13+03:00", comments = "JAXB RI v2.2.4-2")
    public List<String> getFlags() {
        if (flags == null) {
            flags = new ArrayList<String>();
        }
        return this.flags;
    }

}
