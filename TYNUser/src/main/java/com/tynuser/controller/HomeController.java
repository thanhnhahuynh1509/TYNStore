package com.tynuser.controller;

import com.tynentity.Category;
import com.tynentity.Customer;
import com.tynentity.Product;
import com.tynuser.service.CategoryService;
import com.tynuser.service.CustomerService;
import com.tynuser.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final ProductService productService;
    private final CustomerService customerService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CustomerService customerService, CategoryService categoryService) {
        this.productService = productService;
        this.customerService = customerService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String home(Model model) {
        List<Product> products = productService.listAll().stream().limit(8).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("details")
    public String details(Integer id, Model model) {
        Product product = productService.get(id);
        model.addAttribute("product", product);
        return "details";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String register(Customer customer, RedirectAttributes redirectAttributes) {
        Customer customerDb = customerService.getByUserName(customer.getUsername());

        if(customerDb != null) {
            redirectAttributes.addFlashAttribute("message", "Tài khoản đã tồn tại");
            return "redirect:/register";
        }

        customerService.saveOrUpdate(customer);

        return "redirect:/login";
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(required = false, defaultValue = "0") Integer id, Model model) {
        List<Product> products = productService.listAll();

        if(id > 0) {
            products = products.stream().filter(m -> m.getCategory().getId().equals(id)).collect(Collectors.toList());
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.listAll());
        return "shop";
    }


}
