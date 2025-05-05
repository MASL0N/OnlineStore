package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.Configuration.MyUserDetails;
import OnlineStoreWemalpa.com.OnlineStore.model.PrintType;
import OnlineStoreWemalpa.com.OnlineStore.model.Product;
import OnlineStoreWemalpa.com.OnlineStore.model.ProductImage;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.repository.ColorRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.MaterialRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.PrintTypeRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.BasketService;
import OnlineStoreWemalpa.com.OnlineStore.service.FileUploadService;
import OnlineStoreWemalpa.com.OnlineStore.service.PrintTypeService;
import OnlineStoreWemalpa.com.OnlineStore.service.impl.CustomizationServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("api/v1/customization")
@AllArgsConstructor
public class CustomizationController {


    private final CustomizationServiceImpl customizationService;

    private final MaterialRepository materialRepository;

    private final ColorRepository colorRepository;

    private final PrintTypeRepository printTypeRepository;

    private final ProductRepository productRepository;

    private final BasketService basketService;

    private final FileUploadService fileUploadService;

    private final PrintTypeService printTypeService;

    @GetMapping("/{productId}")
    public String showCustomizationPage(@PathVariable Long productId, Model model) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        model.addAttribute("product", product);  // Передаем продукт в модель
        model.addAttribute("materials", materialRepository.findAll());
        model.addAttribute("colors", colorRepository.findAll());
        model.addAttribute("printTypes", printTypeRepository.findAll());
        model.addAttribute("primaryImage", product.getPrimaryImage());
        model.addAttribute("sizes", product.getSizes());

        return "customization";
    }
    @PostMapping("/customize")
    public String customizeProduct(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestParam Long productId,
                                   @RequestParam Long materialId,
                                   @RequestParam Long colorId,
                                   @RequestParam Long printTypeId,
                                   @RequestParam Long sizeId,
                                   @RequestParam String primaryImage,
                                   @RequestParam(value = "redirect", required = false) String redirectUrl,
                                   RedirectAttributes redirectAttributes,
                                   @RequestParam("image") MultipartFile file) {
        // Проверяем, авторизован ли пользователь
        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("error", "Вы не авторизованы!");
            return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
        }

        // Получаем объект пользователя
        User user = ((MyUserDetails) userDetails).getUser();

        if (!file.isEmpty()) {
            PrintType printType = new PrintType();
            String fileUrl = fileUploadService.uploadFile(file);
            printType.setName("Принт, добавленный пользователем");
            printType.setImageUrl(fileUrl);
            printTypeService.savePrintType(printType);
            Product customizedProduct = customizationService.customizeProduct(productId, materialId, colorId, printType.getPrintTypeId());
            // Добавляем кастомизированный товар в корзину с выбранным размером
            boolean added = basketService.addToBasket(user, customizedProduct.getId(), sizeId, primaryImage);

            // Проверяем успешность добавления
            if (!added) {
                redirectAttributes.addFlashAttribute("error", "Не удалось добавить товар в корзину!");
            } else {
                redirectAttributes.addFlashAttribute("success", "Кастомизированный товар добавлен в корзину!");
            }
            return "redirect:/api/v1/basket";
        }
        else{
            Product customizedProduct = customizationService.customizeProduct(productId, materialId, colorId, printTypeId);
            // Добавляем кастомизированный товар в корзину с выбранным размером
            boolean added = basketService.addToBasket(user, customizedProduct.getId(), sizeId, primaryImage);

            // Проверяем успешность добавления
            if (!added) {
                redirectAttributes.addFlashAttribute("error", "Не удалось добавить товар в корзину!");
            } else {
                redirectAttributes.addFlashAttribute("success", "Кастомизированный товар добавлен в корзину!");
            }
            return "redirect:/api/v1/basket";
        }
    }
}
