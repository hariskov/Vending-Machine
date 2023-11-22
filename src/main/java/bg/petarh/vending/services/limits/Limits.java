package bg.petarh.vending.services.limits;

import java.util.HashMap;
import java.util.Map;

class Limits {

    private final Map<LimitType, Limit> limits;

    private Limits(Map<LimitType, Limit> limits) {
        this.limits = limits;
    }

    Limit getLimit(LimitType limitType){
        return limits.get(limitType);
    }

    public static class Builder {

        private final Map<LimitType, Limit> limits;

        public Builder() {
            limits = new HashMap<>();
        }

        Builder addLimit(LimitType limitType, Limit limit) {
            limits.put(limitType, limit);
            return this;
        }

        Limits build() {
            return new Limits(limits);
        }
    }
}
