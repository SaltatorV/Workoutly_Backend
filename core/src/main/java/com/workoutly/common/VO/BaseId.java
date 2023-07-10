package com.workoutly.common.VO;


public abstract class BaseId<ID> {
    private final ID id;

    protected BaseId(ID id) {
        this.id = id;
    }

    public String getId() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseId<?> baseId = (BaseId<?>) o;

        return id.equals(baseId.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

