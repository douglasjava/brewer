package com.algaworks.brewer.controller;

/**
@Controller
public class MyErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		if (statusCode == HttpStatus.NOT_FOUND.value()) {
			return "404";
		} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			return "500";
		}

		return "404";
	}

	@Override
	public String getErrorPath() {
		return "/404";
	}
}
**/