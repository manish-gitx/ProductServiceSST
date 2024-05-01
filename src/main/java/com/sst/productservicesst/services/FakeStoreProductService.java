package com.sst.productservicesst.services;

import com.sst.productservicesst.dtos.FakeStoreProductDto;
import com.sst.productservicesst.models.Category;
import com.sst.productservicesst.models.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    @Override
    public Product getProductById(Long id) {
        ////Call the FakeStore API to get the product with give id.
        RestTemplate restTemplate = new RestTemplate();
        FakeStoreProductDto fakeStoreProductDto =
                restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);


//        convert FakeStoreProductDto object to Product object.
        Product product = new Product();
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

    }

    public List<Product> getAllProducts(){
        RestTemplate restTemplate =new RestTemplate();
        FakeStoreProductDto[] fakeStoreProductDtos=// sending an array
                restTemplate.getForObject("https://fakestoreapi.com/products",FakeStoreProductDto[].class);
        List<Product> products =new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos ){
            products.add(convertFakeStoreProductDtoToProduct(fakeStoreProductDto));

        }
        return products;
    }
    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImage(fakeStoreProductDto.getImage());
        Category category=new Category();
        category.setDescription(fakeStoreProductDto.getCategory());
        category.setId(fakeStoreProductDto.getId());
        category.setTitle(fakeStoreProductDto.getTitle());
        product.setCategory(category);
        return product;

    }
}
