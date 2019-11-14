package com.max.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.max.entity.MovieInfo;
import com.max.entity.MovieUser;
import com.max.service.MovieService;
import com.max.service.UserService;
import com.max.util.CodeUtil;
import com.max.util.PathUtil;

@Controller
public class controller {
	@Autowired
	private MovieService movieService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	private String index() {
		return "index";
	}

	@RequestMapping(value = "/playtest", method = RequestMethod.GET)
	private String playtest() {
		return "playtest";
	}

	@RequestMapping(value = "/play", method = RequestMethod.GET)
	private String play(@RequestParam String filmName) {
		try {
			MovieInfo movieInfo = movieService.queryMovieByName(filmName);
			int playTimes = movieInfo.getPlayTimes() + 1;
			movieInfo.setPlayTimes(playTimes);
			movieService.updateMovie(movieInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "play";
	}

	/**
	 * 向用户登录页面跳转
	 */

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin(Model model) {
		model.addAttribute("msg", "");
		return "login";
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param model
	 * @param session
	 * @return
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MovieUser user, Model model, HttpServletRequest request, HttpSession session) {
		// 获取用户名和密码
		String username = user.getUserName();
		String password = user.getUserPassword();

		// 0.检验验证码
		if (!CodeUtil.checkVerifyCode(request)) {
			model.addAttribute("msg", "验证码错误");
			return "login";
		}
		// 从数据库中获取对用户名和密码后进行判断

		String userPassword = "";
		try {
			userPassword = userService.queryUserByName(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (password != null && DigestUtils.md5DigestAsHex(password.getBytes()).equals(userPassword)) {
			// 将用户对象添加到Session中
			session.setAttribute("userinfo", user);
			//重定向到主页面的跳转方法 
			return "redirect:index"; 
		}
		
		model.addAttribute("msg", "用户名或密码错误，请重新登录！");
		return "login";
	}
	
	@RequestMapping(value = "/logout")
    public String logout(HttpSession session){
        //清除session
        session.invalidate();
        //重定向到登录页面的跳转方法
        return "redirect:login";
    }

	@RequestMapping("/moviepages")
	@ResponseBody
	private Map<String, Object> moviePages(int limit, int offset, String filmName, String sort, String order) {

		Map<String, Object> modelMap = new HashMap<String, Object>();

		List<MovieInfo> movielist = movieService.queryMovie(limit, offset, filmName, sort, order);

		int total = movieService.queryMovieCount(filmName);

		modelMap.put("total", total);
		modelMap.put("rows", movielist);
		System.out.println("modelMap:"+modelMap.toString());
		return modelMap;

	}

	@RequestMapping("/download")
	private void download(@RequestParam("filmName") String filmName, HttpServletResponse response) {
		String path = PathUtil.getMovieBasePath() + File.separator + filmName;
			try {
				MovieInfo movieInfo = movieService.queryMovieByName(filmName);
				int downTimes = movieInfo.getDownTimes()+1;
				movieInfo.setDownTimes(downTimes);
				movieService.updateMovie(movieInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
			File file = new File(path);// 构造要下载的文件
			try {
				if (file.exists()) {
					InputStream ins = new FileInputStream(path);// 构造一个读取文件的IO流对象
					BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
					OutputStream outs = response.getOutputStream();// 获取文件输出IO流
					BufferedOutputStream bouts = new BufferedOutputStream(outs);
					response.setContentType("application/x-download");// 设置response内容的类型 普通下载类型
					// response.setContentType("application/vnd.android.package-archive");//设置response内容的类型
					response.setContentLength((int) file.length());// 设置文件大小
					response.setHeader("Content-disposition",
							"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 设置头部信息
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					// 开始向网络传输文件流
					while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
						bouts.write(buffer, 0, bytesRead);
					}
					bouts.flush();// 这里一定要调用flush()方法
					ins.close();
					bins.close();
					outs.close();
					bouts.close();
				} else {
					System.out.println("下载的文件不存在");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
