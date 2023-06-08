package prompteditor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prompteditor.service.UserCategoryService;

import java.util.logging.Logger;

@Controller
public class IndexController {
    private static final Logger logger = Logger.getLogger(IndexController.class.getName());
    private final UserCategoryService userCategoryService;

    public IndexController(UserCategoryService userCategoryService) {
        this.userCategoryService = userCategoryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        logger.info("Indexing model " + model);
        var userCategories = userCategoryService.getUserCategories();
        model.addAttribute("prompt", userCategories.getPrompt().replaceAll("\n", "\r\n"));
        logger.info(userCategories.getPrompt());
        return "index";
    }

    @PostMapping("/modify")
    public String modify(@RequestParam("message") String message, Model model) {
        logger.info("Modifying model " + message);
        userCategoryService.updateUserCategories(2L, message);
        var userCategories = userCategoryService.getUserCategories();
        model.addAttribute("prompt", userCategories.getPrompt());
        return "index";
    }
}
