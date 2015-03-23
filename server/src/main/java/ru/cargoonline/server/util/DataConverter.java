package ru.cargoonline.server.util;

import ru.cargoonline.common.generated.Xmluser;
import ru.cargoonline.server.model.Address;
import ru.cargoonline.server.model.DbUser;

import java.util.*;

/**
 * Data objects converting util.
 */
public class DataConverter
{

    /**
     * Convert xml user data objects collection to db user data objects collection
     * @param xmlusers xml user data objects collection
     * @return db user data objects collection
     */
    public static List<DbUser> xmlUserList2DbUserList(List<Xmluser> xmlusers)
    {
        if (xmlusers == null)
        {
            return null;
        }

        List<DbUser> dbUsers = new ArrayList<DbUser>(xmlusers.size());

        for (Xmluser xmluser : xmlusers)
        {
            dbUsers.add(xmlUser2DbUser(xmluser));
        }

        return dbUsers;
    }


    /**
     * Convert xml user data object to db user data object
     * @param xmluser xml user data object
     * @return db user data object
     */
    public static DbUser xmlUser2DbUser(Xmluser xmluser)
    {
        return (xmluser != null) ?
                    new DbUser(
                        xmluser.getCode(),
                        xmluser.getUsername(),
                        xmluser.getFirstname(),
                        xmluser.getLastname(),
                        xmluser.getAge(),
                        (xmluser.getBirth() != null) ? xmluser.getBirth().toGregorianCalendar().getTime() : null,
                        xmlAddress2DbAddress(xmluser.getAddress()),
                        xmlFlags2dbFlags(xmluser.getFlags())) :
                    null;
    }

    /**
     * Convert xml user address data object to db user address data object
     * @param xmlAddress xml user address data object
     * @return db user address data object
     */
    public static Address xmlAddress2DbAddress(ru.cargoonline.common.generated.Address xmlAddress)
    {
        return (xmlAddress != null) ?
                    new Address(xmlAddress.getCountry(), xmlAddress.getCity(), xmlAddress.getStreet()) :
                    null;
    }

    /**
     * Convert xml user flags list to db user flags value
     * @param xmlFlags xml user flags list
     * @return db user flags value
     */
    public static byte xmlFlags2dbFlags(List<String> xmlFlags)
    {
        if (xmlFlags == null)
        {
            return 0;
        }

        byte res = 0;

        Set<String> uniqueFlags = new HashSet<String>();
        uniqueFlags.addAll(xmlFlags);       // since unique validation wasn't implemented

        for (String flag : uniqueFlags)
        {
            res += UserFlags.fromName(flag).getValue();
        }

        return res;
    }
}
