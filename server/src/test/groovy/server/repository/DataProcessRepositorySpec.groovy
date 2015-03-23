package server.repository

import ru.cargoonline.server.model.ProcessRequest
import ru.cargoonline.server.repository.DataProcessRepository
import spock.lang.Specification

import javax.persistence.EntityManager

class DataProcessRepositorySpec extends Specification
{
    def entityManager = Mock(EntityManager)
    def dataProcessRepository = new DataProcessRepository(entityManager: entityManager)

    def setup()
    {
        entityManager.find(ProcessRequest.class, 'zero offset id') >> new ProcessRequest(offset: 0)
        entityManager.find(ProcessRequest.class, 'hundred offset id') >> new ProcessRequest(offset: 100)
        entityManager.find(ProcessRequest.class, _) >> null             // all other rows are not exsist
        entityManager.find(ProcessRequest.class, null) >> IllegalArgumentException      // according to entity manger specification
    }

    def "test find or create process request valid input"()
    {
        expect:
            dataProcessRepository.findOrCreateProcessRequest(id).with {
                offset == off
            }
        where:
            id                  | off
            'zero offset id'    | 0
            'hundred offset id' | 100
            'any id'            | 0
    }

    def "test find or create process request invalid input"()
    {
        when:
            dataProcessRepository.findOrCreateProcessRequest(null)
        then:
            thrown(IllegalArgumentException)
    }
}
