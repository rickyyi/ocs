package cn.hf.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 说明：页面跳转层
 * @author XiJie
 * @version 1.0
 * @date 2017年10月12日
 */
@Controller
@RequestMapping("/page")
public class PageController {

	
	/**
	 * 说明：跳转到具体的页面
	 * @param page  要跳转的页面
	 * @return ModelAndView
	 * @author XiJie
	 * @time：2017年10月12日 下午1:54:23
	 */
	@RequestMapping("/{page}")
	public ModelAndView toPage(@PathVariable("page")String page){
		ModelAndView mv = new ModelAndView();
		mv.setViewName(page);
		return mv;
	}
	
	
}
