package com.autorun;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.max.entity.MovieInfo;
import com.max.service.MovieService;
import com.max.util.PathUtil;

public class AutoRun implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(1);// new一个定时器
		// 延时运行
		scheduled.schedule(new Runnable() {
			public void run() {
				try {
					MovieService movieService = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext()).getBean(MovieService.class);
					final Path path = Paths.get(PathUtil.getMovieBasePath());
					System.out.println("监控文件夹运行!" + path);
					// 创建WatchService实例,WatchService类似于观察者模式中的观察者
					try (WatchService service = FileSystems.getDefault().newWatchService()) {
						// 将path注册到WatchService中
						path.register(service, StandardWatchEventKinds.ENTRY_CREATE,
								StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
						while (true) {
							// 注册监听服务，获取事件的各个属性
							// serviec.take（）这个方法会一直堵塞，直到某个事件发生。
							WatchKey key = service.take();
							for (WatchEvent<?> watchEvent : key.pollEvents()) {
								final WatchEvent.Kind<?> kind = watchEvent.kind();
								// 丢失或放弃事件时被触发
								if (kind == StandardWatchEventKinds.OVERFLOW) {

								}
								// 当新的文件夹或者文件出现时
								else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
									@SuppressWarnings("unchecked")
									final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
									String filmName = watchEventPath.context().toString();
									System.out.println("创建了文件 :" + filmName);
									MovieInfo movieInfo = new MovieInfo();
									movieInfo.setFileType(filmName.substring(filmName.lastIndexOf(".") + 1));
									movieInfo.setFilmName(filmName);
									File file = new File(PathUtil.getMovieBasePath() + File.separator + filmName);
									movieInfo.setUploadTime(new Date(file.lastModified()));
									movieInfo.setSize(file.length());
									System.out.println("movie:" + movieInfo);
									try {
										movieService.addMovie(movieInfo);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								// 当有任意文件被修改时
								else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
									System.out.println("========文件被修改========");
									@SuppressWarnings("unchecked")
									final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
									String filmName = watchEventPath.context().toString();
									System.out.println("修改了文件 :" + filmName);
									MovieInfo movieInfo = new MovieInfo();
									movieInfo.setFileType(filmName.substring(filmName.lastIndexOf(".") + 1));
									movieInfo.setFilmName(filmName);
									File file = new File(PathUtil.getMovieBasePath() + File.separator + filmName);
									movieInfo.setUploadTime(new Date(file.lastModified()));
									movieInfo.setSize(file.length());
									System.out.println("movie:" + movieInfo);
									try {
										movieService.updateMovie(movieInfo);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								// 当文件夹或者文件消失时
								else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
									@SuppressWarnings("unchecked")
									final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
									final Path filmName = watchEventPath.context();
									System.out.println("删除了文件 :" + filmName);
									try {
										movieService.deleteMovie(filmName.toString());
									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							}
							// WatchKey实例通过poll或者take返回后，就不会捕获任何事件
							// 调用reset()方法就可以将这个WatchKey重新回到watchservice队列，可以重新等待新的事件。
							boolean valid = key.reset();
							if (!valid) {
								break;
							}
						}
					} catch (Exception e) {
						System.err.println(e);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 2000, TimeUnit.MILLISECONDS);// 5000表示首次执行任务的延迟时间，TimeUnit.MILLISECONDS执行的时间间隔数值单位

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
