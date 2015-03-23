package server.controller

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ru.cargoonline.common.generated.Address
import ru.cargoonline.common.generated.Xmluser
import ru.cargoonline.common.generated.Xmlusers
import ru.cargoonline.server.controller.DataSyncController
import ru.cargoonline.server.service.DataProcessService
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

import static org.springframework.http.MediaType.APPLICATION_XML
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DataSyncControllerSpec extends Specification
{
    def dataProcessService = Mock(DataProcessService)
    def dataSyncController = new DataSyncController(dataProcessService: dataProcessService)

    def mockMvc = MockMvcBuilders.standaloneSetup(dataSyncController).build()

    Marshaller m

    def setup()
    {
        JAXBContext context = JAXBContext.newInstance(Xmlusers.class)
        m = context.createMarshaller()
    }

    def 'null content input test'()
    {
        given:

        when:
            def response = mockMvc.perform(post('/testfilename.xml/123')
                    .contentType(APPLICATION_XML)
            )

        then:
            0 * dataProcessService.batchDataProcessing(_, 'testfilename.xml', 123)
            response
                    .andExpect(status().is(400))
    }

    def 'invalid url input test'()
    {
        given:
            StringWriter writer = new StringWriter()
            m.marshal(new Xmlusers(), writer)
        when:
            def response = mockMvc.perform(post('/filename/nondigitoffset')
                    .contentType(APPLICATION_XML)
                    .content(writer.toString()))
        then:
            0 * dataProcessService.batchDataProcessing(_, 'testfilename.xml', 123)
            response
                    .andExpect(status().is(400))
    }

    def 'invalid content input test'()
    {
        when:
            def response = mockMvc.perform(post('/testfilename.xml/123')
                                .contentType(APPLICATION_XML)
                                .content('<xmlusers><users></users></xmlusers>'))       // todo in the same way we can test any corrupted or restriction-invalid content
        then:
            0 * dataProcessService.batchDataProcessing(_, 'testfilename.xml', 123)
            response
                    .andExpect(status().is(400))
    }

    def 'empty records input test'()
    {
        given:
            StringWriter writer = new StringWriter()
            m.marshal(new Xmlusers(), writer)

        when:
            def response = mockMvc.perform(post('/testfilename.xml/123')
                                    .contentType(APPLICATION_XML)
                                    .content(writer.toString())
            )

        then:
            1 * dataProcessService.batchDataProcessing(_, 'testfilename.xml', 123)
            response
                .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_XML))
                    .andExpect(content().string(writer.toString()))
    }


    def 'single record input test'()
    {
        given:
            StringWriter writer = new StringWriter()
            Xmlusers xmlusers = new Xmlusers()
            xmlusers.users.add(new Xmluser( code : 'test',
                                            username: 'User Name',
                                            firstname: 'First',
                                            lastname: 'Last',
                                            age: 20,
                                            address: new Address(country: 'Country', city: 'City', street: 'Street'),
                                            birth: new XMLGregorianCalendarImpl(new GregorianCalendar()),
                                            flags: ['active']
                                          )
            )

            m.marshal(xmlusers, writer)

        when:
            def response = mockMvc.perform(post('/testfilename.xml/123')
                    .contentType(APPLICATION_XML)
                    .content(writer.toString())
            )

        then:
            1 * dataProcessService.batchDataProcessing(_, 'testfilename.xml', 123)
            response
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_XML))
                .andExpect(content().string(writer.toString()))
    }


    def 'multiple records input test'()
    {
        given:
            StringWriter writer = new StringWriter()
            Xmlusers xmlusers = new Xmlusers()
            xmlusers.users.add(new Xmluser( code : 'test',
                    username: 'User Name',
                    firstname: 'First',
                    lastname: 'Last',
                    age: 20,
                    address: new Address(country: 'Country', city: 'City', street: 'Street'),
                    birth: new XMLGregorianCalendarImpl(new GregorianCalendar()),
                    flags: ['active']
            )
            )

            m.marshal(xmlusers, writer)

        when:
            def response = mockMvc.perform(post('/testfilename.xml/123')
                    .contentType(APPLICATION_XML)
                    .content(writer.toString())
            )

        then:
            1 * dataProcessService.batchDataProcessing(_, 'testfilename.xml', 123)
            response
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_XML))
                .andExpect(content().string(writer.toString()))
    }
}
