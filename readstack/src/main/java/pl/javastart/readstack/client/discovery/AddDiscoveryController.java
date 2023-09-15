package pl.javastart.readstack.client.discovery;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.javastart.readstack.domain.api.CategoryName;
import pl.javastart.readstack.domain.api.CategoryService;
import pl.javastart.readstack.domain.api.DiscoverySaveRequest;
import pl.javastart.readstack.domain.api.DiscoveryService;
import pl.javastart.readstack.domain.discovery.Discovery;

import java.io.IOException;
import java.util.List;

@WebServlet("/discovery/add")
@ServletSecurity(
        httpMethodConstraints = {
                @HttpMethodConstraint(value = "GET", rolesAllowed = "USER"),
                @HttpMethodConstraint(value = "POST", rolesAllowed = "USER")
        }
)
public class AddDiscoveryController extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    private DiscoveryService discoveryService = new DiscoveryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryName> categories = categoryService.findAllCategoryNames();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/views/add-discovery.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiscoverySaveRequest saveRequest = createSaveRequest(request);
        discoveryService.add(saveRequest);
        response.sendRedirect(request.getContextPath());
    }

    private DiscoverySaveRequest createSaveRequest(HttpServletRequest request) {
        String title = request.getParameter("title");
        String url = request.getParameter("url");
        String description = request.getParameter("description");
        Integer categoryId = Integer.valueOf(request.getParameter("categoryId"));
        String loggedInUsername = request.getUserPrincipal().getName();
        return new DiscoverySaveRequest(title, url, description, categoryId, loggedInUsername);
    }
}
