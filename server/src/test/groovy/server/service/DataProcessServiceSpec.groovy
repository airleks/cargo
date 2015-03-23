package ru.cargoonline.server.service

import ru.cargoonline.server.model.DbUser
import ru.cargoonline.server.model.ProcessRequest
import ru.cargoonline.server.repository.DataProcessRepository
import spock.lang.Specification

class DataProcessServiceSpec extends Specification
{
    def dataProcessRepository = Mock(DataProcessRepository)
    def dataProcessService = new DataProcessService(dataProcessRepository: dataProcessRepository)

    def setup()
    {
        dataProcessRepository.findOrCreateProcessRequest('zero-offset.xml') >> new ProcessRequest(offset: 0)
        dataProcessRepository.findOrCreateProcessRequest('hundred-offset.xml') >> new ProcessRequest(offset: 100)
        dataProcessRepository.findOrCreateProcessRequest(null) >> new IllegalArgumentException("Process Request id is null")
        dataProcessRepository.findOrCreateProcessRequest(!null) >> new ProcessRequest(offset: 0)
    }
    def "test standard input"()
    {
        expect:
            dataProcessService.batchDataProcessing(users, requestId, offset) == result
        where:
            result | requestId              | offset | users
            20     | 'zero-offset.xml'      | 0      | [new DbUser()] * 20
            0      | 'hundred-offset.xml'   | 0      | [new DbUser()] * 20
            0      | 'hundred-offset.xml'   | 80     | [new DbUser()] * 20
            10     | 'hundred-offset.xml'   | 90     | [new DbUser()] * 20
            20     | 'hundred-offset.xml'   | 100    | [new DbUser()] * 20
            0      | 'hundred-offset.xml'   | 101    | [new DbUser()] * 20
            0      | 'unknown-offset.xml'   | 10     | [new DbUser()] * 50
            0      | 'unknown-offset.xml'   | 0      |  []
    }

    def "test null users input"()
    {
        when:
            dataProcessService.batchDataProcessing(null, 'unknown-offset.xml', 0)
        then:
            thrown(IllegalArgumentException)
    }

    def "test null request input"()
    {
        when:
            dataProcessService.batchDataProcessing([new DbUser()] * 20, null, 0)
        then:
            thrown(IllegalArgumentException)
    }

    def "test negative offset input"()
    {
        when:
            dataProcessService.batchDataProcessing([new DbUser()] * 20, 'unknown-offset.xml', -1)
        then:
            thrown(IllegalArgumentException)
    }
}
