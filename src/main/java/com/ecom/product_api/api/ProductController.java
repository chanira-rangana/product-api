package com.ecom.product_api.api;

import com.ecom.product_api.dto.request.RequestProductDto;
import com.ecom.product_api.service.ProductService;
import com.ecom.product_api.util.StandardResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products-service/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<StandardResponse> createProduct(
            @RequestParam("data") String data,
            @RequestParam("image") MultipartFile image
    ) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RequestProductDto dto = objectMapper.readValue(data, RequestProductDto.class);
        productService.createProduct(dto, image);
        return new ResponseEntity<>(
                new StandardResponse(201, null, "product saved.."),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<StandardResponse> updateProduct(
            @RequestBody RequestProductDto dto, @PathVariable String productId) {
        productService.updateProduct(dto, productId);
        return new ResponseEntity<>(
                new StandardResponse(201, null, "product updated.."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<StandardResponse> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(
                new StandardResponse(204, null, "product deleted.."),
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("/find-by-id/{productId}")
    public ResponseEntity<StandardResponse> findProductById(@PathVariable String productId) {

        return new ResponseEntity<>(
                new StandardResponse(200, productService.findProductById(productId), "product data.."),
                HttpStatus.OK
        );
    }

    @GetMapping("/search-products")
    public ResponseEntity<StandardResponse> searchAllProducts(
            @RequestParam String searchText, @RequestParam int page, @RequestParam int size) {

        return new ResponseEntity<>(
                new StandardResponse(200, productService.searchAllProducts(searchText, page, size), "product list.."),
                HttpStatus.OK
        );
    }

    @PutMapping("/update-image/{imageId}")
    public ResponseEntity<StandardResponse> updateImage(
            @PathVariable String imageId,
            @RequestParam("image") MultipartFile file
    ) {
        productService.updateImage(imageId, file);
        return new ResponseEntity<>(
                new StandardResponse(201, null, "image updated.."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/delete-image-by-id/{imageId}")
    public ResponseEntity<StandardResponse> deleteImage(@PathVariable String imageId) {
        productService.deleteImage(imageId);
        return new ResponseEntity<>(
                new StandardResponse(204, null, "image deleted.."),
                HttpStatus.NO_CONTENT
        );
    }
}