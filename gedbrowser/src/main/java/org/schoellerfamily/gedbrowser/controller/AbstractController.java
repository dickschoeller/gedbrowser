package org.schoellerfamily.gedbrowser.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.schoellerfamily.gedbrowser.Users;
import org.schoellerfamily.gedbrowser.controller.exception.DataSetNotFoundException;
import org.schoellerfamily.gedbrowser.controller.exception.PersonNotFoundException;
import org.schoellerfamily.gedbrowser.controller.exception.SourceNotFoundException;
import org.schoellerfamily.gedbrowser.renderer.ApplicationInfo;
import org.schoellerfamily.gedbrowser.renderer.GedResourceNotFoundRenderer;
import org.schoellerfamily.gedbrowser.renderer.Renderer;
import org.schoellerfamily.gedbrowser.renderer.RenderingContext;
import org.schoellerfamily.gedbrowser.renderer.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Dick Schoeller
 */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractController {
    /** Logger. */
    private final transient Log logger = LogFactory.getLog(getClass());

    /** */
    @Autowired
    private transient ApplicationInfo applicationInfo;

    /** */
    @Autowired
    private transient Users users;

    /**
     * @return the rendering context
     */
    protected final RenderingContext createRenderingContext() {
        logger.debug("Creating RenderingContext");
        final Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        final User user = users.get(authentication.getName());
        final RenderingContextBuilder contextBuilder =
                new RenderingContextBuilder(
                        authentication, user, applicationInfo);
        return contextBuilder.build();
    }

    /**
     * @param request the request we're processing
     * @param exception the exception caught
     * @return the model and view
     */
    @ExceptionHandler({ PersonNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ModelAndView personNotFoundError(
            final HttpServletRequest request,
            final PersonNotFoundException exception) {
        logger.info("Handling exception: " + exception.getMessage());
        return createModelAndViewForException(request, exception,
                "personNotFound", HttpStatus.NOT_FOUND);
    }

    /**
     * @param request the request we're processing
     * @param exception the exception caught
     * @return the model and view
     */
    @ExceptionHandler({ SourceNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ModelAndView sourceNotFoundError(
            final HttpServletRequest request,
            final SourceNotFoundException exception) {
        logger.info("Handling exception: " + exception.getMessage());
        return createModelAndViewForException(request, exception,
                "sourceNotFound", HttpStatus.NOT_FOUND);
    }

    /**
     * @param request the request we're processing
     * @param exception the exception caught
     * @return the model and view
     */
    @ExceptionHandler({ DataSetNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ModelAndView dataSetNotFoundError(
            final HttpServletRequest request,
            final DataSetNotFoundException exception) {
        logger.info("Handling exception: " + exception.getMessage());
        return createModelAndViewForException(request, exception,
                "dataSetNotFound", HttpStatus.NOT_FOUND);
    }

    /**
     * @param request the request we're processing
     * @param exception the exception caught
     * @return the model and view
     */
    @ExceptionHandler({ Throwable.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ModelAndView error(final HttpServletRequest request,
            final Exception exception) {
        logger.error("Handling exception", exception);
        return createModelAndViewForException(request, exception, "exception",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param request the http request being served
     * @param exception the exception that occurred
     * @param viewName the view we will put up in response
     * @param status the status we are reporting
     * @return the model and view for displaying the page
     */
    private ModelAndView createModelAndViewForException(
            final HttpServletRequest request, final Exception exception,
            final String viewName, final HttpStatus status) {
        final ModelAndView mav = new ModelAndView();
        final RenderingContext context = createRenderingContext();
        final Renderer renderer = new GedResourceNotFoundRenderer(
                exception, context);
        mav.addObject("error", renderer);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(viewName);
        mav.setStatus(status);
        return mav;
    }
}
