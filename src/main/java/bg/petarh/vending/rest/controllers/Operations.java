package bg.petarh.vending.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import bg.petarh.vending.coins.Coin;
import bg.petarh.vending.coins.CoinManagement;
import bg.petarh.vending.entities.Product;
import bg.petarh.vending.order.OrderManagement;
import bg.petarh.vending.order.PurchaseOrderChain;
import bg.petarh.vending.order.PurchaseOrderSelectionType;
import bg.petarh.vending.order.chain.ProductSelect;
import bg.petarh.vending.rest.responses.PurchaseOrderHandlingResponse;
import bg.petarh.vending.services.ProductManagementService;
import bg.petarh.vending.state.StateService;

@RestController(value = "/")
public class Operations {

    @Autowired
    private PurchaseOrderChain purchaseOrderChain;

    @Autowired
    private ProductManagementService productManagementService;

    @Autowired
    private OrderManagement orderManagement;

    @Autowired
    private CoinManagement coinManagement;

    @Autowired
    private StateService stateService;

    @PostMapping(value = "/insertCoin")
    public ResponseEntity<Integer> insertCoin(@RequestBody Coin insertedCoin) {
        this.coinManagement.insertCoin(insertedCoin);
        purchaseOrderChain.selectPurchaseOrder(PurchaseOrderSelectionType.DRINK_DISPATCH_CHECK);
        return ResponseEntity.ok(this.coinManagement.getCurrentAmount());
    }

    @GetMapping(value = "/returnChange")
    public ResponseEntity<PurchaseOrderHandlingResponse> returnChange() {
        PurchaseOrderHandlingResponse handleResult = purchaseOrderChain.selectPurchaseOrder(PurchaseOrderSelectionType.DISPATCH_CHANGE)
                .handle();
        return ResponseEntity.ok(handleResult);
    }

    @PostMapping(value = "/selectDrink")
    public ResponseEntity<PurchaseOrderHandlingResponse> selectDrink(Product product) {
        orderManagement.selectProduct(product);

        ProductSelect productSelect = (ProductSelect) purchaseOrderChain.selectPurchaseOrder(PurchaseOrderSelectionType.SELECT_DRINK);
        PurchaseOrderHandlingResponse handleResult = productSelect.handle();

        return ResponseEntity.ok(handleResult);
    }

    @GetMapping(value = "/getProducts")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productManagementService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/getStateInformation")
    public ResponseEntity<String> getStateInformation() {
        String state = stateService.obtainState();
        return ResponseEntity.ok(state);
    }
}
