package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.Configuration.MyUserDetails;
import OnlineStoreWemalpa.com.OnlineStore.model.*;
import OnlineStoreWemalpa.com.OnlineStore.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/newCustomProduct")
@AllArgsConstructor
public class CustomProduct {

    private final MaterialService materialService;
    private final FileUploadService fileUploadService;
    private final ColorService colorService;
    private final ProductService productService;
    private final PrintTypeService printTypeService;
    private final BasketService basketService;

    @GetMapping
    public String show(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("materials", materialService.findAllMaterial());
        return "new-custom-product"; // Страница с формой
    }

    @PostMapping
    public String handleFileUpload(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "redirect", required = false) String redirectUrl,
            RedirectAttributes redirectAttributes,
            @RequestParam(value = "printFrontFile", required = false) MultipartFile printFrontFile,
            @RequestParam(value = "printBackFile", required = false) MultipartFile printBackFile,
            @RequestParam("colorId") Long colorId,
            @RequestParam("material") String materialName,
            @RequestParam("size") String size,
            @RequestParam("category") String category,
            @RequestParam("printX") String printX,
            @RequestParam("printY") String printY,
            @RequestParam("printScale") String printScale,
            @RequestParam("printBackX") String printBackX,
            @RequestParam("printBackY") String printBackY,
            @RequestParam("printBackScale") String printBackScale,
            Model model
    ) {
        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("error", "Вы не авторизованы!");
            return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
        }

        System.out.println("printX: " + printX);
        System.out.println("printY: " + printY);
        System.out.println("category: " + category);

        System.out.println("printBackX: " + printBackX);
        System.out.println("printBackY: " + printBackY);
        System.out.println("printScaleBack: " + printBackScale);

        User user = ((MyUserDetails) userDetails).getUser();
        try {
            // Загружаем изображения
            String frontImageUrl = null;
            String backImageUrl = null;

            if (printFrontFile != null && !printFrontFile.isEmpty()) {
                frontImageUrl = fileUploadService.uploadFile(printFrontFile);
            }
            if (printBackFile != null && !printBackFile.isEmpty()) {
                backImageUrl = fileUploadService.uploadFile(printBackFile);
            }

            // Получаем сущности цвета и материала
            Color color = colorService.findById(colorId);
            Material material = materialService.findByNameAndClothingType(materialName, category);

            // Создаем продукт
            Product product = new Product();
            product.setName(category + " Custom");
            product.setDescription("Создан пользователем");
            product.setPrice(new BigDecimal("4999"));
            product.setGender("унисекс");
            product.setClothingType(category);
            product.setCustom(true);
            product.setActual(false);
            product.setColor(color);
            product.setMaterial(material);
            product.setPrintType(printTypeService.findById(6L));

            // Добавляем размер
            ProductSize productSize = new ProductSize(product, size);
            product.getSizes().add(productSize);

            // Добавляем картинку
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);
            productImage.setImageUrl("/img/" + category + "/white.png");
            productImage.setIsPrimary(true);
            product.getImages().add(productImage);

            // Проверка на наличие принтов
            if (frontImageUrl != null) {
                PrintCustom frontPrint = new PrintCustom();
                frontPrint.setX(printX);
                frontPrint.setY(printY);
                frontPrint.setScale(printScale);
                frontPrint.setOrientation("front");
                frontPrint.setImageUrl(frontImageUrl);
                frontPrint.setProduct(product);
                product.getPrints().add(frontPrint);
            }

            if (backImageUrl != null) {
                PrintCustom backPrint = new PrintCustom();
                backPrint.setX(printBackX);
                backPrint.setY(printBackY);
                backPrint.setScale(printBackScale);
                backPrint.setOrientation("back");
                backPrint.setImageUrl(backImageUrl);
                backPrint.setProduct(product);
                product.getPrints().add(backPrint);
            }

            // Сохраняем продукт с каскадом
            productService.saveProduct(product);

            boolean added = basketService.addToBasket(user, product.getId(), productSize.getId(), productImage.getImageUrl());

            // Проверяем успешность добавления
            if (!added) {
                redirectAttributes.addFlashAttribute("error", "Не удалось добавить товар в корзину!");
            } else {
                redirectAttributes.addFlashAttribute("success", "Кастомизированный товар добавлен в корзину!");
            }

            return "redirect:/api/v1/basket";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при создании товара: " + e.getMessage());
            return "redirect:/api/v1/newCustomProduct";
        }
    }

}

