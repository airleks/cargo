package server.util

import org.hibernate.validator.constraints.Range
import ru.cargoonline.server.model.Address
import ru.cargoonline.server.model.DbUser
import ru.cargoonline.server.util.DataConverter
import ru.cargoonline.server.util.UserFlags
import spock.lang.Specification

import javax.persistence.Embedded
import javax.xml.datatype.XMLGregorianCalendar

/**
 * Created by Алексей on 3/23/2015.
 */
class DataConverterSpec extends Specification
{
    def 'test users list convertation'()
    {
        expect:
            DataConverter.xmlUserList2DbUserList([new ru.cargoonline.common.generated.Xmluser(
                    code: code,
                    username : username,
                    firstname : firstname,
                    lastname : lastname,
                    age : age,
                    //new XMLGregorianCalendar()    todo compare birth
                    address: new ru.cargoonline.common.generated.Address(country: country, city: city, street: street),
                    flags : flags
            )] * count) == [new DbUser(
                    code: code,
                    username : username,
                    firstname : firstname,
                    lastname : lastname,
                    age : age,
                    //birth : birth,        todo compare birth
                    address : new Address(country : country, city : city, street : street),
                    flags : flag)] * count
        where:
            // todo compare birth
            count | code   | username | firstname | lastname | age | birth | country   | city   | street   | flags | flag
            10    | null   | null     | null      | null     | 20  | null  | null      | null   | null     | null  | 0
            100   | 'code' | 'name'   | 'first'   | 'last'   | 40  | null  | 'country' | 'city' | 'street' | ['active','superuser']  | 5
    }

    def 'test null users list convertation'()
    {
        expect:
            DataConverter.xmlUserList2DbUserList(null) == null
    }

    def 'test user convertation'()
    {
        expect:
            DataConverter.xmlUser2DbUser(
                    new ru.cargoonline.common.generated.Xmluser(
                            code: code,
                            username : username,
                            firstname : firstname,
                            lastname : lastname,
                            age : age,
                            //new XMLGregorianCalendar()    todo compare birth
                            address: new ru.cargoonline.common.generated.Address(country: country, city: city, street: street),
                            flags : flags
                    )) ==
                    new DbUser(
                            code: code,
                            username : username,
                            firstname : firstname,
                            lastname : lastname,
                            age : age,
                            //birth : birth,        todo compare birth
                            address : new Address(country : country, city : city, street : street),
                            flags : flag)
        where:
            // todo compare birth
            code   | username | firstname | lastname | age | birth | country   | city   | street   | flags | flag
            null   | null     | null      | null     | 20  | null  | null      | null   | null     | null  | 0
            'code' | 'name'   | 'first'   | 'last'   | 40  | null  | 'country' | 'city' | 'street' | ['active','deleted']  | 3
    }

    def 'test null user convertation'()
    {
        expect:
            DataConverter.xmlUser2DbUser(null) == null
    }

    def 'test address convertation'()
    {
        expect:
            DataConverter.xmlAddress2DbAddress(new ru.cargoonline.common.generated.Address(country: country, city: city, street: street)) == new Address(country: country, city: city, street: street)
        where:
            country     | city      | street
            null        | null      | null
            'Country'   | null      | null
            null        | 'City'    | null
            null        | null      | 'Street'
            'Country'   | 'City'    | null
            null        | 'City'    | 'Street'
            'Country'   | null      | 'Street'
            'Country'   | 'City'    | 'Street'
    }

    def 'test null address convertation'()
    {
        expect:
            DataConverter.xmlAddress2DbAddress(null) == null
    }

    def 'test flag convertation'()
    {
        expect:
            DataConverter.xmlFlags2dbFlags(flags) == flag
        where:
            flag | flags
            0    | []
            1    | ['active']
            2    | ['deleted']
            3    | ['active', 'deleted']
            4    | ['superuser']
            5    | ['active', 'superuser']
            6    | ['deleted', 'superuser']
            7    | ['active', 'deleted', 'superuser']
    }

    def 'test null flag convertation'()
    {
        expect:
            DataConverter.xmlFlags2dbFlags(null) == 0
    }
}
