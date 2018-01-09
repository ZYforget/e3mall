package cn.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {

	Logger logger=LoggerFactory.getLogger(GlobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.debug("系统发生异常");
		logger.info("系统出现info异常");
		logger.error("系统发生错误", ex);
		//发邮件、发短信
		//Jmail：可以查找相关的资料
		//需要在购买短信。调用第三方接口即可。
		//展示错误页面
		ModelAndView andView=new ModelAndView();
		andView.addObject("message", "系统出现异常");
		andView.setViewName("error/exception");
		return andView;
	}

}
