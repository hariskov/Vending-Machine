package bg.petarh.vending.order.chain;

import bg.petarh.vending.entities.Product;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrder;
import bg.petarh.vending.rest.responses.DrinkSelectUnavailableResponse;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.vending.services.ProductService;

public class ProductDispatchChecker extends PurchaseOrder {

    private ProductService productService;
    private OrderManagement orderManagement;

    public ProductDispatchChecker(ProductService productService, OrderManagement orderManagement) {
        this.orderManagement = orderManagement;
        this.productService = productService;
    }

    @Override
    public PurchaseOrderHandlingResponse handle() {
        Product product = orderManagement.getCurrentOrder().getSelectedProduct();

        boolean hasProduct = productService.isProductAvailable(product);
        if (hasProduct) {
            return super.getNextPurchaseOrder().handle();
        }

        return new DrinkSelectUnavailableResponse();
    }
}
