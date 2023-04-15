package com.example.finalattestation.services;


import com.example.finalattestation.models.Category;
import com.example.finalattestation.models.Product;
import com.example.finalattestation.repository.PersonRepository;
import com.example.finalattestation.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Данный метод создает лист с товарами
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    //Данный метод позовляет получить товар по id
    public Product getProductId(int id){
        Optional<Product> optionalProduct=productRepository.findById(id);
        return  optionalProduct.orElse(null);
    }

    //Данный метод позволяет сохранить товар
    @Transactional
    public void saveProduct(Product product, Category category){
        product.setCategory(category);
        productRepository.save(product);
    }
    @Transactional
    //Данный метод позволяет обновить данные о товаре
    public  void updateProduct(int id, Product product){
        product.setId(id);
        productRepository.save(product);
    }

    @Transactional
    //Данный метод позволяет удалить товар по ID
    public  void deleteProduct(int id){
        productRepository.deleteById(id);
    }

}
