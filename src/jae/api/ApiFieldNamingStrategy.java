package jae.api;

import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class ApiFieldNamingStrategy implements FieldNamingStrategy
{
	@Override
	public String translateName(Field field)
	{
		return (field.getName().startsWith("_") ? field.getName().substring(1) : field.getName());
	}
}
