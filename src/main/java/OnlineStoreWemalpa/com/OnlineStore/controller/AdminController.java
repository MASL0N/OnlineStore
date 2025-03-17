package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.model.*;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductSizeRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.UserRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService service;
    private final OrderService orderService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductSizeRepository productSizeRepository;
    private final MaterialService materialService;
    private final PrintTypeService printTypeService;
    private final FileUploadService fileUploadService;

    @GetMapping("/product-admin")
    public String adminDashboard(Model model) {
        List<Product> products = service.findAllProduct();

        // Фильтруем обычные и кастомизированные товары
        List<Product> regularProducts = products.stream()
                .filter(p -> !Boolean.TRUE.equals(p.getCustom()))
                .toList();

        List<Product> customProducts = products.stream()
                .filter(p -> Boolean.TRUE.equals(p.getCustom()))
                .toList();

        model.addAttribute("regularProducts", regularProducts);
        model.addAttribute("customProducts", customProducts);
        return "product-admin";
    }

    // Удаляем продукт по ID
    @GetMapping("/product-delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            boolean success = service.deleteProductAdmin(id);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "Товар успешно удален.");
            }
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/api/v1/admin/product-admin";  // Перенаправляем на страницу с продуктами
    }

    // Редактируем продукт по ID
    @GetMapping("/product-edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        Product product = service.findById(id);  // Получаем продукт по ID
        model.addAttribute("product", product);  // Передаем продукт в модель
        return "product-edit";  // Страница для редактирования
    }

    // Обработчик изменения продукта
    @PostMapping("/product-edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        Product existingProduct = service.findById(id); // Находим текущий продукт в БД

        if (product.getColor() == null) { // Если в форме не передали цвет
            product.setColor(existingProduct.getColor()); // Используем старый цвет
        }

        if (product.getMaterial() == null) { // Если в форме не передали цвет
            product.setMaterial(existingProduct.getMaterial()); // Используем старый цвет
        }

        if (product.getPrintType() == null) { // Если в форме не передали цвет
            product.setPrintType(existingProduct.getPrintType()); // Используем старый цвет
        }

        service.updateProduct(product); // Обновляем продукт
        return "redirect:/api/v1/admin/product-admin";
    }

    @GetMapping("/product-create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product()); // Добавляем пустого пользователя в модель
        return "product-create"; // Отображаем форму для создания нового пользователя
    }

    // Обработка формы создания нового пользователя
    @PostMapping("/product-create")
    public String createProduct(
            @ModelAttribute Product product,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam(value = "sizes", required = false) List<String> sizes,
            RedirectAttributes redirectAttributes) {

        try {
            // Загружаем изображения и создаем объекты ProductImage
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : images) {
                if (!file.isEmpty()) {
                    String imageUrl = fileUploadService.uploadFile(file);
                    ProductImage productImage = new ProductImage();
                    productImage.setImageUrl(imageUrl);
                    productImage.setProduct(product); // Устанавливаем продукт для изображения
                    productImage.setIsPrimary(false);  // По умолчанию ставим, что это не главное изображение
                    productImages.add(productImage);
                }
            }

            // Преобразуем размеры из строки в объекты ProductSize
            List<ProductSize> productSizes = new ArrayList<>();
            if (sizes != null) {
                for (String size : sizes) {
                    ProductSize productSize = new ProductSize();
                    productSize.setSize(size); // Устанавливаем размер
                    productSize.setProduct(product); // Устанавливаем продукт для размера
                    productSizes.add(productSize);
                }
            }

            // Устанавливаем список изображений и размеров в продукт
            product.setImages(productImages);
            product.setSizes(productSizes);

            // Сохраняем продукт с изображениями и размерами
            service.saveProductWithImagesAndSizes(product);

            redirectAttributes.addFlashAttribute("success", "Товар успешно создан!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании товара: " + e.getMessage());
        }

        return "redirect:/api/v1/admin/product-admin";
    }

    @GetMapping("/order-admin")
    public String adminDashboardOrder(Model model) {
        List<Order> orders = orderService.findAllOrder(); // Получаем список продуктов
        model.addAttribute("orders", orders); // Передаем данные в модель
        return "order-admin"; // Указывает на шаблон
    }

    @GetMapping("/order-delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);  // Вызываем сервис для удаления
        return "redirect:/api/v1/admin/order-admin";  // Перенаправляем на страницу с продуктами
    }

    @GetMapping("/order-edit/{id}")
    public String editOrder(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);  // Получаем продукт по ID
        model.addAttribute("order", order);  // Передаем продукт в модель
        return "order-edit";  // Страница для редактирования
    }

    @PostMapping("/order-edit/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute Order order) {
        Order existingOrder = orderService.findById(id); // Найти существующий заказ

        if (existingOrder == null) {
            throw new IllegalArgumentException("Order not found");
        }

        // Обновляем только изменяемые поля, НЕ трогаем user
        existingOrder.setStatus(order.getStatus());
        existingOrder.setOrderPrice(order.getOrderPrice());

        orderService.updateOrder(existingOrder); // Сохраняем изменения
        return "redirect:/api/v1/admin/order-admin";
    }

    @GetMapping("/user-admin")
    public String adminDashboardUser(Model model) {
        List<User> users = userService.findAllUser(); // Получаем список продуктов
        model.addAttribute("users", users); // Передаем данные в модель
        return "user-admin"; // Указывает на шаблон
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);  // Вызываем сервис для удаления
        return "redirect:/api/v1/admin/user-admin";  // Перенаправляем на страницу с продуктами
    }

    @GetMapping("/user-edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);  // Получаем продукт по ID
        model.addAttribute("user", user);  // Передаем продукт в модель
        return "user-edit";  // Страница для редактирования
    }

    @PostMapping("/user-edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        // Получаем существующего пользователя из базы данных
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        // Обновляем только измененные параметры
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setCity(user.getCity());
        existingUser.setAddress(user.getAddress());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setPostIndex(user.getPostIndex());
        existingUser.setRole(user.getRole());

        // Если password не изменился, не обновляем его
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());  // Обновляем пароль только если он был предоставлен
        }

        // Если orders не должны изменяться, не обновляем их, оставляем прежними
        existingUser.setOrders(existingUser.getOrders());  // Оставляем текущие заказы без изменений

        // Сохраняем изменения в базе данных
        userRepository.save(existingUser);

        return "redirect:/api/v1/admin/user-admin";  // Перенаправляем на страницу с пользователями
    }

    // Отображение формы для создания нового пользователя
    @GetMapping("/user-create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new User()); // Добавляем пустого пользователя в модель
        return "user-create"; // Отображаем форму для создания нового пользователя
    }

    // Обработка формы создания нового пользователя
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user) {
        // Проверка на существующего пользователя с таким email
        if (userService.existsByEmail(user.getEmail())) {
            // Если пользователь с таким email уже существует, возвращаем ошибку
            return "redirect:/api/v1/admin/user-create?error=emailExists";
        }

        // Сохраняем нового пользователя
        userService.createUser(user);  // Вызываем сервис для сохранения пользователя
        return "redirect:/api/v1/admin/user-admin";  // Перенаправляем на страницу с пользователями
    }

    @GetMapping("/custom-admin")
    public String adminDashboardCustom(Model model) {
        List<Material> materials = materialService.findAllMaterial(); // Получаем список продуктов
        model.addAttribute("materials", materials);
        List<PrintType> printTypes = printTypeService.findAllPrintType(); // Получаем список продуктов
        model.addAttribute("printTypes", printTypes);// Передаем данные в модель
        return "custom-admin"; // Указывает на шаблон
    }

    @GetMapping("/custom-material-delete/{id}")
    public String deleteMaterial(@PathVariable("id") Long id) {
        materialService.deleteMaterial(id);  // Вызываем сервис для удаления
        return "redirect:/api/v1/admin/custom-admin";  // Перенаправляем на страницу с продуктами
    }

    @GetMapping("/custom-material-edit/{id}")
    public String editMaterial(@PathVariable("id") Long id, Model model) {
        Material material = materialService.findById(id);  // Получаем продукт по ID
        model.addAttribute("material", material);  // Передаем продукт в модель
        return "custom-material-edit";  // Страница для редактирования
    }

    @PostMapping("/custom-material-edit/{id}")
    public String updateMaterial(@PathVariable("id") Long id, @ModelAttribute("material") Material material) {
        // Получаем существующего пользователя из базы данных
        Material existingMaterial = materialService.findById(id);

        if (existingMaterial == null) {
            throw new IllegalArgumentException("Order not found");
        }

        existingMaterial.setName(material.getName());
        existingMaterial.setDescription(material.getDescription());

        materialService.updateMaterial(existingMaterial);

        return "redirect:/api/v1/admin/custom-admin";  // Перенаправляем на страницу с пользователями
    }

    // Отображение формы для создания нового пользователя
    @GetMapping("/custom-material-create")
    public String showCreateMaterialForm(Model model) {
        model.addAttribute("material", new Material()); // Добавляем пустого пользователя в модель
        return "custom-material-create"; // Отображаем форму для создания нового пользователя
    }

    // Обработка формы создания нового пользователя
    @PostMapping("/custom-material-create")
    public String createMaterial(@ModelAttribute("material") Material material) {
        if (materialService.existsByName(material.getName())) {
            return "redirect:/api/v1/admin/custom-material-create?error=nameExists";
        }

        materialService.saveMaterial(material);  // Вызываем сервис для сохранения пользователя
        return "redirect:/api/v1/admin/custom-admin";  // Перенаправляем на страницу с пользователями
    }

    @GetMapping("/custom-printType-delete/{id}")
    public String deletePrintType(@PathVariable("id") Long id) {
        printTypeService.deletePrintType(id);  // Вызываем сервис для удаления
        return "redirect:/api/v1/admin/custom-admin";  // Перенаправляем на страницу с продуктами
    }

    @GetMapping("/custom-printType-edit/{id}")
    public String editPrintType(@PathVariable("id") Long id, Model model) {
        PrintType printType = printTypeService.findById(id);  // Получаем продукт по ID
        model.addAttribute("printType", printType);  // Передаем продукт в модель
        return "custom-printType-edit";  // Страница для редактирования
    }

    @PostMapping("/custom-printType-edit/{id}")
    public String updatePrintType(@PathVariable("id") Long id,
                                  @ModelAttribute("printType") PrintType printType,
                                  @RequestParam("image") MultipartFile file) {
        PrintType existingPrintType = printTypeService.findById(id);

        if (existingPrintType == null) {
            throw new IllegalArgumentException("Принт не найден");
        }

        existingPrintType.setName(printType.getName());
        existingPrintType.setDescription(printType.getDescription());

        if (!file.isEmpty()) {
            String fileUrl = fileUploadService.uploadFile(file);
            existingPrintType.setImageUrl(fileUrl);
        }

        printTypeService.updatePrintType(existingPrintType);

        return "redirect:/api/v1/admin/custom-admin";
    }

    @GetMapping("/custom-printType-create")
    public String showCreatePrintTypeForm(Model model) {
        model.addAttribute("printType", new PrintType());
        return "custom-printType-create";
    }

    @PostMapping("/custom-printType-create")
    public String createPrintType(@ModelAttribute("printType") PrintType printType,
                                  @RequestParam("image") MultipartFile file) {
        if (printTypeService.existsByName(printType.getName())) {
            return "redirect:/api/v1/admin/custom-printType-create?error=nameExists";
        }

        // Загружаем изображение и сохраняем URL в объекте PrintType
        if (!file.isEmpty()) {
            String fileUrl = fileUploadService.uploadFile(file);
            printType.setImageUrl(fileUrl);
        }

        // Сохраняем новый принт в базу данных
        printTypeService.savePrintType(printType);
        return "redirect:/api/v1/admin/custom-admin";
    }
}