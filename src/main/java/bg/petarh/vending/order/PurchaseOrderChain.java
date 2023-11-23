package bg.petarh.vending.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.order.chain.ChangeDispatchChecker;
import bg.petarh.vending.order.chain.ChangeDispatcher;
import bg.petarh.vending.order.chain.OrderFinish;
import bg.petarh.vending.order.chain.OrderStart;
import bg.petarh.vending.order.chain.ProductDispatchChecker;
import bg.petarh.vending.order.chain.ProductDispatcher;
import bg.petarh.vending.order.chain.ProductSelect;
import bg.petarh.vending.order.chain.SufficientCoinChecker;
import bg.petarh.vending.services.ProductManagementService;
import bg.petarh.vending.services.ProductOperationService;

@Component
public class PurchaseOrderChain {

    private final ProductManagementService productManagementService;
    private final OrderManagement orderManagement;
    private final CoinManagement coinManagement;
    private final ProductOperationService productOperationService;

    private PurchaseOrder orderStart; // Do you want to purchase ?
    private PurchaseOrder productSelect; // Select drink
    private PurchaseOrder productDispatchChecker; // can dispatch drink ?
    private PurchaseOrder sufficientCoinChecker; // can the drink be paid
    private PurchaseOrder changeDispatchChecker;  // can return change ?
    private PurchaseOrder productDispatcher; // dispatch drink
    private PurchaseOrder changeDispatcher; // return change
    private PurchaseOrder orderFinish; // done with order

    @Autowired
    PurchaseOrderChain(ProductOperationService productOperationService, ProductManagementService productManagementService, OrderManagement orderManagement,
                       CoinManagement coinManagement) {
        this.productManagementService = productManagementService;
        this.productOperationService = productOperationService;
        this.orderManagement = orderManagement;
        this.coinManagement = coinManagement;
        constructFields();
        setupChainOrder();
    }

    private void constructFields() {
        this.orderStart = new OrderStart();
        this.productSelect = new ProductSelect();
        this.productDispatchChecker = new ProductDispatchChecker(productManagementService, orderManagement);
        this.sufficientCoinChecker = new SufficientCoinChecker(orderManagement, coinManagement);
        this.changeDispatchChecker = new ChangeDispatchChecker(orderManagement, coinManagement);
        this.productDispatcher = new ProductDispatcher(productOperationService, orderManagement);
        this.changeDispatcher = new ChangeDispatcher(orderManagement, coinManagement);
        this.orderFinish = new OrderFinish();
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
