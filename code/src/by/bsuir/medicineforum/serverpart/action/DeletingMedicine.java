package by.bsuir.medicineforum.serverpart.action;

import by.bsuir.medicineforum.database.DrugDao;
import by.bsuir.medicineforum.database.dao.AbstractDao;
import by.bsuir.medicineforum.entity.Drug;
import by.bsuir.medicineforum.exception.ApplicationException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements interface "Action" for deleting drug.
 *
 * @author Nikita
 * @version 1.0
 * @since 14.11.2018
 */
public class DeletingMedicine implements Action {

    /**
     * Logger for debug and error.
     */
    private static Logger logger;

    /**
     * Public default constructor.
     */
    public DeletingMedicine() {

        logger = LogManager.getLogger(DeletingMedicine.class);

    }

    /**
     * {@inheritDoc}
     */
    public void execute(final HttpServletRequest request,
                        final HttpServletResponse response)
            throws IOException, ServletException {

        final String drugName = request.getParameter("medicine_name");
        final AbstractDao dao = new DrugDao();

        List<Drug> drugs = new ArrayList<>();

        try {

            ((DrugDao) dao).delete(drugName);
            drugs = ((DrugDao) dao).select();

        } catch (ApplicationException e) {

            logger.log(Level.ERROR, e.getMessage());
            e.printStackTrace();

        }

        request.getSession().removeAttribute("drugs");
        request.getSession().setAttribute("drugs", drugs);
        request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp")
                .forward(request, response);

    }

}
