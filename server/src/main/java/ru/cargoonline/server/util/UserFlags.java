package ru.cargoonline.server.util;

public enum UserFlags
{
    undefined(0),
    active(1),
    deleted(2),
    superuser(4);

    private int value;

    private UserFlags(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public static UserFlags fromName(String name)   // exception wrapped valueOf
    {
        try
        {
            return UserFlags.valueOf(name);
        }
        catch (IllegalArgumentException e)
        {
            return UserFlags.undefined;
        }
    }
}
