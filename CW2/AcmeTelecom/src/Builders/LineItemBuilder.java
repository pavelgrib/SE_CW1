package builders;

import com.acmetelecom.Call;
import com.acmetelecom.Cost;
import com.acmetelecom.LineItem;
import com.sun.istack.internal.Builder;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: yufeiwang
 * Date: 30/11/2012
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */

/*
Builder to build a LineItem object in DSL style
 */
public class LineItemBuilder implements Builder {
    private Call call;
    private BigDecimal callCost;

    public static LineItemBuilder aLineItem() {
        return new LineItemBuilder();
    }

    private LineItemBuilder() {
        this.call = null;
        this.callCost = null;
    }

    public LineItemBuilder withCall(Call call) {
        this.call = call;
        return this;
    }

    public LineItemBuilder withACostOf(BigDecimal callCost) {
        this.callCost = callCost;
        return this;
    }

    @Override
    public LineItem build() {
        // TODO Auto-generated method stub
        return new LineItem(new Cost(this.callCost), this.call);
    }
}
