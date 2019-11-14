package test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Test {
	public static void main(String[] args) {
		final Path path = Paths.get("D:\\movie");
		// 创建WatchService实例,WatchService类似于观察者模式中的观察者
		try (WatchService service = FileSystems.getDefault().newWatchService()) {
			// 将path注册到WatchService中
			path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);
			while (true) {
				// 注册监听服务，获取事件的各个属性
				// serviec.take（）这个方法会一直堵塞，直到某个事件发生。
				WatchKey key = service.take();
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					final WatchEvent.Kind<?> kind = watchEvent.kind();
					// 丢失或放弃事件时被触发
					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					}
					// 当新的文件夹或者文件出现时
					else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						@SuppressWarnings("unchecked")
						final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
						final Path filename = watchEventPath.context();
						// print it out
						System.out.println("新增文件:" + filename);
					}
					// 当有任意文件被修改时
					else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
						System.out.println("========文件被修改========");
					}
					// 当文件夹或者文件消失时
					else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
						@SuppressWarnings("unchecked")
						final WatchEvent<Path> watchEventPath = (WatchEvent<Path>) watchEvent;
						final Path filename = watchEventPath.context();
						// print it out
						System.out.println("删除文件:" + filename);

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
	}
}
