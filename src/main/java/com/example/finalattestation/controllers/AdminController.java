package com.example.finalattestation.controllers;

import com.example.finalattestation.enumm.Status;
import com.example.finalattestation.models.*;
import com.example.finalattestation.repository.CategoryRepository;
import com.example.finalattestation.repository.OrderRepository;
import com.example.finalattestation.services.OrderService;
import com.example.finalattestation.services.PersonService;
import com.example.finalattestation.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    private final PersonService personService;
    private final ProductService productService;
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    public AdminController(PersonService personService, ProductService productService, OrderService orderService, OrderRepository orderRepository, CategoryRepository categoryRepository) {
        this.personService = personService;
        this.productService = productService;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
    }
    @GetMapping("admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category",categoryRepository.findAll());
        return "product/addProduct";
    }

    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one, @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three, @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("category",categoryRepository.findAll());
            return "product/addProduct";
        }
        if(file_one!=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "."+ file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath+"/"+resultFileName));
            Image image =new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_two!=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "."+ file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath+"/"+resultFileName));
            Image image =new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_three!=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "."+ file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath+"/"+resultFileName));
            Image image =new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_four!=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "."+ file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath+"/"+resultFileName));
            Image image =new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_five!=null){
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "."+ file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath+"/"+resultFileName));
            Image image =new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product,category_db);

        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("products", productService.getAllProduct());
        return "admin";
    }

    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }
    @PostMapping("/admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return  "redirect:/admin";
    }

    // Метод передающий данные заказов на форму "/user/editOrder"
    @GetMapping("/admin/order/edit")
    public String orderUser(Model model){
        model.addAttribute("orders", orderService.getAllOrder());
        return "user/searchOrder";
    }

//     Метод для поиска по последним 4 символам
    @PostMapping("admin/order/edit/search")
    public String orderSearch(@RequestParam("search") String search, Model model){
        model.addAttribute("orders", orderService.getAllOrder());
        model.addAttribute("search_order", orderRepository.findByLast4DigitsOfOrderNumber(search));
        model.addAttribute("value_search", search);
        return "user/searchOrder";
    }
    //Метод для входа на страницу редактироания заказа
    @GetMapping("admin/order/edit/{id}")
    public String editOrder(Model model, @PathVariable("id") int id){
        model.addAttribute("order", orderService.getOrderId(id));
        model.addAttribute("status", Status.values());
        return "user/editOrder";
    }

    //Метод для редактироания заказа
    @PostMapping("/admin/order/edit/{id}")
    public String editOrder(@ModelAttribute("order") @Valid Order order, @PathVariable("id") int id){
        orderService.updateOrder(id, order);
        return  "redirect:/admin/order/edit";
    }

    // Метод для удаления заказа
    @GetMapping("admin/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id){
        orderService.deleteOrder(id);
        return "redirect:/admin/order/edit";
    }

    // Метод передающий данные пользователей на форму "//persons"
    @GetMapping("/admin/persons")
    public String personUser(Model model){
        model.addAttribute("persons", personService.getAllPerson());
        return "/persons";
    }

    @GetMapping("admin/persons/edit/{id}")
    public String editPersons(Model model, @PathVariable("id") int id){
        model.addAttribute("persons", personService.getPersonId(id));
        return "/editPersons";
    }
    //Метод для редактироания данных пользователя
    @PostMapping("/admin/persons/edit/{id}")
    public String editPersons(@ModelAttribute("persons") @Valid String person, @PathVariable("id") int id){
        personService.updatePersonRole(id, person);
        return  "redirect:/admin/persons";
    }

    // Метод для удаления пользователя
    @GetMapping("admin/persons/delete/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/admin/persons";
    }

}
