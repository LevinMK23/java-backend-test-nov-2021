package com.geekbrains.mybatis;

import java.util.List;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;

public class Test {
    public static void main(String[] args) {

        MyBatisDbService dbService = new MyBatisDbService();
        ProductsMapper mapper = dbService.getProductsMapper();
        CategoriesMapper mapperForCategories = dbService.getCategoriesMapper();

        //1. Добавить в БД через код 3 продукта и 1 категорию
        Categories book = new Categories();
        book.setTitle("Book");
        book.setId(3L);
        mapperForCategories.insert(book);

        Products book1 = new Products();
        book1.setTitle("Something Wicked This Way Comes");
        book1.setCategoryId(3L);
        book1.setPrice(250);

        Products book2 = new Products();
        book2.setTitle("Tunnel in the Sky");
        book2.setCategoryId(3L);
        book2.setPrice(350);

        Products book3 = new Products();
        book3.setTitle("The Goblin Reservation");
        book3.setCategoryId(3L);
        book3.setPrice(300);

        mapper.insert(book1);
        mapper.insert(book2);
        mapper.insert(book3);

        //2. Найти все продукты 1 категории

        ProductsExample exampleFirstCategory = new ProductsExample();
        exampleFirstCategory.createCriteria().andCategoryIdEqualTo(1L);

        List<Products> productsFromFirstCategory = mapper.selectByExample(exampleFirstCategory);
        System.out.println(productsFromFirstCategory);


        //3. Найти все продукты дешевле 1000

        ProductsExample exampleLessThan1000 = new ProductsExample();
        exampleLessThan1000.createCriteria().andPriceLessThan(1000);

        List<Products> productsLessThan1000 = mapper.selectByExample(exampleLessThan1000);
        System.out.println(productsLessThan1000);

        //4. Найти все продукты от a до h

        ProductsExample exampleFromAToH = new ProductsExample();
        exampleFromAToH.createCriteria().andTitleBetween("a", "h");

        List<Products> productsFromAToH = mapper.selectByExample(exampleFromAToH);
        System.out.println(productsFromAToH);
    }
}
