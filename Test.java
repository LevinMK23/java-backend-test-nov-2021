package com.geekbrains.mybatis;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;

import java.util.List;

public class Test {

    public static void main(String[] args) {

        MyBatisDbService dbService = new MyBatisDbService();
        ProductsMapper productsMapper = dbService.getProductsMapper();
        CategoriesMapper categoriesMapper = dbService.getCategoriesMapper();

//1.Добавление категории
        Categories category = new Categories();
        category.setId(5);
        category.setTitle("Car");
        categoriesMapper.insert(category);

//2.Добавление 3-х продуктов
        Products product1 = new Products();
        product1.setTitle("Lada");
        product1.setPrice(400000);
        product1.setCategoryId(3);
        product1.setId(6);
        productsMapper.insert(product1);

        Products product2 = new Products();
        product2.setTitle("UAZ");
        product2.setPrice(300000);
        product2.setCategoryId(3);
        product2.setId(7);
        productsMapper.insert(product2);

        Products product3 = new Products();
        product3.setTitle("KIA");
        product3.setPrice(500000);
        product3.setCategoryId(3);
        product3.setId(8);
        productsMapper.insert(product3);

//3.Найти все продукты 1 категории
        ProductsExample firstCategory = new ProductsExample();
        firstCategory.createCriteria()
                .andCategoryIdEqualTo(1);

        List<Products> productsFirstCategory = productsMapper.selectByExample(firstCategory);
        System.out.println(firstCategory);
//4.Найти все продукты дешевле 1000
        ProductsExample cheaperThan1000 = new ProductsExample();
        cheaperThan1000.createCriteria()
                .andPriceLessThan(1000);

        List<Products> productsLessThan1000 = productsMapper.selectByExample(cheaperThan1000);
        System.out.println(productsLessThan1000);

//5.Найти все продукты от a до h
        ProductsExample fromAToH = new ProductsExample();
        fromAToH.createCriteria()
                .andTitleBetween("A", "H");

        List<Products> productsFromAToH = productsMapper.selectByExample(fromAToH);
        System.out.println(productsFromAToH);
    }
}