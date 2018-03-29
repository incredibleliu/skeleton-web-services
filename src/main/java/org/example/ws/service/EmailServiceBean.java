package org.example.ws.service;

import java.util.concurrent.Future;

import org.example.ws.model.Greeting;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceBean implements EmailService {

	@Override
	public Boolean send(Greeting greeting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Async
	@Override
	public void sendAsync(Greeting greeting) {
		// TODO Auto-generated method stub

	}

	@Async
	@Override
	public Future<Boolean> sendAsyncWithResult(Greeting greeting) {
		// TODO Auto-generated method stub
		return null;
	}

}
