package com.pp.server;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.pp.server.inter.AquiredLockWorker;
import com.pp.server.inter.RedisLocker;

import brave.sampler.Sampler;

@SpringBootApplication
@RestController
public class EServiceMiyaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EServiceMiyaApplication.class, args);
	}
	
	@Autowired
	private RedisLocker redisLocker;
	
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping("/testLock")
	public String testLock() {
		CountDownLatch startSingle = new CountDownLatch(1);
		CountDownLatch doneSingle = new CountDownLatch(5);
		for(int i = 0; i< 5 ; i++)
		{
			new Thread(new Worker(startSingle, doneSingle)).start();
		}
		startSingle.countDown(); // let all threads proceed
		try {
			doneSingle.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("/hello")
	public String sayHello() {
		return "/sayHello";
	}
	
	
	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
	
	class Worker implements Runnable
	{
		private CountDownLatch startSingle;
		private CountDownLatch doneSingle;
		
		public Worker(CountDownLatch startSingle, CountDownLatch doneSingle) {
			// TODO Auto-generated constructor stub
			this.startSingle = startSingle;
			this.doneSingle = doneSingle;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				System.out.println("Thread start -----");
				startSingle.await();
				redisLocker.lock("test", new AquiredLockWorker<Object>() {

					@Override
					public Object invokeAfterLockAcquire() {
						// TODO Auto-generated method stub
						doTask();
						return null;
					}
				});
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		public void doTask() {
			System.out.println(Thread.currentThread().getName()+" start");
			Random random = new Random();
			int _random = random.nextInt(20);
			System.out.println(Thread.currentThread().getName()+" sleep"+_random+"mills");
			try {
				Thread.sleep(_random);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				System.out.println(Thread.currentThread().getName()+" end");
			}
			doneSingle.countDown();
		}
		
	}
}

