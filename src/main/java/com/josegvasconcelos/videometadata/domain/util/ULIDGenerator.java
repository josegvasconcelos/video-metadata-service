package com.josegvasconcelos.videometadata.domain.util;

import de.huxhorn.sulky.ulid.ULID;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ULIDGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return new ULID().nextULID();
    }
}
