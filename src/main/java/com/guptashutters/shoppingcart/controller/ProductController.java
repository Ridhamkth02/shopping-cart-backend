package com.guptashutters.shoppingcart.controller;


import com.guptashutters.shoppingcart.exceptions.ResourceNotFoundException;
import com.guptashutters.shoppingcart.model.Product;
import com.guptashutters.shoppingcart.requests.AddProductRequest;
import com.guptashutters.shoppingcart.response.ApiResponse;
import com.guptashutters.shoppingcart.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("success", products));

    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try{
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add Product success", theProduct));
        } catch(Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

        }

    }

}
