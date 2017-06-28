package ru.opentech.api.shop.component.hibernate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.ArrayMutabilityPlan;
import org.hibernate.type.descriptor.java.IncomparableComparator;
import org.springframework.util.StringUtils;

import java.util.*;

public class IterableTypeDescriptor extends AbstractTypeDescriptor<String[]> {

    public static final IterableTypeDescriptor INSTANCE = new IterableTypeDescriptor();

    @SuppressWarnings( "unchecked" )
    public IterableTypeDescriptor() {
        super( String[].class, ArrayMutabilityPlan.INSTANCE );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public String toString( String[] value ) {
        return "{" + StringUtils.arrayToCommaDelimitedString( value ) + "}";
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public String[] fromString( String string ) {
        if ( string == null ) {
            return null;
        }
        return StringUtils.split( string.substring( 1, string.length() - 1 ), "," );
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public Comparator<String[]> getComparator() {
        return IncomparableComparator.INSTANCE;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public <X> X unwrap( String[] value, Class<X> type, WrapperOptions options ) {
        if( value == null ) {
            return null;
        }
        return (X)value;
    }

    @Override
    public <X> String[] wrap( X value, WrapperOptions options ) {
        if( value == null ) {
            return null;
        }
        return (String[])value;
    }

}
