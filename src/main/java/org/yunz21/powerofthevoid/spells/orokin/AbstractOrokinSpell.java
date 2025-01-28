package org.yunz21.powerofthevoid.spells.orokin;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;

public abstract class AbstractOrokinSpell extends AbstractSpell{
    @Override
    public boolean allowLooting() {
        return false;
    }
}
