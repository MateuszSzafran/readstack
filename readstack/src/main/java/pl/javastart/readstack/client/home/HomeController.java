package pl.javastart.readstack.client.home;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.javastart.readstack.domain.api.CategoryName;
import pl.javastart.readstack.domain.api.CategoryService;
import pl.javastart.readstack.domain.api.DiscoveryBasicInfo;
import pl.javastart.readstack.domain.api.DiscoveryService;

import java.io.IOException;
import java.util.List;

@WebServlet("")
public class HomeController extends HttpServlet {
    private final DiscoveryService discoveryService = new DiscoveryService();
    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DiscoveryBasicInfo> discoveries = discoveryService.findAll();
        List<CategoryName> allCategoryNames = categoryService.findAllCategoryNames();
        request.setAttribute("discoveries", discoveries);
        request.setAttribute("categories", allCategoryNames);
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }

}
