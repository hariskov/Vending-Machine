package bg.petarh.noser.order.chain;

import bg.petarh.noser.entities.Product;
import bg.petarh.noser.order.OrderManagement;
import bg.petarh.noser.order.PurchaseOrder;
import bg.petarh.noser.rest.responses.DrinkSelectUnavailableResponse;
import bg.petarh.noser.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.noser.services.ProductService;

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
