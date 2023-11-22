package bg.petarh.noser.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.petarh.noser.coins.CoinManagement;
import bg.petarh.noser.order.chain.ChangeDispatchChecker;
import bg.petarh.noser.order.chain.ChangeDispatcher;
import bg.petarh.noser.order.chain.OrderFinish;
import bg.petarh.noser.order.chain.OrderStart;
import bg.petarh.noser.order.chain.ProductDispatchChecker;
import bg.petarh.noser.order.chain.ProductDispatcher;
import bg.petarh.noser.order.chain.ProductSelect;
import bg.petarh.noser.order.chain.SufficientCoinChecker;
import bg.petarh.noser.services.ProductService;

@Component
public class PurchaseOrderChain {

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderManagement orderManagement;
    @Autowired
    private CoinManagement coinManagement;

    private final PurchaseOrder orderStart = new OrderStart(); // Do you want to purchase ?
    private final PurchaseOrder productSelect = new ProductSelect(); // Select drink
    private final PurchaseOrder productDispatchChecker = new ProductDispatchChecker(productService, orderManagement); // can dispatch drink ?
    private final PurchaseOrder sufficientCoinChecker = new SufficientCoinChecker(orderManagement, coinManagement); // can the drink be paid
    private final PurchaseOrder changeDispatchChecker = new ChangeDispatchChecker(orderManagement, coinManagement);  // can return change ?
    private final PurchaseOrder productDispatcher = new ProductDispatcher(orderManagement); // dispatch drink
    private final PurchaseOrder changeDispatcher = new ChangeDispatcher(orderManagement, coinManagement); // return change
    private final PurchaseOrder orderFinish = new OrderFinish(coinManagement); // done with order

    @Autowired
    PurchaseOrderChain() {
        setupChainOrder();
    }

    private void setupChainOrder() {
        orderStart.setNext(productSelect);
        productSelect.setNext(productDispatchChecker);
        productDispatchChecker.setNext(sufficientCoinChecker);
        sufficientCoinChecker.setNext(changeDispatchChecker);
        changeDispatchChecker.setNext(productDispatcher);
        productDispatcher.setNext(changeDispatcher);
        changeDispatcher.setNext(orderFinish);
    }

    public PurchaseOrder selectPurchaseOrder(PurchaseOrderSelectionType type) {
        return switch (type) {
            case START_PURCHASE -> this.orderStart;
            case SELECT_DRINK -> this.productSelect;
            case DRINK_DISPATCH_CHECK -> this.productDispatchChecker;
            case SUFFICIENT_COIN_CHECK -> this.sufficientCoinChecker;
            case CHANGE_RETURN_CHECK -> this.changeDispatchChecker;
            case DISPATCH_DRINK -> this.productDispatcher;
            case DISPATCH_CHANGE -> this.changeDispatcher;
            case ORDER_FINISH -> this.orderFinish;
        };
    }

}
