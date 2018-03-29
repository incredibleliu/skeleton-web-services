package org.example.ws.service;

import java.util.Collection;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
		propagation=Propagation.SUPPORTS, 
		readOnly=true)
public class GreetingServiceBean implements GreetingService {
	
//	private static BigInteger nextId;
//	private static Map<BigInteger, Greeting> greetingMap;
//
//	private static Greeting save(Greeting greeting) {
//		if (greetingMap == null) {
//			greetingMap = new HashMap<BigInteger, Greeting>();
//			nextId = BigInteger.ONE;
//		}
//		//if update...
//		if(greeting.getId() != null){
//			Greeting oldGreeting = greetingMap.get(greeting.getId());
//			if(oldGreeting == null){
//				return null;
//			}
//			greetingMap.remove(greeting.getId());
//			greetingMap.put(greeting.getId(), greeting);
//			return greeting;
//		}
//		//if Create...
//		greeting.setId(nextId);
//		nextId = nextId.add(BigInteger.ONE);
//		greetingMap.put(greeting.getId(), greeting);
//		return greeting;
//
//	}
//	
//	private static boolean remove(BigInteger id){
//		Greeting deletedGreeting = greetingMap.remove(id);
//		if(deletedGreeting == null){
//			return false;
//		}
//		return true;
//	}
//
//	static {
//		Greeting g1 = new Greeting();
//		g1.setText("Hello World!");
//		save(g1);
//
//		Greeting g2 = new Greeting();
//		g2.setText("Hola Mundo!");
//		save(g2);
//
//	}
	
//	@Override
//	public Collection<Greeting> findAll() {
//		Collection<Greeting> greetings = greetingMap.values();
//		return greetings;
//	}
//
//	@Override
//	public Greeting findOne(BigInteger id) {
//		Greeting greeting = greetingMap.get(id);
//		return greeting;
//	}
//
//	@Override
//	public Greeting create(Greeting greeting) {
//		Greeting savedGreeting = save(greeting);
//		return savedGreeting;
//	}
//
//	@Override
//	public Greeting update(Greeting greeting) {
//		Greeting updatedGreeting = save(greeting);
//		return updatedGreeting;
//	}
//
//	@Override
//	public void delete(BigInteger id) {
//		remove(id);
//	}
//	
	
	@Autowired
	private GreetingRepository greetingRepository;
	
	@Override
	public Collection<Greeting> findAll() {
		Collection<Greeting> greetings = greetingRepository.findAll();
		return greetings;
	}

	@Override
	@Cacheable(
			value = "greetings",
			key = "#id")
	public Greeting findOne(Long id) {
		Greeting greeting = greetingRepository.findOne(id);
		return greeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
	@CachePut(
			value = "greetings",
			key = "#result.id")
	public Greeting create(Greeting greeting) {
		
		if(greeting.getId() != null){
			//Cannot create Greeting with specified ID　value
			return null;
		}
		Greeting savedGreeting = greetingRepository.save(greeting);
		
		//illustrate Tx rollback
		if(savedGreeting.getId() == 4){
			throw new RuntimeException("Roll me back!");
		}
		return savedGreeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
	@CachePut(
			value = "greetings",
			key = "#greeting.id")
	public Greeting update(Greeting greeting) {
		
		Greeting greetingPersisted = findOne(greeting.getId());
		if(greetingPersisted == null){
			//cannot update Greeting that hasn't been persisted
			return null;
		}
		Greeting updatedGreeting = greetingRepository.save(greeting);
		return updatedGreeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
	@CacheEvict(value = "greetings",
				key = "#id")
	public void delete(Long id) {
		greetingRepository.delete(id);
	}
	
	@Override
	@CacheEvict(
			value = "greetings",
			allEntries = true)
	public void evictCache(){
		
	}



}
