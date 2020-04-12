package com.dioler.springmvcrest.bootstrap;

import com.dioler.springmvcrest.domain.Category;
import com.dioler.springmvcrest.domain.Customer;
import com.dioler.springmvcrest.repositories.CategoryRepository;
import com.dioler.springmvcrest.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {

    public final CategoryRepository categoryRepository;
    public final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCategories();

        loadCustomers();
    }

    private void loadCustomers() {
        Customer paco = new Customer();
        paco.setFirstName("Francisco");
        paco.setLastName("Silvela");
        Customer jorge = new Customer();
        jorge.setFirstName("Jorge");
        jorge.setLastName("Garre");
        Customer maria = new Customer();
        maria.setFirstName("María");
        maria.setLastName("Villa");

        customerRepository.save(paco);
        customerRepository.save(jorge);
        customerRepository.save(maria);

        System.out.println("Customers inserted in startup: " + customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Categories inserted in startup: " + categoryRepository.count());
    }
}
