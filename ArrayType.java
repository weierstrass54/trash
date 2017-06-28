package ru.opentech.api.shop.component.hibernate;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.*;

public class ArrayType extends AbstractSingleColumnStandardBasicType<String[]> {

    public static final ArrayType INSTANCE = new ArrayType();

    public ArrayType() {
        super( ArrayTypeDescriptor.INSTANCE, IterableTypeDescriptor.INSTANCE );
    }

    @Override
    public String getName() {
        return "string[]";
    }

    @Override
    protected boolean registerUnderJavaType() {
        return true;
    }

    @Override
    public String[] getRegistrationKeys() {
        return new String[] { getName(), "string[]", String[].class.getName() };
    }


    private static class ArrayTypeDescriptor implements SqlTypeDescriptor {

        static final ArrayTypeDescriptor INSTANCE = new ArrayTypeDescriptor();

        @Override
        public int getSqlType() {
            return Types.ARRAY;
        }

        @Override
        public boolean canBeRemapped() {
            return true;
        }

        @Override
        public <X> ValueBinder<X> getBinder( JavaTypeDescriptor<X> javaTypeDescriptor ) {
            return new BasicBinder<X>( javaTypeDescriptor, this ) {
                @Override
                protected void doBind(
                    PreparedStatement st, X value, int index, WrapperOptions options
                ) throws SQLException {
                    st.setObject( index, javaTypeDescriptor.unwrap( value, Iterable.class, options ) );
                }

                @Override
                protected void doBind(
                    CallableStatement st, X value, String name, WrapperOptions options
                ) throws SQLException {
                    st.setObject( name, javaTypeDescriptor.unwrap( value, Iterable.class, options ) );
                }
            };
        }

        @Override
        public <X> ValueExtractor<X> getExtractor( JavaTypeDescriptor<X> javaTypeDescriptor ) {
            return new BasicExtractor<X>( javaTypeDescriptor, this ) {
                @Override
                protected X doExtract( ResultSet rs, String name, WrapperOptions options ) throws SQLException {
                    return javaTypeDescriptor.wrap( rs.getArray( name ), options );
                }

                @Override
                protected X doExtract( CallableStatement statement, int index, WrapperOptions options ) throws SQLException {
                    return javaTypeDescriptor.wrap( statement.getArray( index ), options );
                }

                @Override
                protected X doExtract( CallableStatement statement, String name, WrapperOptions options ) throws SQLException {
                    return javaTypeDescriptor.wrap( statement.getArray( name ), options );
                }
            };
        }

    }

}
